package net.minesec.rules.api;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.openqa.selenium.WebDriver;

import static net.minesec.rules.api.ContextBuilder.ContextEvent;

/**
 * Copyright (c) 20-7-17, MineSec. All rights reserved.
 */
public interface Context {

    Pool<WebDriver> getWebDriverPool();

    WebDriver getWebDriver();

    HttpRequest getRequest();

    HttpResponse getResponse();

    void dispatch(ContextEvent event);

    ODatabaseDocumentTx getDatabase();

    String getId();

    Context getParent();

    Context forRequest(HttpRequest request);

    Context forResponse(HttpResponse request);

}
