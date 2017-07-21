package net.minesec.rules.core;

import org.eclipse.jetty.util.BlockingArrayQueue;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static org.openqa.selenium.chrome.ChromeOptions.CAPABILITY;

/**
 * Copyright (c) 16-7-17, MineSec. All rights reserved.
 */
public class WebDriverPool {

    private static final String CAPABILITY_HEADLESS = "headless";

    static {
        System.setProperty("webdriver.chrome.driver", "/opt/chromedriver/chromedriver");
    }

    private final List<WebDriver> webDrivers;
    private final Queue<Consumer<WebDriver>> queue;
    private final Queue<WebDriver> ready;
    private final int sessionsLimit;
    private int sessionsActive;
    private final String proxyAddress;
    private final ExecutorService executorService;

    /**
     * @param sessionsLimit maximum amount of webdrivers
     * @param proxyAddress  address of a proxy all webdriver traffic should be routed through
     */
    public WebDriverPool(int sessionsLimit, String proxyAddress) {
        this.proxyAddress = proxyAddress;
        this.queue = new BlockingArrayQueue<>();
        this.ready = new BlockingArrayQueue<>();
        this.sessionsLimit = sessionsLimit;
        this.sessionsActive = 0;
        this.executorService = Executors.newCachedThreadPool();
        this.webDrivers = new ArrayList<>();
    }

    /**
     * @param task a task which essentially leases a webdriver. this task must
     *             return only when the webdriver is free to use for other tasks.
     */
    public void queue(Consumer<WebDriver> task) {
        WebDriver webDriver = this.ready.poll();
        if (webDriver == null) {
            if (this.sessionsActive < this.sessionsLimit) {
                webDriver = this.createWebDriver(this.proxyAddress);
                this.submit(task, webDriver);
            } else {
                this.queue.add(task);
            }
        } else {
            this.submit(task, webDriver);
        }
    }

    private void release(WebDriver webDriver) {
        Consumer<WebDriver> task = this.queue.poll();
        if (task == null) {
            this.ready.add(webDriver);
        } else {
            this.submit(task, webDriver);
        }
    }

    private void submit(Consumer<WebDriver> task, WebDriver webDriver) {
        this.executorService.submit(() -> {
            try {
                task.accept(webDriver);
            } finally {
                WebDriverPool.this.release(webDriver);
            }
        });
    }

    private static void requireCapabilityChromeHeadless(DesiredCapabilities capabilities) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(CAPABILITY_HEADLESS);
        capabilities.setCapability(CAPABILITY, chromeOptions);
    }

    private static void requireCapabilityProxy(String httpProxy, DesiredCapabilities capabilities) {
        Proxy proxy = new Proxy();
        proxy.setProxyType(Proxy.ProxyType.MANUAL);
        proxy.setSslProxy(httpProxy);
        capabilities.setCapability(CapabilityType.PROXY, proxy);
    }

    private WebDriver createWebDriver(String proxyAddress) {
        this.sessionsActive++;
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        requireCapabilityChromeHeadless(capabilities);
        requireCapabilityProxy(proxyAddress, capabilities);
        ChromeDriver chromeDriver = new ChromeDriver(ChromeDriverService.createDefaultService(), capabilities);
        this.webDrivers.add(chromeDriver);
        return chromeDriver;
    }

    /**
     * Quits all WebDrivers.
     */
    public void stop() {
        this.webDrivers.parallelStream().forEach(WebDriver::quit);
    }

}
