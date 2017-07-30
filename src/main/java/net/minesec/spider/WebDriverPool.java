package net.minesec.spider;

import net.minesec.rules.api.ContextBuilder;
import net.minesec.rules.api.impl.Pool;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.MitmManager;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.littleshoot.proxy.mitm.CertificateSniffingMitmManager;
import org.littleshoot.proxy.mitm.RootCertificateException;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.Closeable;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.chrome.ChromeOptions.CAPABILITY;

/**
 * Copyright (c) 16-7-17, MineSec. All rights reserved.
 */
public class WebDriverPool extends Pool<WebDriver> implements Closeable {

    private static final String CAPABILITY_HEADLESS = "headless";

    static {
        System.setProperty("webdriver.chrome.driver", "/opt/chromedriver/chromedriver");
    }

    private final List<WebDriver> webdrivers;
    private final List<HttpProxyServer> httpProxyServers;
    private final int basePort;
    private final MitmManager mitmManager;
    private final ContextBuilder contextBuilder;

    WebDriverPool(int basePort, int limit, ContextBuilder contextBuilder) throws RootCertificateException {
        super(limit);
        this.basePort = basePort;
        this.mitmManager = new CertificateSniffingMitmManager();
        this.contextBuilder = contextBuilder;
        this.webdrivers = new ArrayList<>();
        this.httpProxyServers = new ArrayList<>();
    }

    protected WebDriver create() {
        RuleCallingHttpFiltersSource httpFiltersSource = new RuleCallingHttpFiltersSource();
        final HttpProxyServer httpProxyServer = DefaultHttpProxyServer.bootstrap()
                .withAllowLocalOnly(true)
                .withTransparent(true)
                .withFiltersSource(httpFiltersSource)
                .withManInTheMiddle(mitmManager)
                .withPort(0)
                .start();
        final InetSocketAddress address = httpProxyServer.getListenAddress();
        final String proxyAddress = String.format("%s:%d", address.getHostName(), address.getPort());
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(CAPABILITY_HEADLESS);
        final Proxy proxy = new Proxy();
        proxy.setProxyType(Proxy.ProxyType.MANUAL);
        proxy.setSslProxy(proxyAddress);
        final DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(CAPABILITY, chromeOptions);
        capabilities.setCapability(CapabilityType.PROXY, proxy);
        final ChromeDriver chromeDriver = new ChromeDriver(ChromeDriverService.createDefaultService(), capabilities);
        httpFiltersSource.setContextSupplier(() -> contextBuilder.build(this, chromeDriver, null, null));
        this.httpProxyServers.add(httpProxyServer);
        this.webdrivers.add(chromeDriver);
        return chromeDriver;
    }

    /**
     * Quits all WebDrivers and HttpProxyServers.
     */
    public void close() {
        this.webdrivers.parallelStream().forEach(WebDriver::quit);
        this.httpProxyServers.parallelStream().forEach(HttpProxyServer::stop);
    }

}
