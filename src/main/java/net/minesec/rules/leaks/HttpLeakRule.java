package net.minesec.rules.leaks;

import net.minesec.rules.api.ContextBuilder;

import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.RESPONSE;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class HttpLeakRule implements Consumer<ContextBuilder> {

    @Override
    public void accept(ContextBuilder contextBuilder) {
        contextBuilder.addEventListener(RESPONSE, ctx -> {
            // TODO: Implement this rule
            // Check if the response contains data entered by other browsers
            // Find code comments that don't match anything contained in common licenses or libraries
        });
    }

}
