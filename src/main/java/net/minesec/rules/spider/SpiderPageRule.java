package net.minesec.rules.spider;

import net.minesec.rules.api.Context;
import net.minesec.rules.api.ContextBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.PAGELOAD;

/**
 * Copyright (c) 16-7-17, MineSec. All rights reserved.
 */
public class SpiderPageRule implements Consumer<ContextBuilder> {

    private static final Set<String> URLS = new HashSet<>();

    @Override
    public void accept(ContextBuilder contextBuilder) {
        contextBuilder.addEventListener(PAGELOAD, ctx -> {
            final WebDriver sourceWebDriver = ctx.getWebDriver();
            System.out.println(sourceWebDriver + " " + sourceWebDriver.getCurrentUrl());
            sourceWebDriver.findElements(By.cssSelector("button"));
            sourceWebDriver.findElements(By.cssSelector("form"));
            sourceWebDriver.findElements(By.cssSelector("[href]")).stream()
                    .map(webElement -> webElement.getAttribute("href"))
                    .forEach(href -> {
                        // TODO: Use a shared domains whitelist & accessed URLs
                        if (!URLS.contains(href)) {
                            URLS.add(href);
                            ctx.getWebDriverPool().queue(webDriver -> {
                                System.out.println(webDriver + " " + webDriver.getCurrentUrl());
                                webDriver.get(href);
                                WebDriverWait webDriverWait = new WebDriverWait(sourceWebDriver, 60);
                                webDriverWait.until(jsDriver -> ((JavascriptExecutor) jsDriver).executeScript("return document.readyState").equals("complete"));
                                final Context build = contextBuilder.build(ctx.getWebDriverPool(), webDriver, null, null);
                                build.dispatch(PAGELOAD);
                            });
                        }
                    });
            sourceWebDriver.findElements(By.cssSelector("[src]"));
            sourceWebDriver.findElements(By.cssSelector("[class^='btn']"));
            sourceWebDriver.findElements(By.cssSelector("[class^='button']"));
            sourceWebDriver.findElements(By.cssSelector("[onclick]"));
        });
    }

}
