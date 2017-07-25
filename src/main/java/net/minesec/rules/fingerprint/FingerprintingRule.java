package net.minesec.rules.fingerprint;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import lombok.Data;
import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class FingerprintingRule implements Rule {

    @Data
    public static class Meta {
        private boolean java = false;
    }

    @Override
    public Moment moment() {
        return Moment.RESPONSE;
    }

    @Override
    public void apply(Context ctx) {
        // TODO: If possible, implement https://github.com/AliasIO/Wappalyzer if license-possible
        final HttpResponse response = ctx.getResponse();
        if (response instanceof FullHttpResponse) {
            FullHttpResponse fullHttpResponse = (FullHttpResponse) response;
            Meta meta = new Meta();
            final String setCookieHeader = fullHttpResponse.headers().get("Set-Cookie");
            if (setCookieHeader != null && setCookieHeader.contains("JSESSIONID")) {
                meta.setJava(true);
            }
            // TODO: Save data on context
        }

    }
}
