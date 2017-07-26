package net.minesec.rules.api;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.openqa.selenium.WebDriver;

import java.util.function.Consumer;

/**
 * Copyright (c) 20-7-17, MineSec. All rights reserved.
 */
public interface Context {

    enum type {
        PAGE, REQUEST, RESPONSE
    }

    WebDriver getWebDriver();

    void queue(Consumer<WebDriver> task);

    Context forRequest(HttpRequest request);

    HttpRequest getRequest();

    Context forResponse(HttpResponse response);

    HttpResponse getResponse();

    ODatabaseDocumentTx getDatabase();

    String getId();

    Context getParent();

    // TODO[CORE]: Create API to add a Finding, auto-create Browser, PageLoad, Request, Response, Run and Target.
}
