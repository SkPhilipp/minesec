package net.minesec.rules.core;

import org.openqa.selenium.WebDriver;

import java.util.function.Consumer;

/**
 * Copyright (c) 15-7-17, MineSec. All rights reserved.
 */
public interface PageRule {

    default void process(WebDriver webDriver, Consumer<Consumer<WebDriver>> taskConsumer) {
        this.process(webDriver);
    }

    default void process(WebDriver webDriver) {
    }

}
