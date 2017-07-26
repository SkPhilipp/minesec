package net.minesec.rules.api.model;

import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * Copyright (c) 26-7-17, MineSec. All rights reserved.
 */
@Data
public class PageLoad {

    private String contextId;
    private Date moment;
    private String link;
    private Set<String> links;

}
