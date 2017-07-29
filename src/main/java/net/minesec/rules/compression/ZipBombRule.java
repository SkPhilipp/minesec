package net.minesec.rules.compression;

import net.minesec.rules.api.ContextBuilder;

import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.SETUP;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class ZipBombRule implements Consumer<ContextBuilder> {

    @Override
    public void accept(ContextBuilder contextBuilder) {
        contextBuilder.addEventListener(SETUP, ctx -> {
            // TODO: Implement this rule
            // Perform a Zip-Bomb on the target URLs, if enabled
            // These auxiliary routines should be executed when the underlying tech stack changes
        });
    }

}