package net.minesec.spider;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.minesec.rules.Rules;
import net.minesec.rules.core.Context;
import net.minesec.rules.core.ContextBuilder;
import net.minesec.rules.core.Rule;
import org.littleshoot.proxy.HttpFiltersAdapter;

/**
 * Copyright (c) 11-7-17, MineSec. All rights reserved.
 */
class RuleCallingHttpFilter extends HttpFiltersAdapter {

    private final ContextBuilder contextBuilder;

    RuleCallingHttpFilter(HttpRequest originalRequest,
                          ChannelHandlerContext ctx,
                          ContextBuilder contextBuilder) {
        super(originalRequest, ctx);
        // TODO: Re-use context for the duration of this object
        this.contextBuilder = contextBuilder;
    }

    @Override
    public HttpResponse proxyToServerRequest(HttpObject httpObject) {
        if (httpObject instanceof HttpRequest) {
            final HttpRequest request = (HttpRequest) httpObject;
            final Context context = this.contextBuilder.build(request);
            Rules.invokeAll(Rule.Moment.BEFORE_REQUEST, context);
        }
        return super.proxyToServerRequest(httpObject);
    }

    @Override
    public HttpObject proxyToClientResponse(HttpObject httpObject) {
        if (httpObject instanceof HttpResponse) {
            final HttpResponse response = (HttpResponse) httpObject;
            final Context context = this.contextBuilder.build(this.originalRequest, response);
            Rules.invokeAll(Rule.Moment.AFTER_RESPONSE, context);
            Rules.invokeAll(Rule.Moment.AFTER_RESPONSE_PROCESSED, context);
        }
        return super.proxyToClientResponse(httpObject);
    }
}
