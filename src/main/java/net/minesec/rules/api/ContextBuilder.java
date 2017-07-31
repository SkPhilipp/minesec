package net.minesec.rules.api;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.minesec.rules.api.impl.Pool;
import org.openqa.selenium.WebDriver;

import java.util.function.Consumer;

/**
 * Copyright (c) 27-7-17, MineSec. All rights reserved.
 */
public interface ContextBuilder {

    enum ContextEvent {
        /**
         * On creation of a {@link Context}
         */
        SETUP,
        /**
         * When a page requested by a {@link WebDriver} loads
         */
        PAGELOAD,
        /**
         * When a request is about to be sent to the target
         */
        REQUEST,
        /**
         * When a response is about to be sent to the client
         */
        RESPONSE
    }

    /**
     * @return an instance of {@link Database}
     */
    Database getDatabase();

    /**
     * Registers an event listener for all {@link Context}s built and to be built using this {@link ContextBuilder}
     */
    void addEventListener(ContextEvent event, Consumer<Context> listener);

    /**
     * Creates a new {@link Context} which may dispatch to all registered event listeners
     *
     * @return a new {@link Context}
     */
    Context build(Pool<WebDriver> webDriverPool, WebDriver webDriver, HttpRequest request, HttpResponse response);

}
