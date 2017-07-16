package net.minesec.rules.core;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.openqa.selenium.WebDriver;

import java.util.function.Consumer;

/**
 * Copyright (c) 15-7-17, MineSec. All rights reserved.
 */
public interface TrafficRule {

    default void processRequest(HttpRequest request, Consumer<Consumer<WebDriver>> taskConsumer) {
        this.processRequest(request);
    }

    default void processResponse(HttpRequest request, HttpResponse response, Consumer<Consumer<WebDriver>> taskConsumer) {
        this.processResponse(request, response);
    }

    default void processRequest(HttpRequest request) {
    }

    default void processResponse(HttpRequest request, HttpResponse response) {
    }

}
