package net.minesec.rules.core;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.openqa.selenium.WebDriver;

import java.util.*;
import java.util.function.Consumer;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
class ContextImpl implements Context {

    private final Map<Class<?>, Object> meta;
    private final Map<Class<?>, List<Consumer<?>>> metaListeners;
    private final WebDriverPool webDriverPool;
    private final WebDriver webdriver;
    private final HttpRequest httpRequest;
    private final HttpResponse httpResponse;

    ContextImpl(Map<Class<?>, List<Consumer<?>>> metaListeners,
                WebDriverPool webDriverPool, WebDriver webdriver,
                HttpRequest httpRequest, HttpResponse httpResponse) {
        this.meta = new HashMap<>();
        this.metaListeners = metaListeners;
        this.webDriverPool = webDriverPool;
        this.webdriver = webdriver;
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
    }

    @Override
    public WebDriver webdriver() {
        return this.webdriver;
    }

    @Override
    public void queue(Consumer<WebDriver> task) {
        this.webDriverPool.queue(task);
    }

    @Override
    public HttpRequest request() {
        return this.httpRequest;
    }

    @Override
    public HttpResponse response() {
        return this.httpResponse;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Optional<T> getMeta(Class<T> type) {
        return Optional.ofNullable((T) this.meta.get(type));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void setMeta(Class<? extends T> type, T instance) {
        this.meta.put(type, instance);
        final List<Consumer<?>> list = this.metaListeners.getOrDefault(type, Collections.emptyList());
        for (Consumer<?> consumer : list) {
            ((Consumer<T>) consumer).accept(instance);
        }
    }

    @Override
    public <T> void addMetaListener(Class<T> type, Consumer<T> consumer) {
        this.metaListeners.computeIfAbsent(type, key -> new ArrayList<>()).add(consumer);
    }
}
