package net.minesec.rules.form;

import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class FormApiWeaknessRule implements Rule {

    @Override
    public void onResponse(Context ctx) {
        // TODO[RULE]: Form discovery and fuzzing
        // TODO[RULE]: Identify weaknesses (ie CORS-bypassing forms)
    }
}
