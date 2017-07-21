package net.minesec.rules.flash;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import lombok.Data;
import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class CrossdomainRule implements Rule {

    @Override
    public Moment moment() {
        return Moment.SETUP;
    }

    @Override
    public void apply(Context ctx) {
        // TODO: Inspect crossdomain.xml
    }
}
