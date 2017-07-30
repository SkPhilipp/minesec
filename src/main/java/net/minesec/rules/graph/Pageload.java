package net.minesec.rules.graph;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;

import java.util.Date;

/**
 * Copyright (c) 29-7-17, MineSec. All rights reserved.
 */
@Data
@DatabaseTable
class Pageload {
    @DatabaseField(canBeNull = false)
    private String contextId;
    @DatabaseField(canBeNull = false)
    private Date moment;
}
