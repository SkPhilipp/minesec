package net.minesec.rules.form;

import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class FormApiWeaknessRule implements Rule {
    @Override
    public Moment moment() {
        return Moment.RESPONSE;
    }

    @Override
    public void apply(Context ctx) {
        // TODO: Form discovery and fuzzing
        // TODO: Identify weaknesses (ie CORS-bypassing forms)
    }
}
