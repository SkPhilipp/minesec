package net.minesec.rules.api;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.openqa.selenium.WebDriver;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Copyright (c) 20-7-17, MineSec. All rights reserved.
 */
public interface Context {

    WebDriver getWebDriver();

    void queue(Consumer<WebDriver> task);

    HttpRequest getRequest();

    HttpResponse getResponse();

    ODatabaseDocumentTx getDatabase();

}
