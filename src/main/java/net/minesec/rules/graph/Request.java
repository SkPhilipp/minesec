package net.minesec.rules.graph;

import lombok.Data;

import java.util.Date;

/**
 * Copyright (c) 26-7-17, MineSec. All rights reserved.
 */
@Data
class Request {
    private String contextId;
    private Date moment;
    private String contentType;
    private String url;
}
