package net.minesec.rules.api;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

import java.util.function.Consumer;

/**
 * Copyright (c) 27-7-17, MineSec. All rights reserved.
 */
// TODO[CORE]: Remove Rule class, replace with Consumer<ContextBuilder>
public interface ContextBuilder {

    ODatabaseDocumentTx getDatabase();

    /**
     * Invoke during context creation, before processing any request or response.
     */
    void onSetup(Consumer<Context> rule);

    /**
     * Before a request is forwarded, a rule may inspect it and even mock a response.
     */
    void onRequest(Consumer<Context> rule);

    /**
     * After a response is received, a rule may inspect it and its respective request, and even mock a response.
     */
    void onResponse(Consumer<Context> rule);

    /**
     * Upon page load, a rule may inspect the webpage using the connected {@link org.openqa.selenium.WebDriver}.
     */
    void onPageLoad(Consumer<Context> rule);

}
