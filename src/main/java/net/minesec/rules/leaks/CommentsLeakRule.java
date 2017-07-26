package net.minesec.rules.leaks;

import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class CommentsLeakRule implements Rule {

    @Override
    public Moment moment() {
        return Moment.RESPONSE;
    }

    @Override
    public void apply(Context ctx) {
        // TODO[RULE]: Find interesting comments that don't match common licenses
    }
}