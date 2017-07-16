package net.minesec.rules.spider;

import net.minesec.rules.core.PageRule;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Copyright (c) 16-7-17, MineSec. All rights reserved.
 */
public class SpiderPageRule implements PageRule {

    // TODO: Implement domain filtering, allow setup from global context?
    private final Set<String> domains;
    private final Set<String> urls;

    public SpiderPageRule() {
        this.domains = new HashSet<>();
        this.urls = new HashSet<>();
    }

    @Override
    public void process(WebDriver webDriver, Consumer<Consumer<WebDriver>> taskConsumer) {
        webDriver.findElements(By.cssSelector("button"));
        webDriver.findElements(By.cssSelector("form"));
        webDriver.findElements(By.cssSelector("[href]")).stream()
                .map(webElement -> webElement.getAttribute("href"))
                .forEach(href -> {
                    if (!this.urls.contains(href)) {
                        System.out.println(href);
                        this.urls.add(href);
                        taskConsumer.accept(webDriver1 -> {
                            webDriver1.get(href);
                            WebDriverWait webDriverWait = new WebDriverWait(webDriver, 60);
                            webDriverWait.until(jsDriver -> ((JavascriptExecutor) jsDriver).executeScript("return document.readyState").equals("complete"));
                        });
                    } else {
                        System.out.println("Duplicate: " + href);
                    }
                });
        List<WebElement> a2 = webDriver.findElements(By.cssSelector("[src]"));
        a2.forEach(webElement -> System.out.println(webElement.getAttribute("src")));
        webDriver.findElements(By.cssSelector("[class^='btn']"));
        webDriver.findElements(By.cssSelector("[class^='button']"));
        webDriver.findElements(By.cssSelector("[onclick]"));
    }
}
