package net.minesec.rules.json;

import net.minesec.rules.Context;
import net.minesec.rules.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class JsonApiFuzzRule implements Rule {
    @Override
    public Moment moment() {
        return Moment.RESPONSE;
    }

    @Override
    public void apply(Context ctx) {
        // TODO: JSON API discovery and fuzzing
    }
}
