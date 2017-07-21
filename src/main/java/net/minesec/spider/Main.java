package net.minesec.spider;

import net.minesec.rules.api.Context;
import net.minesec.rules.api.ContextBuilder;
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

    public static void main(String[] args) throws InterruptedException, RootCertificateException {
        RuleCallingHttpFiltersSource filtersSource = new RuleCallingHttpFiltersSource();
        // TODO: Start one proxy server per webdriver, to allow the request-bound contexts to always have a parent webdriver
        HttpProxyServer httpProxyServer = DefaultHttpProxyServer.bootstrap()
                .withAllowLocalOnly(true)
                .withTransparent(true)
                .withFiltersSource(filtersSource)
                .withManInTheMiddle(new CertificateSniffingMitmManager())
                .withPort(8080)
                .start();
        InetSocketAddress address = httpProxyServer.getListenAddress();
        String proxyAddress = String.format("%s:%d", address.getHostName(), address.getPort());
        WebDriverPool webDriverPool = new WebDriverPool(SESSIONS_LIMIT, proxyAddress);
        ContextBuilder contextBuilder = new ContextBuilderImpl(webDriverPool);
        filtersSource.setContextBuilder(contextBuilder);
        try {
            webDriverPool.queue(webDriver -> {
                webDriver.get(args[0]);
                WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
                webDriverWait.until(jsDriver -> ((JavascriptExecutor) jsDriver).executeScript("return document.readyState").equals("complete"));
                final Context context = contextBuilder.build(webDriver);
                Rules.invokeAll(Rule.Moment.PAGE_LOAD, context);
            });
            Thread.sleep(1000 * 60 * 60 * 24);
        } finally {
            webDriverPool.stop();
            httpProxyServer.stop();
        }
    }
}
