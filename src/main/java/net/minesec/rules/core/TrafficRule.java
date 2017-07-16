package net.minesec.rules.core;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * Copyright (c) 15-7-17, MineSec. All rights reserved.
 */
public interface TrafficRule {

    default void processRequest(HttpRequest request) {
    }

    default void processResponse(HttpRequest request, HttpResponse response) {
    }

}
