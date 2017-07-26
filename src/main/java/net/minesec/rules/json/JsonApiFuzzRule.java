package net.minesec.rules.json;

import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class JsonApiFuzzRule implements Rule {

    @Override
    public void onResponse(Context ctx) {
        // TODO[RULE]: JSON API discovery and fuzzing
        // TODO[RULE]: Submitting a single-use request multiple times in parallel ( example; gift cards )
        // TODO[RULE]: Enumerating and then choosing values outside the range allowed through the frontend
    }
}
