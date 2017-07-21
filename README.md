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
- DNS Scanner integrations
- Port Scanner integrations
- Authorizers
- Form api fuzzers
- Pathfinders (js-made requests, json, comments, [src], [href], [action]
- Submitting a zip-bomb
- Submitting a single-use request multiple times in parallel ( example; gift cards )
- Choosing values outside the range allowed through the frontend
- Image size limit checker
- Authorizing a browser (& blocking logout calls?, verifying email, verifying phone)

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
