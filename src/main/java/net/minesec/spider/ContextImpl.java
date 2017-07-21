package net.minesec.spider;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.minesec.rules.api.Context;
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
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

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
    public WebDriver getWebDriver() {
        return this.webdriver;
    }

    @Override
    public void queueTask(Consumer<WebDriver> task) {
        this.webDriverPool.queue(task);
    }

    @Override
    public HttpRequest getRequest() {
        return this.httpRequest;
    }

    @Override
    public HttpResponse getResponse() {
        return this.httpResponse;
    }

    @Override
    public void setRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public void setResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    @Override
    public <T> Optional<T> getMeta(Class<T> type) {
        return Optional.ofNullable((T) this.meta.get(type));
    }

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
