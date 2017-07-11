package net.minesec.commands.explore;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersSource;

/**
 * Copyright (c) 11-7-17, MineSec. All rights reserved.
 * <p>
 * Implements 100 MB per-request buffers
 */
public class ExploreHttpFiltersSource implements HttpFiltersSource {

    private static final int MAXIMUM_BUFFER_SIZE_IN_BYTES_REQUEST = 1024 * 1024 * 100;
    private static final int MAXIMUM_BUFFER_SIZE_IN_BYTES_RESPONSE = 1024 * 1024 * 100;

    @Override
    public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
        return new ExploreHttpFilters(originalRequest, ctx);
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
