package net.minesec.rules.api;

import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;
import net.minesec.spider.WebDriverPool;
import org.openqa.selenium.WebDriver;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
@AllArgsConstructor
public class ContextImpl implements Context {

    @Delegate
    private final WebDriverPool webDriverPool;
    private final OPartitionedDatabasePool oPartitionedDatabasePool;
    @Getter
    @Setter
    private WebDriver webDriver;
    @Getter
    @Setter
    private HttpRequest request;
    @Getter
    @Setter
    private HttpResponse response;

    @Override
    public ODatabaseDocumentTx getDatabase() {
        return oPartitionedDatabasePool.acquire();
    }

}
