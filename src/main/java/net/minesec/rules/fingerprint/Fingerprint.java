package net.minesec.rules.fingerprint;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;

/**
 * Copyright (c) 29-7-17, MineSec. All rights reserved.
 */
@Data
@DatabaseTable
class Fingerprint {
    @DatabaseField(canBeNull = false)
    private String technology;
    @DatabaseField(canBeNull = false)
    private String trigger;
}
