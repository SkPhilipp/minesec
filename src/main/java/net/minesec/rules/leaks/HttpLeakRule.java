package net.minesec.rules.leaks;

import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class HttpLeakRule implements Rule {

    @Override
    public void onResponse(Context ctx) {
        // TODO[RULE]: Check if the response contains any personal data
    }
}
