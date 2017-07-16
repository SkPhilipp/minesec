package net.minesec.rules.logging;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.minesec.rules.core.TrafficRule;

import static io.netty.util.CharsetUtil.UTF_8;

/**
 * Copyright (c) 16-7-17, MineSec. All rights reserved.
 */
public class LoggingTrafficRule implements TrafficRule {

    @Override
    public void processResponse(HttpRequest request, HttpResponse response) {
        if (response instanceof HttpContent && request instanceof FullHttpRequest) {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) request;
            HttpContent httpContent = (HttpContent) response;
            String output = httpContent.content().toString(UTF_8);
            System.out.println(String.format("(%d) [%s] [%s] %s",
                    output.length(),
                    request.getMethod(),
                    fullHttpRequest.headers().get("Host"),
                    request.getUri()));
        }
    }
}
