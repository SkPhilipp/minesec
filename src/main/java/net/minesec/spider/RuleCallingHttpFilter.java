package net.minesec.spider;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.minesec.rules.Rules;
import org.littleshoot.proxy.HttpFiltersAdapter;

/**
 * Copyright (c) 11-7-17, MineSec. All rights reserved.
 */
class RuleCallingHttpFilter extends HttpFiltersAdapter {

    private ContextImpl contextImpl;

    RuleCallingHttpFilter(HttpRequest originalRequest, ChannelHandlerContext ctx, ContextImpl contextImpl) {
        super(originalRequest, ctx);
        this.contextImpl = contextImpl;
        Rules.invokeAll(rule -> rule.onSetup(contextImpl));
    }

    @Override
    public HttpResponse proxyToServerRequest(HttpObject httpObject) {
        if (httpObject instanceof HttpRequest) {
            final HttpRequest httpRequest = (HttpRequest) httpObject;
            this.contextImpl = this.contextImpl.forRequest(httpRequest);
            Rules.invokeAll(rule -> rule.onRequest(contextImpl));
        }
        // TODO[CORE]: Allow faked responses from the contextImpl object
        final HttpResponse response = this.contextImpl.getResponse();
        return response != null ? response : super.proxyToServerRequest(httpObject);
    }

    @Override
    public HttpObject proxyToClientResponse(HttpObject httpObject) {
        if (httpObject instanceof HttpResponse) {
            final HttpResponse httpResponse = (HttpResponse) httpObject;
            this.contextImpl = this.contextImpl.forResponse(httpResponse);
            Rules.invokeAll(rule -> rule.onResponse(contextImpl));
        }
        // TODO[CORE]: Allow faked responses from the contextImpl object
        final HttpResponse response = this.contextImpl.getResponse();
        return response != null ? response : super.proxyToClientResponse(httpObject);
    }
}
