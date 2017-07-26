package net.minesec.spider;

import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.Getter;
import net.minesec.rules.api.Context;
import org.openqa.selenium.WebDriver;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class ContextImpl implements Context {

    private final WebDriverPool webDriverPool;
    private final OPartitionedDatabasePool oPartitionedDatabasePool;
    @Getter
    private final String id;
    @Getter
    private final Context parent;
    @Getter
    private WebDriver webDriver;
    @Getter
    private HttpRequest request;
    @Getter
    private HttpResponse response;

    public ContextImpl(WebDriverPool webDriverPool, OPartitionedDatabasePool oPartitionedDatabasePool, WebDriver webDriver) {
        this(webDriverPool, oPartitionedDatabasePool, null, webDriver, null, null);
    }

    ContextImpl(WebDriverPool webDriverPool, OPartitionedDatabasePool oPartitionedDatabasePool, Context parent, WebDriver webDriver, HttpRequest request, HttpResponse response) {
        this.webDriverPool = webDriverPool;
        this.oPartitionedDatabasePool = oPartitionedDatabasePool;
        this.parent = parent;
        this.webDriver = webDriver;
        this.request = request;
        this.response = response;
        this.id = UUID.randomUUID().toString();
    }

    public ContextImpl forRequest(HttpRequest request) {
        return new ContextImpl(webDriverPool, oPartitionedDatabasePool, this, webDriver, request, null);
    }

    public ContextImpl forResponse(HttpResponse response) {
        return new ContextImpl(webDriverPool, oPartitionedDatabasePool, this, webDriver, request, response);
    }

    @Override
    public void queue(Consumer<WebDriver> task) {
        this.webDriverPool.queue(task);
    }

    @Override
    public ODatabaseDocumentTx getDatabase() {
        return oPartitionedDatabasePool.acquire();
    }

}
