package net.minesec.rules.mock;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.iterator.ORecordIteratorClass;
import com.orientechnologies.orient.core.record.impl.ODocument;
import net.minesec.rules.api.ContextBuilder;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.RESPONSE;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class ScriptedRule implements Consumer<ContextBuilder> {

    private final ScriptEngine engine;

    public ScriptedRule() {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        engine = engineManager.getEngineByName("nashorn");
    }

    @Override
    public void accept(ContextBuilder contextBuilder) {
        final ODatabaseDocumentTx database = contextBuilder.getDatabase();
        final ORecordIteratorClass<ODocument> scripts = database.browseClass("Scripts");
        final List<String> scriptList = new ArrayList<>();
        scripts.forEach(document -> scriptList.add(document.field("content")));
        contextBuilder.on(RESPONSE, ctx -> {
            final Bindings bindings = engine.createBindings();
            bindings.put("ctx", ctx);
            scriptList.forEach(script -> {
                try {
                    engine.eval(script);
                } catch (ScriptException e) {
                    // TODO[RULE] Use a common logging system
                    e.printStackTrace();
                }
            });
        });
    }

}
