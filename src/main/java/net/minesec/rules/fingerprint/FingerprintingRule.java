package net.minesec.rules.fingerprint;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class FingerprintingRule implements Rule {

    @Override
    public void onResponse(Context ctx) {
        // TODO[RULE]: If license possible, implement https://github.com/AliasIO/Wappalyzer
        final HttpResponse response = ctx.getResponse();
        if (response instanceof FullHttpResponse) {
            FullHttpResponse fullHttpResponse = (FullHttpResponse) response;
            final String setCookieHeader = fullHttpResponse.headers().get("Set-Cookie");
            if (setCookieHeader != null && setCookieHeader.contains("JSESSIONID")) {
                final ODatabaseDocumentTx db = ctx.getDatabase();
                final ODocument meta = db.newInstance(this.getClass().getSimpleName());
                meta.field("match", "JSESSIONID");
                meta.field("value", "java");
                db.save(meta);
            }
        }
    }
}
