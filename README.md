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

# TODOs:

## Must have

- Read through https://www.owasp.org/index.php/OWASP_Testing_Guide_v4_Table_of_Contents
  Currently at #4.5
- Vulnerable test site
  Setup with VMs:
  https://www.owasp.org/index.php/OWASP_Vulnerable_Web_Applications_Directory_Project/Pages/VMs
- OrientDB Installation
  wget https://orientdb.com/download.php?file=orientdb-community-importers-2.2.24.tar.gz&os=linux
  find-replace /home/philipp/Downloads/orientdb-community-importers-2.2.24
  find-replace philipp
  To install OrientDB as a service on an init-based unix or Linux system, copy the modified orientdb.sh file from $ORIENTDB_HOME/bin into /etc/init.d/:
  cp $ORIENTDB_HOME/bin/orientdb.sh /etc/init.d/orientdb
  open localhost:2480
  ./orientdb.sh start
  vim config/orientdb-server-config.xml
    replace root pw with a plaintext one for local development
    go to localhost:2480 and create a new db

## Nice to have

## Uncategorized ideas

- Authorizing a browser (& blocking logout calls?, verifying email, verifying phone)

## Not in scope

- DNS Scanner integrations
- Port Scanner integrations
