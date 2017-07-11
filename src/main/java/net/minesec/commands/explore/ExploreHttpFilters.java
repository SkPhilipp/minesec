package net.minesec.commands.explore;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.littleshoot.proxy.HttpFiltersAdapter;

import java.nio.charset.Charset;

/**
 * Copyright (c) 11-7-17, MineSec. All rights reserved.
 */
public class ExploreHttpFilters extends HttpFiltersAdapter {

    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public ExploreHttpFilters(HttpRequest originalRequest, ChannelHandlerContext ctx) {
        super(originalRequest, ctx);
    }

    @Override
    public HttpResponse proxyToServerRequest(HttpObject httpObject) {
        if (httpObject instanceof FullHttpRequest) {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) httpObject;
            HttpHeaders headers = fullHttpRequest.headers();
            System.out.println(headers.get("Host") + " " + fullHttpRequest.getUri());
            System.out.println();
        }
        return super.proxyToServerRequest(httpObject);
    }

    @Override
    public HttpObject proxyToClientResponse(HttpObject httpObject) {
        if (httpObject instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) httpObject;
            String output = httpContent.content().toString(UTF_8);
            System.out.println(String.format("(%d) [%s] %s",
                    output.length(),
                    this.originalRequest.getMethod(),
                    this.originalRequest.getUri()));
        }
        return super.proxyToClientResponse(httpObject);
    }
}
