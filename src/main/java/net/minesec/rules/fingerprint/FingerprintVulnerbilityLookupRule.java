package net.minesec.rules.fingerprint;

import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class FingerprintVulnerbilityLookupRule implements Rule {
    @Override
    public Moment moment() {
        return Moment.SETUP;
    }

    @Override
    public void apply(Context ctx) {
        ctx.addMetaListener(FingerprintingRule.Meta.class, meta -> {
            // TODO: Vulnerability lookup for the respective technologies and versions
        });
    }
}
