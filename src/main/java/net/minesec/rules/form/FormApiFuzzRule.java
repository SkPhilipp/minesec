package net.minesec.rules.form;

import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class FormApiFuzzRule implements Rule {
    @Override
    public Moment moment() {
        return Moment.RESPONSE;
    }

    @Override
    public void apply(Context ctx) {
        // TODO: Form discovery and fuzzing
        // TODO: Submitting a single-use request multiple times in parallel ( example; gift cards )
        // TODO: Enumerating and then choosing values outside the range allowed through the frontend
    }
}
