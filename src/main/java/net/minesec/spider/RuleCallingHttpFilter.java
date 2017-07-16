package net.minesec.spider;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.minesec.rules.Rules;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.openqa.selenium.WebDriver;

import java.util.function.Consumer;

/**
 * Copyright (c) 11-7-17, MineSec. All rights reserved.
 */
class RuleCallingHttpFilter extends HttpFiltersAdapter {

    private final Consumer<Consumer<WebDriver>> taskConsumer;

    RuleCallingHttpFilter(HttpRequest originalRequest, ChannelHandlerContext ctx, Consumer<Consumer<WebDriver>> taskConsumer) {
        super(originalRequest, ctx);
        this.taskConsumer = taskConsumer;
    }

    @Override
    public HttpResponse proxyToServerRequest(HttpObject httpObject) {
        if (httpObject instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) httpObject;
            Rules.TRAFFIC_RULES.parallelStream().forEach(rule -> rule.processRequest(request, this.taskConsumer));
        }
        return super.proxyToServerRequest(httpObject);
    }

    @Override
    public HttpObject proxyToClientResponse(HttpObject httpObject) {
        if (httpObject instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) httpObject;
            Rules.TRAFFIC_RULES.parallelStream().forEach(rule -> rule.processResponse(this.originalRequest, response, this.taskConsumer));
        }
        return super.proxyToClientResponse(httpObject);
    }
}
