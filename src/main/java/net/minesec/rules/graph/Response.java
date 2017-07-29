package net.minesec.rules.graph;

import lombok.Data;

import java.util.Date;

/**
 * Copyright (c) 29-7-17, MineSec. All rights reserved.
 */
@Data
class Response {
    private Date moment;
    private String contextId;
    private Integer status;
}