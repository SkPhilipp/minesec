package net.minesec.spider;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersSource;
import org.openqa.selenium.WebDriver;

import java.util.function.Consumer;

/**
 * Copyright (c) 11-7-17, MineSec. All rights reserved.
 * <p>
 * Implements 100 MB per-request buffers
 */
class RuleCallingHttpFiltersSource implements HttpFiltersSource {

    private static final int MAXIMUM_BUFFER_SIZE_IN_BYTES_REQUEST = 1024 * 1024 * 100;
    private static final int MAXIMUM_BUFFER_SIZE_IN_BYTES_RESPONSE = 1024 * 1024 * 100;

    private Consumer<Consumer<WebDriver>> taskConsumer = task -> {
    };

    public void setTaskConsumer(Consumer<Consumer<WebDriver>> taskConsumer) {
        this.taskConsumer = taskConsumer;
    }

    @Override
    public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
        return new RuleCallingHttpFilter(originalRequest, ctx, this.taskConsumer);
    }

    @Override
    public int getMaximumRequestBufferSizeInBytes() {
        return MAXIMUM_BUFFER_SIZE_IN_BYTES_REQUEST;
    }

    @Override
    public int getMaximumResponseBufferSizeInBytes() {
        return MAXIMUM_BUFFER_SIZE_IN_BYTES_RESPONSE;
    }

}
