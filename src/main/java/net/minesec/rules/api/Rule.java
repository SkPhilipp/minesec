package net.minesec.rules.api;

/**
 * Copyright (c) 20-7-17, MineSec. All rights reserved.
 */
public interface Rule {

    enum Moment {
        /**
         * Invoke during context creation, before processing any request or response.
         */
        SETUP,
        /**
         * Before a request is forwarded, a rule may inspect it and even mock a response.
         */
        REQUEST,
        /**
         * After a response is received, a rule may inspect it and its respective request, and even mock a response.
         */
        RESPONSE,
        /**
         * Upon page load, a rule may inspect the webpage using the connected {@link org.openqa.selenium.WebDriver}.
         */
        PAGE_LOAD
    }

    Moment moment();

    void apply(Context ctx);

}
