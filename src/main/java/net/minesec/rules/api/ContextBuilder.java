package net.minesec.rules.api;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.minesec.spider.WebDriverPool;
import org.openqa.selenium.WebDriver;

import java.util.function.Consumer;

/**
 * Copyright (c) 27-7-17, MineSec. All rights reserved.
 */
public interface ContextBuilder {

    enum ContextEvent {
        SETUP,
        PAGELOAD,
        REQUEST,
        RESPONSE
    }

    ODatabaseDocumentTx getDatabase();

    void on(ContextEvent event, Consumer<Context> rule);

    Context build(WebDriverPool webDriverPool, WebDriver webDriver, HttpRequest request, HttpResponse response);

}
