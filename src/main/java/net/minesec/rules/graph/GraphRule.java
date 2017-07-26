package net.minesec.rules.graph;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class GraphRule implements Rule {
    @Override
    public Moment moment() {
        return Moment.RESPONSE;
    }

    @Override
    public void apply(Context ctx) {
        // TODO[CORE]: Multiple moments
        final ODatabaseDocumentTx db = ctx.getDatabase();
        final ODocument meta = db.newInstance(this.getClass().getSimpleName());
        meta.field("context", ctx.getId());
        meta.field("webdriver", ctx.getWebDriver().getCurrentUrl());
        meta.field("uri", ctx.getRequest().getUri());
        meta.field("host", ctx.getRequest().headers().get("Host"));
        db.save(meta);

    }
}
