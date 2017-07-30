package net.minesec.rules.authorization;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;

import java.util.Date;

/**
 * Copyright (c) 29-7-17, MineSec. All rights reserved.
 */
@Data
@DatabaseTable
class CookieChange {
    @DatabaseField(canBeNull = false)
    private Date moment;
    @DatabaseField(canBeNull = false)
    private String cookie;
}
