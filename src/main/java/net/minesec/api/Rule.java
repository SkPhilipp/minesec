package net.minesec.api;

/**
 * Copyright (c) 20-7-17, MineSec. All rights reserved.
 */
public interface Rule {

    enum Moment {

        //pre-request:
        //- mock responses
        //- deduplication
        //- url blocking
        BEFORE_REQUEST,

        //active rules, request-bound:
        //- fingerprinting ( wappalyzer, custom rules )
        //- search in public vulnerability databases for found technologies and versions
        //- weaknesses (ie JSONP / non-padded JSON to avoid CORS, ...)
        //- graph (neo4j, orientdb ; reconstructing APIs automatically)
        //- i/o registry(find matches between earlier requests and the response)
        AFTER_RESPONSE,

        //active rules, request-free:
        //- authorizers
        //- image limit
        //- json api fuzzers
        //- form api fuzzers
        //- pathfinders (js-made requests, json, comments, [src], [href], [action]
        //- submitting a zip-bomb
        //- submitting a single-use request multiple times in parallel ( example; gift cards )
        //- choosing values outside the range allowed through the frontend
        //- image size limit checker
        //- authorizing a browser (& blocking logout calls?, verifying email, verifying phone)
        AFTER_RESPONSE_PROCESSED,

        AFTER_PAGE_LOAD
    }


}
