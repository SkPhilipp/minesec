package net.minesec.rules.compression;

import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class ZipBombRule implements Rule {
    @Override
    public Moment moment() {
        return Moment.SETUP;
    }

    @Override
    public void apply(Context ctx) {
        // TODO: Submitting a zip-bomb
    }
}
