installation:

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

build-and-run:

    # example run
    gradle run -Pargs="explore https://4chan.org"

indexing bounties:

    ( or... minesec --run script.js http://hackerone/... )
    minesec index

    bug bounty indexes      -> root urls
    ( hackerone )
    ( bugcrowd )
    ( vulnerability lab )
    ( search engine patterns )

exploring from a root url and all coupled functionalities:

    ( or... minesec [<root-url>...] )
    minesec explore <root-url>

    loop until fully crawled or timed out:
    root url pages          -> fullly crawled site
    ( could identify page structure as not to crawl the same page template multiple times )
    ( the pages are navigated using a real browser, not by interpreting responses         )
    ( the browser will proxy all requests, including secure requests                      )
    ( the proxy saves each request and response to a persistent store                     )

    fn 1: passive rules:
    much like a form of deep-packet inspection.
    each request and response will be processed by multiple rules, which can identify
    aspects of the application. matching is usually done based on an extended regular
    expression-based systems.
    ( could identify page structure to find pages which are generated differently,        )
    ( as to identify different backend technologies, on which to re-apply certain rules   )

    fn 2: active rules:
    event-triggered(i.e. by passive rules), or always running,
    active rules do more than listen, and can interact with the target.
    examples:
    - submitting a zip-bomb
    - submitting requests with values which are not normally possible to choose through the UI
      ( & other generic fuzzing )
    - submitting a single-use request multiple times in parallel
      ( for example, redeeming the same gift card many times )

    fn 3: identifying technologies and vulnerabilities through finterprinting:
    through use of for example wappalyzer, and custom rules, technologies used by the
    target can be identified, possibly including versions.
    each technology can then be searched on in public vulnerability databases,
    generating a set of possible vulnerabilities.

    fn 4: reconstructing the API:
    through use of passive inspection, the application's API can be reconstructed.
    for example rules that could match this are:
    - responses returning JSON, XML, or other data-formats
      this will include javascript with mainly JSON in it
    - responses which contain part of the request
    - all other responses which result in unique pages
    these can then be used as input to active rules, and for hand-picked attacks.
