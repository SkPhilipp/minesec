package net.minesec.rules.api;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * Copyright (c) 30-7-17, MineSec. All rights reserved.
 */
@Slf4j
public class Utilities {

    @FunctionalInterface
    public interface ThrowingConsumer<T> {
        void apply(T instance) throws Exception;
    }

    public static <T> Consumer<T> silenced(ThrowingConsumer<T> throwingConsumer) {
        return (instance) -> {
            try {
                throwingConsumer.apply(instance);
            } catch (Exception e) {
                log.error("Erred during execution of a consumer", e);
            }
        };
    }

}
