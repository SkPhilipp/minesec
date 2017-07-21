package net.minesec.rules.core;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.openqa.selenium.WebDriver;

import java.util.function.Consumer;

/**
 * Copyright (c) 20-7-17, MineSec. All rights reserved.
 */
public interface ContextBuilder {

    Context build();

    Context build(HttpRequest httpRequest);

    Context build(HttpRequest httpRequest, HttpResponse httpResponse);

    Context build(WebDriver webDriver);

    <T> void addMetaListener(Class<T> type, Consumer<T> consumer);

}
