package net.minesec.rules.authorization;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;
import net.minesec.rules.fingerprint.FingerprintingRule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class CookieWatchRule implements Rule {

    @Override
    public Moment moment() {
        return Moment.RESPONSE;
    }

    @Override
    public void apply(Context ctx) {
        final HttpResponse response = ctx.getResponse();
        if (response instanceof FullHttpResponse) {
            FullHttpResponse fullHttpResponse = (FullHttpResponse) response;
            FingerprintingRule.Meta meta = new FingerprintingRule.Meta();
            final String setCookieHeader = fullHttpResponse.headers().get("Set-Cookie");
            if (setCookieHeader != null) {
                // TODO: Use shared logging
                System.out.println("Set-Cookie:" + setCookieHeader);
            }
            // TODO: Save meta on ctx db
        }
    }
}