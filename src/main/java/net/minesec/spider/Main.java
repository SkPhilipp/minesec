package net.minesec.spider;

import net.minesec.rules.Rules;
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
        filtersSource.setTaskConsumer(webDriverPool::queue);
        try {
            webDriverPool.queue(webDriver -> {
                webDriver.get(args[0]);
                WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
                webDriverWait.until(jsDriver -> ((JavascriptExecutor) jsDriver).executeScript("return document.readyState").equals("complete"));
                Rules.PAGE_RULES.parallelStream().forEach(pageRule -> pageRule.process(webDriver, webDriverPool::queue));
            });
            Thread.sleep(1000 * 60 * 60 * 24);
        } finally {
            webDriverPool.stop();
            httpProxyServer.stop();
        }
    }
}
