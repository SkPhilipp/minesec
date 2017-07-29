package net.minesec.rules.authorization;

import lombok.Data;

import java.util.Date;

/**
 * Copyright (c) 29-7-17, MineSec. All rights reserved.
 */
@Data
class CookieChange {
    private Date moment;
    private String cookie;
}
