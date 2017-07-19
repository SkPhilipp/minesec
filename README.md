
# TODOs:

## Must have

- Vulnerable test sites
- Architecture implementation

## Nice to have

- Git bisect integration

## Uncategorized ideas

- Bug bounty indexer should use search engines alongside existing indexes
- Spider could identify page structure as not to crawl the same type of page more than N times
- Spider could identify login and registration forms and automatically authorize

# Development

## Installation:

    # chromedriver
    sudo apt-get install curl
    PLATFORM=linux64
    VERSION=$(curl http://chromedriver.storage.googleapis.com/LATEST_RELEASE)
    curl http://chromedriver.storage.googleapis.com/$VERSION/chromedriver_$PLATFORM.zip > chromedriver.zip
    unzip chromedriver.zip
    sudo mkdir /opt/chromedriver
    sudo mv chromedriver /opt/chromedriver/chromedriver
    chmod +x /opt/chromedriver/chromedriver
    rm chromedriver.zip

    # certificate utilities
    sudo apt-get install libnss3-tools
    certutil -d sql:$HOME/.pki/nssdb -A -t TC -n "MineSec" -i ./littleproxy-mitm.pem

## Utilities

    gradle run -Pcrawl=https://4chan.org
    gradle run -Pindex

# Architecture

    scanners:
    - ports
    - dns
    - *web*

     | request
     v

    pre-request:
    - mock responses
    - deduplication
    - url blocking

    | allowed request
    v

    proxy
    
    | response
    v
    
    active rules, request-bound:
    - fingerprinting ( wappalyzer, custom rules )
    - search in public vulnerability databases for found technologies and versions
    - weaknesses (ie JSONP / non-padded JSON to avoid CORS, ...)
    - graph (neo4j, orientdb ; reconstructing APIs automatically)
    - i/o registry(find matches between earlier requests and the response)

    | response with tags
    v

    active rules, request-free:
    - authorizers
    - image limit
    - json api fuzzers
    - form api fuzzers
    - pathfinders (js-made requests, json, comments, [src], [href], [action]
    - submitting a zip-bomb
    - submitting a single-use request multiple times in parallel ( example; gift cards )
    - choosing values outside the range allowed through the frontend
    - image size limit checker
    - authorizing a browser (& blocking logout calls?, verifying email, verifying phone)
