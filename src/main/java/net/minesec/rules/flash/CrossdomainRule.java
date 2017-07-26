package net.minesec.rules.flash;

import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class CrossdomainRule implements Rule {

    @Override
    public void onSetup(Context ctx) {
        // TODO[RULE]: Inspect crossdomain.xml
    }
}
