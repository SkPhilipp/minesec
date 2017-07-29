package net.minesec.rules.image;

import net.minesec.rules.api.ContextBuilder;

import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.RESPONSE;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class ImageRule implements Consumer<ContextBuilder> {

    @Override
    public void accept(ContextBuilder contextBuilder) {
        contextBuilder.addEventListener(RESPONSE, ctx -> {
            // TODO: Implement this rule
            // Check sizes of images and look for hints in the request to whether the size is variable
            // Perform requests for larger images
        });
    }

}
