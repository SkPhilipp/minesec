package net.minesec.rules.clickjacking;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;

/**
 * Copyright (c) 29-7-17, MineSec. All rights reserved.
 */
@Data
@DatabaseTable
class ClickjackablePage {
    @DatabaseField(canBeNull = false)
    private String url;
    @DatabaseField(canBeNull = false)
    private String trigger;
    @DatabaseField
    private String frameOptions;
}
