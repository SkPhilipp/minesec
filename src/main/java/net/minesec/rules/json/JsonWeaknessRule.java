package net.minesec.rules.json;

import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class JsonWeaknessRule implements Rule {

    @Override
    public void onResponse(Context ctx) {
        // TODO[RULE]: Identify weaknesses (ie CORS-bypassing JSONP, or CORS-bypassing non-padded JSON)
    }
}
