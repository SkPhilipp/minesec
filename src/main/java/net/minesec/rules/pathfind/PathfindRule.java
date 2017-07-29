package net.minesec.rules.pathfind;

import net.minesec.rules.api.ContextBuilder;

import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.RESPONSE;
import static net.minesec.rules.api.ContextBuilder.ContextEvent.SETUP;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class PathfindRule implements Consumer<ContextBuilder> {

    @Override
    public void accept(ContextBuilder contextBuilder) {
        // TODO: Implement this rule
        contextBuilder.addEventListener(RESPONSE, ctx -> {
            // Find links in JS, CSS, HTML comments
            // Find links in JSON responses
            // Find links using [href], [src], [action]
            // Find links using [href], [src], [action]
        });
        contextBuilder.addEventListener(SETUP, ctx -> {
            // Find paths in robots.txt, especially disallowed paths
        });
    }

}

