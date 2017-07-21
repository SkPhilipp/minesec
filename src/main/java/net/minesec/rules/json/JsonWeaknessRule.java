package net.minesec.rules.json;

import net.minesec.rules.Context;
import net.minesec.rules.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class JsonWeaknessRule implements Rule {
    @Override
    public Moment moment() {
        return Moment.RESPONSE;
    }

    @Override
    public void apply(Context ctx) {
        // TODO: Identify weaknesses (ie CORS-bypassing JSONP, or CORS-bypassing non-padded JSON)
    }
}
