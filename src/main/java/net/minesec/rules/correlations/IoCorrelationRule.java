package net.minesec.rules.correlations;

import net.minesec.rules.Context;
import net.minesec.rules.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class IoCorrelationRule implements Rule {
    @Override
    public Moment moment() {
        return Moment.RESPONSE;
    }

    @Override
    public void apply(Context ctx) {
        // TODO: i/o registry(find matches between earlier requests and the response)
    }
}
