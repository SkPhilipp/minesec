package net.minesec.rules.logging;

import io.netty.handler.codec.http.*;
import net.minesec.rules.core.TrafficRule;

import static io.netty.util.CharsetUtil.UTF_8;

/**
 * Copyright (c) 16-7-17, MineSec. All rights reserved.
 */
public class LoggingTrafficRule implements TrafficRule {

    @Override
    public void processRequest(HttpRequest request) {
        if (request instanceof FullHttpRequest) {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) request;
            HttpHeaders headers = fullHttpRequest.headers();
            System.out.println(headers.get("Host") + " " + fullHttpRequest.getUri());
            System.out.println();
        }
    }

    @Override
    public void processResponse(HttpRequest request, HttpResponse response) {
        if (response instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) response;
            String output = httpContent.content().toString(UTF_8);
            System.out.println(String.format("(%d) [%s] %s",
                    output.length(),
                    request.getMethod(),
                    request.getUri()));
        }
    }
}
