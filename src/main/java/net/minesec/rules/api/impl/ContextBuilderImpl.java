package net.minesec.rules.api.impl;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.Getter;
import lombok.Setter;
import net.minesec.rules.api.Context;
import net.minesec.rules.api.ContextBuilder;
import net.minesec.rules.api.Database;
import org.openqa.selenium.WebDriver;

import java.util.*;
import java.util.function.Consumer;

/**
 * Copyright (c) 29-7-17, MineSec. All rights reserved.
 */
public class ContextBuilderImpl implements ContextBuilder {

    private final Database database;
    private final Map<ContextEvent, List<Consumer<Context>>> eventHandlers;

    public ContextBuilderImpl(Database database) {
        this.database = database;
        this.eventHandlers = new HashMap<>();
    }

    @Getter
    public class ContextImpl implements Context {

        private final Pool<WebDriver> webDriverPool;
        private final WebDriver webDriver;
        private final String id;
        private final Context parent;
        private final HttpRequest request;
        private final HttpResponse response;
        @Setter
        private HttpResponse mockResponse;

        ContextImpl(Pool<WebDriver> webDriverPool, Context parent, WebDriver webDriver, HttpRequest request, HttpResponse response) {
            this.webDriverPool = webDriverPool;
            this.parent = parent;
            this.webDriver = webDriver;
            this.request = request;
            this.response = response;
            this.id = UUID.randomUUID().toString();
        }

        @Override
        public ContextImpl forRequest(HttpRequest request) {
            return new ContextImpl(webDriverPool, this, webDriver, request, null);
        }

        @Override

        public ContextImpl forResponse(HttpResponse response) {
            return new ContextImpl(webDriverPool, this, webDriver, request, response);
        }

        @Override
        public void dispatch(ContextEvent event) {
            final List<Consumer<Context>> consumers = eventHandlers.get(event);
            consumers.parallelStream().forEach(consumer -> consumer.accept(this));
        }

        @Override
        public Database getDatabase() {
            return database;
        }
    }

    @Override
    public Database getDatabase() {
        return database;
    }

    @Override
    public void addEventListener(ContextEvent event, Consumer<Context> listener) {
        eventHandlers.computeIfAbsent(event, (ignored) -> new ArrayList<>()).add(listener);
    }

    @Override
    public Context build(Pool<WebDriver> webDriverPool, WebDriver webDriver, HttpRequest request, HttpResponse response) {
        return new ContextImpl(webDriverPool, null, webDriver, request, response);
    }
}
