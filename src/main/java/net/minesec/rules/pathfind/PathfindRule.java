package net.minesec.rules.pathfind;

import net.minesec.rules.api.ContextBuilder;

import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.RESPONSE;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class PathfindRule implements Consumer<ContextBuilder> {

    @Override
    public void accept(ContextBuilder contextBuilder) {
        contextBuilder.on(RESPONSE, ctx -> {
            // TODO[RULE]: Find links in JS, CSS, HTML comments
            // TODO[RULE]: Find links in JSON responses
            // TODO[RULE]: Find links using [href], [src], [action]
        });
        contextBuilder.on(RESPONSE, ctx -> {
            // TODO[RULE]: Find links using [href], [src], [action]
        });
        contextBuilder.on(RESPONSE, ctx -> {
            // TODO[RULE]: Find paths in robots.txt, especially disallowed paths
        });
    }

}

