package net.minesec.rules.clickjacking;

import lombok.Data;

/**
 * Copyright (c) 29-7-17, MineSec. All rights reserved.
 */
@Data
class ClickjackablePage {
    private String url;
    private String trigger;
    private String frameOptions;
}
