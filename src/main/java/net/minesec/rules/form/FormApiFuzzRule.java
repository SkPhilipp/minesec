package net.minesec.rules.form;

import net.minesec.rules.api.ContextBuilder;

import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.PAGELOAD;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class FormApiFuzzRule implements Consumer<ContextBuilder> {

    @Override
    public void accept(ContextBuilder contextBuilder) {
        contextBuilder.on(PAGELOAD, ctx -> {
            // TODO: Implement this rule
            // - Form discovery and fuzzing
            // - Identify weaknesses (ie CORS-bypassing forms)
            // - Submitting a single-use request multiple times in parallel (example; gift cards)
            // - Enumerating and then choosing values outside the range allowed through the frontend
            // - Check for disabled fields and report them
            // - Check for hidden fields and report them
        });
    }

}