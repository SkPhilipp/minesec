package net.minesec.rules.mock;

import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class MockedResponseRule implements Rule {
    @Override
    public Moment moment() {
        return Moment.REQUEST;
    }

    @Override
    public void apply(Context ctx) {
        // TODO: Under certain conditions, create a faked HttpResponse and set it on the context
//        final HttpRequest httpRequest = ctx.getRequest();
//        if (httpRequest.getUri().endsWith(".jpg")) {
//            final DefaultHttpResponse httpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR);
//            System.out.println("BLOCKING: " + httpRequest.getUri());
//            ctx.setResponse(httpResponse);
//        }
    }
}
