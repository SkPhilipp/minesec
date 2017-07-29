package net.minesec.rules.clickjacking;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import net.minesec.rules.api.ContextBuilder;

import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.RESPONSE;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class ClickjackingRule implements Consumer<ContextBuilder> {

    @Override
    public void accept(ContextBuilder contextBuilder) {
        contextBuilder.on(RESPONSE, ctx -> {
            final HttpResponse response = ctx.getResponse();
            final HttpHeaders headers = response.headers();
            final String xFrameOptions = headers.get("X-Frame-Options");
            if (xFrameOptions != null) {
                if (!xFrameOptions.equals("DENY")
                        && !xFrameOptions.equals("SAMEORIGIN")) {
                    final ODatabaseDocumentTx db = ctx.getDatabase();
                    final ODocument xFrameOptionsMeta = db.newInstance(this.getClass().getSimpleName());
                    xFrameOptionsMeta.field("match", "ALLOW-FROM");
                    xFrameOptionsMeta.field("value", xFrameOptions);
                    db.save(xFrameOptionsMeta);
                }
            } else {
                final ODatabaseDocumentTx db = ctx.getDatabase();
                final ODocument xFrameOptionsMeta = db.newInstance(this.getClass().getSimpleName());
                xFrameOptionsMeta.field("match", "MISSING");
                db.save(xFrameOptionsMeta);
            }
        });
    }
}
