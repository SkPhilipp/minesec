package net.minesec.spider;

import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePoolFactory;
import net.minesec.rules.Rules;
import net.minesec.rules.api.Context;
import net.minesec.rules.api.ContextImpl;
import net.minesec.rules.api.Rule;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.littleshoot.proxy.mitm.CertificateSniffingMitmManager;
import org.littleshoot.proxy.mitm.RootCertificateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.InetSocketAddress;

/**
 * Copyright (c) 10-7-17, MineSec. All rights reserved.
 */
class Main {

    private static final int SESSIONS_LIMIT = 10;
    private static final int DB_POOL_LIMIT = 100;

    public static void main(String[] args) throws InterruptedException, RootCertificateException {
        RuleCallingHttpFiltersSource filtersSource = new RuleCallingHttpFiltersSource();
        // TODO[CORE]: Start one proxy server per webdriver, to allow the request-bound contexts to always have a parent webdriver
        HttpProxyServer httpProxyServer = DefaultHttpProxyServer.bootstrap()
                .withAllowLocalOnly(true)
                .withTransparent(true)
                .withFiltersSource(filtersSource)
                .withManInTheMiddle(new CertificateSniffingMitmManager())
                .withPort(8080)
                .start();
        InetSocketAddress address = httpProxyServer.getListenAddress();
        String proxyAddress = String.format("%s:%d", address.getHostName(), address.getPort());
        final OPartitionedDatabasePoolFactory dbPoolFactory = new OPartitionedDatabasePoolFactory();
        dbPoolFactory.setMaxPoolSize(DB_POOL_LIMIT);
        final OPartitionedDatabasePool dbPool = dbPoolFactory.get("remote:localhost/minesec", "admin", "admin");
        try (WebDriverPool webDriverPool = new WebDriverPool(SESSIONS_LIMIT, proxyAddress)) {
            // TODO[CORE]: With proxy-per-webdriver, assign a webDriver here
            filtersSource.setContextBuilder(() -> new ContextImpl(webDriverPool, dbPool, null));
            webDriverPool.queue(webDriver -> {
                webDriver.get(args[0]);
                WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
                webDriverWait.until(jsDriver -> ((JavascriptExecutor) jsDriver).executeScript("return document.readyState").equals("complete"));
                final Context context = new ContextImpl(webDriverPool, dbPool, webDriver);
                Rules.invokeAll(Rule.Moment.PAGE_LOAD, context);
            });
            Thread.sleep(1000 * 60 * 60 * 24);
        } finally {
            dbPool.close();
            httpProxyServer.stop();
        }
    }
}
