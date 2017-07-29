package net.minesec.rules.mock;

import com.j256.ormlite.dao.Dao;
import net.minesec.rules.api.ContextBuilder;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.RESPONSE;
import static net.minesec.rules.api.Utilities.silenced;

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
        try {
            final Dao<Script, ?> dao = contextBuilder.getDatabase().getDao(Script.class);
            final List<String> scriptList = new ArrayList<>();
            dao.iterator().forEachRemaining(script -> scriptList.add(script.getContent()));
            contextBuilder.on(RESPONSE, ctx -> {
                final Bindings bindings = engine.createBindings();
                bindings.put("ctx", ctx);
                scriptList.forEach(silenced(engine::eval));
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
