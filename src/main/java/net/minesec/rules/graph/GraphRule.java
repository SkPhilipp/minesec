package net.minesec.rules.graph;

import net.minesec.rules.Context;
import net.minesec.rules.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class GraphRule implements Rule {
    @Override
    public Moment moment() {
        return Moment.RESPONSE;
    }

    @Override
    public void apply(Context ctx) {
        // TODO: graph (neo4j, orientdb ; reconstructing APIs automatically)
    }
}
