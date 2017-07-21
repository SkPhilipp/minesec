package net.minesec.rules.logging;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;

import static io.netty.util.CharsetUtil.UTF_8;

/**
 * Copyright (c) 16-7-17, MineSec. All rights reserved.
 */
public class LoggingTrafficRule implements Rule {

    @Override
    public Moment moment() {
        return Moment.RESPONSE;
    }

    @Override
    public void apply(final Context ctx) {
        final HttpRequest request = ctx.getRequest();
        final HttpResponse response = ctx.getResponse();
        if (response instanceof HttpContent && request instanceof FullHttpRequest) {
            final FullHttpRequest fullHttpRequest = (FullHttpRequest) request;
            final HttpContent httpContent = (HttpContent) response;
            final String output = httpContent.content().toString(UTF_8);
            // TODO: Use shared logging system
            System.out.println(String.format("(%d) [%s] [%s] %s",
                    output.length(),
                    request.getMethod(),
                    fullHttpRequest.headers().get("Host"),
                    request.getUri()));
        }
    }
}
