package net.minesec.rules.graph;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import net.minesec.rules.api.ContextBuilder;

import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.RESPONSE;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class GraphRule implements Consumer<ContextBuilder> {

    @Override
    public void accept(ContextBuilder contextBuilder) {
        contextBuilder.on(RESPONSE, ctx -> {
            final ODatabaseDocumentTx db = ctx.getDatabase();
            final ODocument meta = db.newInstance(this.getClass().getSimpleName());
            meta.field("context", ctx.getId());
            meta.field("webdriver", ctx.getWebDriver().getCurrentUrl());
            meta.field("uri", ctx.getRequest().getUri());
            meta.field("host", ctx.getRequest().headers().get("Host"));
            db.save(meta);
        });
    }

}
