package net.minesec.rules.api.model;

import lombok.Data;

import java.util.Date;

/**
 * Copyright (c) 26-7-17, MineSec. All rights reserved.
 */
@Data
public class Finding {

    enum Level {
        ONE, TWO, THREE, FOUR, FIVE
    }

    private String contextId;
    private Date moment;
    private String type;
    private Level level;
    private String trigger;

}
