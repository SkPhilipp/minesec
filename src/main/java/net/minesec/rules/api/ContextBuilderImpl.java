package net.minesec.rules.api;

import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.Getter;
import net.minesec.spider.WebDriverPool;
import org.openqa.selenium.WebDriver;

import java.util.*;
import java.util.function.Consumer;

/**
 * Copyright (c) 29-7-17, MineSec. All rights reserved.
 */
public class ContextBuilderImpl implements ContextBuilder {

    private final OPartitionedDatabasePool oPartitionedDatabasePool;
    private final Map<ContextEvent, List<Consumer<Context>>> eventHandlers;

    public ContextBuilderImpl(OPartitionedDatabasePool oPartitionedDatabasePool) {
        this.oPartitionedDatabasePool = oPartitionedDatabasePool;
        this.eventHandlers = new HashMap<>();
    }

    @Getter
    public class ContextImpl implements Context {

        private final Pool<WebDriver> webDriverPool;
        private final WebDriver webDriver;
        private final String id;
        private final Context parent;
        private final HttpRequest request;
        private final HttpResponse response;

        ContextImpl(Pool<WebDriver> webDriverPool, Context parent, WebDriver webDriver, HttpRequest request, HttpResponse response) {
            this.webDriverPool = webDriverPool;
            this.parent = parent;
            this.webDriver = webDriver;
            this.request = request;
            this.response = response;
            this.id = UUID.randomUUID().toString();
        }

        @Override
        public ContextImpl forRequest(HttpRequest request) {
            return new ContextImpl(webDriverPool, this, webDriver, request, null);
        }

        @Override

        public ContextImpl forResponse(HttpResponse response) {
            return new ContextImpl(webDriverPool, this, webDriver, request, response);
        }

        @Override
        public void dispatch(ContextEvent event) {
            final List<Consumer<Context>> consumers = eventHandlers.get(event);
            consumers.parallelStream().forEach(consumer -> consumer.accept(this));
        }

        @Override
        public ODatabaseDocumentTx getDatabase() {
            return oPartitionedDatabasePool.acquire();
        }

    }

    @Override
    public ODatabaseDocumentTx getDatabase() {
        return oPartitionedDatabasePool.acquire();
    }

    @Override
    public void on(ContextEvent event, Consumer<Context> rule) {
        eventHandlers.computeIfAbsent(event, (ignored) -> new ArrayList<>()).add(rule);
    }

    @Override
    public Context build(WebDriverPool webDriverPool, WebDriver webDriver, HttpRequest request, HttpResponse response) {
        return new ContextImpl(webDriverPool, null, webDriver, request, response);
    }
}
