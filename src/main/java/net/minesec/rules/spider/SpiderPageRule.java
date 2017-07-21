package net.minesec.rules.spider;

import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 16-7-17, MineSec. All rights reserved.
 */
public class SpiderPageRule implements Rule {

    private final Set<String> urls;

    public SpiderPageRule() {
        this.urls = new HashSet<>();
    }

    @Override
    public Moment moment() {
        return Moment.PAGE_LOAD;
    }

    @Override
    public void apply(Context ctx) {
        final WebDriver webDriver = ctx.getWebDriver();

        System.out.println(webDriver.getCurrentUrl());
        webDriver.findElements(By.cssSelector("button"));
        webDriver.findElements(By.cssSelector("form"));
        webDriver.findElements(By.cssSelector("[href]")).stream()
                .map(webElement -> webElement.getAttribute("href"))
                .forEach(href -> {
                    // TODO: Pathfinders (js-made requests, json, comments, [src], [href], [action]
                    // TODO: Use a domain whitelist
                    // TODO: Use a shared set of URLs
                    // TODO: Use a shared set of whitelisted domains
                    // TODO: Spider could identify page structure as not to crawl the same type of page more than N times
                    // TODO: Spider could identify login and registration forms and automatically authorize
                    if (!this.urls.contains(href)) {
                        this.urls.add(href);
                        ctx.queueTask(webDriver1 -> {
                            webDriver1.get(href);
                            WebDriverWait webDriverWait = new WebDriverWait(webDriver, 60);
                            webDriverWait.until(jsDriver -> ((JavascriptExecutor) jsDriver).executeScript("return document.readyState").equals("complete"));
                        });
                    }
                });
        List<WebElement> a2 = webDriver.findElements(By.cssSelector("[src]"));
        webDriver.findElements(By.cssSelector("[class^='btn']"));
        webDriver.findElements(By.cssSelector("[class^='button']"));
        webDriver.findElements(By.cssSelector("[onclick]"));
    }
}
