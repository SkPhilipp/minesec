package net.minesec.spider;

import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePoolFactory;
import net.minesec.rules.Rules;
import net.minesec.rules.api.Context;
import org.littleshoot.proxy.MitmManager;
import org.littleshoot.proxy.mitm.CertificateSniffingMitmManager;
import org.littleshoot.proxy.mitm.RootCertificateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Copyright (c) 10-7-17, MineSec. All rights reserved.
 */
class Main {

    private static final int SESSIONS_LIMIT = 10;
    private static final int DB_POOL_LIMIT = 100;

    public static void main(String[] args) throws InterruptedException, RootCertificateException {
        final MitmManager mitmManager = new CertificateSniffingMitmManager();
        final RuleCallingHttpFiltersSource httpFiltersSource = new RuleCallingHttpFiltersSource();
        final OPartitionedDatabasePoolFactory dbPoolFactory = new OPartitionedDatabasePoolFactory(DB_POOL_LIMIT);
        dbPoolFactory.setMaxPoolSize(DB_POOL_LIMIT);
        final OPartitionedDatabasePool dbPool = dbPoolFactory.get("remote:localhost/minesec", "admin", "admin");
        try (WebDriverPool webDriverPool = new WebDriverPool(8080, SESSIONS_LIMIT, mitmManager, httpFiltersSource)) {
            // TODO[CORE]: With proxy-per-webdriver, ensure all request/response based
            httpFiltersSource.setContextBuilder(() -> new ContextImpl(webDriverPool, dbPool, null));
            webDriverPool.queue(webDriver -> {
                webDriver.get(args[0]);
                WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
                webDriverWait.until(jsDriver -> ((JavascriptExecutor) jsDriver).executeScript("return document.readyState").equals("complete"));
                final Context context = new ContextImpl(webDriverPool, dbPool, webDriver);
                Rules.invokeAll(rule -> rule.onPageLoad(context));
            });
            Thread.sleep(1000 * 60 * 60 * 24);
        } finally {
            dbPool.close();
        }
    }
}
