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

    RuleCallingHttpFilter(HttpRequest originalRequest, ChannelHandlerContext ctx) {
        super(originalRequest, ctx);
    }

    @Override
    public HttpResponse proxyToServerRequest(HttpObject httpObject) {
        if (httpObject instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) httpObject;
            Rules.TRAFFIC_RULES.parallelStream().forEach(rule -> rule.processRequest(request));
        }
        return super.proxyToServerRequest(httpObject);
    }

    @Override
    public HttpObject proxyToClientResponse(HttpObject httpObject) {
        if (httpObject instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) httpObject;
            Rules.TRAFFIC_RULES.parallelStream().forEach(rule -> rule.processResponse(this.originalRequest, response));
        }
        return super.proxyToClientResponse(httpObject);
    }
}
