package net.minesec.rules.api;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.minesec.rules.api.impl.Pool;
import org.openqa.selenium.WebDriver;

import static net.minesec.rules.api.ContextBuilder.ContextEvent;

/**
 * Copyright (c) 20-7-17, MineSec. All rights reserved.
 */
public interface Context {

    /**
     * @return an instance of {@link Database}
     */
    Database getDatabase();

    /**
     * @return a {@link Pool} of {@link WebDriver}
     */
    Pool<WebDriver> getWebDriverPool();

    /**
     * @return a {@link WebDriver} intended for reading, to which this context is bound
     */
    WebDriver getWebDriver();

    /**
     * @return a {@link HttpRequest} intended for reading, to which this context is bound
     */
    HttpRequest getRequest();

    /**
     * @return a {@link HttpResponse} intended for reading, to which this context is bound
     */
    HttpResponse getResponse();

    /**
     * Dispatches an event, triggering all bound event listeners.
     */
    void dispatch(ContextEvent event);

    /**
     * @return the UUID bound to this context
     */
    String getId();

    /**
     * Contexts are hierarchical in nature.
     * <p>
     * Top level {@link Context}s are bound to a {@link WebDriver}
     * Next {@link Context}s are bound also to a {@link HttpRequest}
     * Next {@link Context}s are bound also to a {@link HttpResponse}
     *
     * @return the parent context, if any
     */
    Context getParent();

    /**
     * @return a new {@link Context} bound to the given parameter, and the same {@link WebDriver} as this
     */
    Context forRequest(HttpRequest request);

    /**
     * @param response {@link HttpResponse} to bind to
     * @return a new {@link Context} bound to the given parameter, and the same {@link WebDriver} and {@link HttpRequest} as this
     */
    Context forResponse(HttpResponse response);

    /**
     * Sets a {@link HttpResponse} to be sent to the client
     */
    void setMockResponse(HttpResponse mockResponse);

    /**
     * @return the mock response, if any has been set
     */
    HttpResponse getMockResponse();

}
