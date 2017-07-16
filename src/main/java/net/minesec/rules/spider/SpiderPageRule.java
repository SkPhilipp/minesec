package net.minesec.rules.spider;

import net.minesec.rules.core.PageRule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Copyright (c) 16-7-17, MineSec. All rights reserved.
 */
public class SpiderPageRule implements PageRule {

    @Override
    public void process(WebDriver webDriver) {
        webDriver.findElements(By.cssSelector("button"));
        webDriver.findElements(By.cssSelector("form"));
        List<WebElement> a = webDriver.findElements(By.cssSelector("a"));
        a.forEach(webElement -> System.out.println(webElement.getAttribute("href")));
        webDriver.findElements(By.cssSelector("[class^='btn']"));
        webDriver.findElements(By.cssSelector("[class^='button']"));
        webDriver.findElements(By.cssSelector("[onclick]"));
    }
}
