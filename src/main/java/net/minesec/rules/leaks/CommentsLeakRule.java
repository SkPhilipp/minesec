package net.minesec.rules.leaks;

import net.minesec.rules.api.ContextBuilder;

import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.RESPONSE;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class CommentsLeakRule implements Consumer<ContextBuilder> {

    @Override
    public void accept(ContextBuilder contextBuilder) {
        contextBuilder.on(RESPONSE, ctx -> {
            // TODO: Implement this rule
            // Find comments that don't match anything contained in common licenses or libraries
        });
    }

}
