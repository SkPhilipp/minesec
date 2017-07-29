package net.minesec.rules.json;

import net.minesec.rules.api.ContextBuilder;

import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.RESPONSE;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class JsonApiFuzzRule implements Consumer<ContextBuilder> {

    @Override
    public void accept(ContextBuilder contextBuilder) {
        contextBuilder.on(RESPONSE, ctx -> {
            // TODO: Implement this rule
            // - JSON API discovery and fuzzing
            // - Submitting a single-use request multiple times in parallel ( example; gift cards )
            // - Enumerating and then choosing values outside the range allowed through the frontend
            // - Identify weaknesses (ie CORS-bypassing JSONP, or CORS-bypassing non-padded JSON)
        });
    }

}
