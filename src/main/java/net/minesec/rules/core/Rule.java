package net.minesec.rules.core;

/**
 * Copyright (c) 20-7-17, MineSec. All rights reserved.
 */
public interface Rule {

    enum Moment {
        BEFORE_REQUEST,
        AFTER_RESPONSE,
        AFTER_RESPONSE_PROCESSED,
        AFTER_PAGE_LOAD
    }

    Moment moment();

    void apply(Context ctx);

}
