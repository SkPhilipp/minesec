package net.minesec.commands.shared;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import org.junit.Test;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersSource;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.littleshoot.proxy.mitm.CertificateSniffingMitmManager;
import org.littleshoot.proxy.mitm.RootCertificateException;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.openqa.selenium.chrome.ChromeOptions.CAPABILITY;

/**
 * Copyright (c) 10-7-17, MineSec. All rights reserved.
 */
public class ExploreTest {

    @Test
    public void test() throws InterruptedException, RootCertificateException {
        HttpProxyServer server =
                DefaultHttpProxyServer.bootstrap()
                        .withFiltersSource(new HttpFiltersSource() {
                            @Override
                            public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
                                System.out.println(" WEW LADDD");
                                System.out.println(originalRequest.getUri());
                                return null;
                            }

                            @Override
                            public int getMaximumRequestBufferSizeInBytes() {
                                return 0;
                            }

                            @Override
                            public int getMaximumResponseBufferSizeInBytes() {
                                return 0;
                            }
                        })
                        .withManInTheMiddle(new CertificateSniffingMitmManager())
                        .withPort(8080)
                        .start();

        // Optional, if not specified, WebDriver will search your path for chromedriver.
        System.setProperty("webdriver.chrome.driver", "/opt/chromedriver/chromedriver");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("headless");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(CAPABILITY, chromeOptions);
        Proxy proxy = new Proxy();
        proxy.setProxyType(Proxy.ProxyType.MANUAL);
        System.out.println();
        String httpProxy = server.getListenAddress().getHostName() + ":" + server.getListenAddress().getPort();
//        proxy.setHttpProxy(httpProxy);
        proxy.setSslProxy(httpProxy);
//        proxy.setFtpProxy(ftpProxy);
        capabilities.setCapability(CapabilityType.PROXY, proxy);
        WebDriver driver = new ChromeDriver(ChromeDriverService.createDefaultService(), capabilities);
        driver.get("http://www.google.com/xhtml");
        Thread.sleep(5000);  // Let the user actually see something!
        System.out.println(driver.getTitle());
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("ChromeDriver");
        searchBox.submit();
        Thread.sleep(5000);  // Let the user actually see something!
        System.out.println(driver.getTitle());
        driver.quit();
    }

    @Test
    public void setup() throws RootCertificateException, InterruptedException {
        HttpProxyServer server =
                DefaultHttpProxyServer.bootstrap()
                        .withManInTheMiddle(new CertificateSniffingMitmManager())
                        .withPort(8080)
                        .start();
        System.out.println("shields up");
        Thread.sleep(600000);
        // this created littleproxy-mitm.pem & .p12 files in root of project:
        // sudo apt-get install libnss3-tools
        // certutil -d sql:$HOME/.pki/nssdb -A -t TC -n "MineSec" -i ./littleproxy-mitm.pem
    }
}

