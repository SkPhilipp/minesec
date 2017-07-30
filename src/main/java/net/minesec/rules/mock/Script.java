package net.minesec.rules.mock;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;


/**
 * Copyright (c) 29-7-17, MineSec. All rights reserved.
 */
@Data
@DatabaseTable
class Script {
    @DatabaseField(canBeNull = false)
    private String content;
}
