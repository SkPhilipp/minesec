package net.minesec.rules.api;

import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.Getter;
import lombok.experimental.Delegate;
import net.minesec.spider.WebDriverPool;
import org.openqa.selenium.WebDriver;

import java.util.UUID;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class ContextImpl implements Context {

    @Delegate
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

    @Override
    public Context forRequest(HttpRequest request) {
        return new ContextImpl(webDriverPool, oPartitionedDatabasePool, this, webDriver, request, null);
    }

    @Override
    public Context forResponse(HttpResponse response) {
        return new ContextImpl(webDriverPool, oPartitionedDatabasePool, this, webDriver, request, response);
    }

    @Override
    public ODatabaseDocumentTx getDatabase() {
        return oPartitionedDatabasePool.acquire();
    }

}
