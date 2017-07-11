
1) [ ] Discover Bounties
2) [ ] Discover Bounty-Endpoints
3) [ ] Autoscan Endpoints
4)
    1) [ ] Find Vulnerabilities Through Fuzz
    2) [ ] Fingerprint Endpoints
    3) [ ] Reconstruct API Specification and Calls
5)
    1) [ ] Match Tech from Fingerprinted Endpoints
    2) [ ] Fuzz API Using Headless Browser
6)
    1) [ ] Find Vulnerabilities for Techs
    2) [ ] Handpick interesting API paths for manual exploitation
    3) [ ] Apply Common Attacks on handpicked endpoints (i.e. Shotgun & Bomb)

sudo apt-get install curl
PLATFORM=linux64
VERSION=$(curl http://chromedriver.storage.googleapis.com/LATEST_RELEASE)
curl http://chromedriver.storage.googleapis.com/$VERSION/chromedriver_$PLATFORM.zip > chromedriver.zip
unzip chromedriver.zip
sudo mkdir /opt/chromedriver
sudo mv chromedriver /opt/chromedriver/chromedriver
chmod +x /opt/chromedriver/chromedriver
rm chromedriver.zip

HttpProxyServer server =
        DefaultHttpProxyServer.bootstrap()
                .withManInTheMiddle(new CertificateSniffingMitmManager())
                .withPort(8080)
                .start();
System.out.println("shields up");
Thread.sleep(600000);
// this created littleproxy-mitm.pem & .p12 files in root of project:
// sudo apt-get install libnss3-tools
// certutil -d sql:$HOME/.pki/nssdb -A -t TC -n "MineSec" -i ./littleproxy-mitm.pem
