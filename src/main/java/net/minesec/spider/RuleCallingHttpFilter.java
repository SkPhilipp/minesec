package net.minesec.spider;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.minesec.rules.api.Context;
import org.littleshoot.proxy.HttpFiltersAdapter;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.*;

/**
 * Copyright (c) 11-7-17, MineSec. All rights reserved.
 */
class RuleCallingHttpFilter extends HttpFiltersAdapter {

    private Context context;

    RuleCallingHttpFilter(HttpRequest originalRequest, ChannelHandlerContext ctx, Context context) {
        super(originalRequest, ctx);
        this.context = context;
        context.dispatch(SETUP);
    }

    @Override
    public HttpResponse proxyToServerRequest(HttpObject httpObject) {
        if (httpObject instanceof HttpRequest) {
            final HttpRequest httpRequest = (HttpRequest) httpObject;
            this.context = this.context.forRequest(httpRequest);
            context.dispatch(REQUEST);
        }
        return context.getMockResponse();
    }

    @Override
    public HttpObject proxyToClientResponse(HttpObject httpObject) {
        if (httpObject instanceof HttpResponse) {
            final HttpResponse httpResponse = (HttpResponse) httpObject;
            this.context = this.context.forResponse(httpResponse);
            context.dispatch(RESPONSE);
        }
        final HttpResponse mockResponse = context.getMockResponse();
        return mockResponse == null ? httpObject : mockResponse;
    }
}
