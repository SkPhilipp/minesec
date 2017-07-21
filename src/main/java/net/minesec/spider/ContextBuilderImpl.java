package net.minesec.spider;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.minesec.rules.api.Context;
import net.minesec.rules.api.ContextBuilder;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class ContextBuilderImpl implements ContextBuilder {

    private final WebDriverPool webDriverPool;
    private final Map<Class<?>, Object> meta;
    private final Map<Class<?>, List<Consumer<?>>> metaListeners;

    public ContextBuilderImpl(WebDriverPool webDriverPool) {
        this.webDriverPool = webDriverPool;
        this.meta = new HashMap<>();
        this.metaListeners = new HashMap<>();
    }

    private Context build(HttpRequest httpRequest, HttpResponse httpResponse, WebDriver webDriver) {
        final Map<Class<?>, Object> meta = new HashMap<>(this.meta);
        final Map<Class<?>, List<Consumer<?>>> metaListeners = new HashMap<>(this.metaListeners);
        final ContextImpl context = new ContextImpl(metaListeners,
                this.webDriverPool, webDriver,
                httpRequest, httpResponse);
        meta.forEach(context::setMeta);
        return context;
    }

    @Override
    public Context build() {
        return build(null, null, null);
    }

    @Override
    public Context build(HttpRequest httpRequest) {
        return build(httpRequest, null, null);
    }

    @Override
    public Context build(HttpRequest httpRequest, HttpResponse httpResponse) {
        return build(httpRequest, httpResponse, null);
    }

    @Override
    public Context build(WebDriver webDriver) {
        return build(null, null, webDriver);
    }

    @Override
    public <T> void addMetaListener(Class<T> type, Consumer<T> consumer) {
        this.metaListeners.computeIfAbsent(type, key -> new ArrayList<>()).add(consumer);
    }
}
