package net.minesec.rules.mock;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.record.impl.ODocument;
import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class ScriptedRule implements Rule {

    private final ScriptEngine engine;

    public ScriptedRule() {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        engine = engineManager.getEngineByName("nashorn");
    }

    // TODO[CORE] Convert onRequest etc to ctxBuilder.onRequest(Consumer<Context>) to allow for custom rule setups including db interaction
    @Override
    public void onRequest(Context ctx) {
        final Bindings bindings = engine.createBindings();
        final ODatabaseDocumentTx database = ctx.getDatabase();
        final ORecordIteratorClass<ODocument> scripts = database.browseClass("Scripts");
        bindings.put("ctx", ctx);
        scripts.forEach(document -> {
            // TODO[RULE] Retrieve scripts on startup or on some kind of reload event
            final String content = document.field("content");
            try {
                engine.eval(content);
            } catch (ScriptException e) {
                // TODO[RULE] Use a common logging system
                e.printStackTrace();
            }
        });
    }
}
