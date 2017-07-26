package net.minesec.rules.form;

import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class FormApiFuzzRule implements Rule {
    @Override
    public Moment moment() {
        return Moment.PAGE_LOAD;
    }

    @Override
    public void apply(Context ctx) {
        // TODO[RULE]: Form discovery and fuzzing
        // TODO[RULE]: Submitting a single-use request multiple times in parallel ( example; gift cards )
        // TODO[RULE]: Enumerating and then choosing values outside the range allowed through the frontend
    }
}
