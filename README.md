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

# TODOs:

## Must have

- Read through https://www.owasp.org/index.php/OWASP_Testing_Guide_v4_Table_of_Contents
  Currently at #4.5
- Vulnerable test site
  Setup with VMs:
  https://www.owasp.org/index.php/OWASP_Vulnerable_Web_Applications_Directory_Project/Pages/VMs
- Research viability of Kotlin
- \[Fingerprint rule\] If license possible, implement https://github.com/AliasIO/Wappalyzer
- \[Fingerprint rule\] Vulnerability lookup for the respective technologies and versions
- \[Crossdomain Rule\] Implement crossdomain.xml checker

## Nice to have

- Bug bounty indexer should use search engines alongside existing indexes

## Uncategorized ideas

- Authorizing a browser (& blocking logout calls?, verifying email, verifying phone)
