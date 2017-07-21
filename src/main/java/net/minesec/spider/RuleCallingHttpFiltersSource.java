package net.minesec.spider;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import net.minesec.rules.core.ContextBuilder;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersSource;

/**
 * Copyright (c) 11-7-17, MineSec. All rights reserved.
 * <p>
 * Implements 100 MB per-request buffers
 */
class RuleCallingHttpFiltersSource implements HttpFiltersSource {

    private static final int MAXIMUM_BUFFER_SIZE_IN_BYTES_REQUEST = 1024 * 1024 * 100;
    private static final int MAXIMUM_BUFFER_SIZE_IN_BYTES_RESPONSE = 1024 * 1024 * 100;
    private ContextBuilder contextBuilder;

    @Override
    public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
        return new RuleCallingHttpFilter(originalRequest, ctx, this.contextBuilder.build());
    }

    @Override
    public int getMaximumRequestBufferSizeInBytes() {
        return MAXIMUM_BUFFER_SIZE_IN_BYTES_REQUEST;
    }

    @Override
    public int getMaximumResponseBufferSizeInBytes() {
        return MAXIMUM_BUFFER_SIZE_IN_BYTES_RESPONSE;
    }

    void setContextBuilder(ContextBuilder contextBuilder) {
        this.contextBuilder = contextBuilder;
    }
}
