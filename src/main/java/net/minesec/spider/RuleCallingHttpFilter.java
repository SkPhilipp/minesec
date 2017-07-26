package net.minesec.spider;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.minesec.rules.Rules;
import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;
import org.littleshoot.proxy.HttpFiltersAdapter;

/**
 * Copyright (c) 11-7-17, MineSec. All rights reserved.
 */
class RuleCallingHttpFilter extends HttpFiltersAdapter {

    private Context context;

    RuleCallingHttpFilter(HttpRequest originalRequest, ChannelHandlerContext ctx, Context context) {
        super(originalRequest, ctx);
        this.context = context;
        Rules.invokeAll(Rule.Moment.SETUP, this.context);
    }

    @Override
    public HttpResponse proxyToServerRequest(HttpObject httpObject) {
        if (httpObject instanceof HttpRequest) {
            final HttpRequest httpRequest = (HttpRequest) httpObject;
            this.context = this.context.forRequest(httpRequest);
            Rules.invokeAll(Rule.Moment.REQUEST, this.context);
        }
        // TODO[CORE]: Allow faked responses from the context object
        final HttpResponse response = this.context.getResponse();
        return response != null ? response : super.proxyToServerRequest(httpObject);
    }

    @Override
    public HttpObject proxyToClientResponse(HttpObject httpObject) {
        if (httpObject instanceof HttpResponse) {
            final HttpResponse httpResponse = (HttpResponse) httpObject;
            this.context = this.context.forResponse(httpResponse);
            Rules.invokeAll(Rule.Moment.RESPONSE, this.context);
        }
        // TODO[CORE]: Allow faked responses from the context object
        final HttpResponse response = this.context.getResponse();
        return response != null ? response : super.proxyToClientResponse(httpObject);
    }
}
