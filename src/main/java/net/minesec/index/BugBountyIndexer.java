package net.minesec.index;

import net.minesec.core.Module;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 29/06/2017, MineSec. All rights reserved.
 */
public class BugBountyIndexer extends Module {

    public Map<String, String> htmlHeaders(URI uri) {
        String domain = uri.getHost();
        Map<String, String> headers = new HashMap<>();
        headers.put("DNT", "1");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "en-US,en;q=0.8,nl;q=0.6");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
        headers.put("Referer", uri.toString());
        headers.put("Authority", domain);
        headers.put("Host", domain);
        return headers;
    }

    public Map<String, String> jsonHeaders(URI uri) {
        Map<String, String> headers = htmlHeaders(uri);
        headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.put("X-Requested-With", "XMLHttpRequest");
        return headers;
    }

    //    def fetch_html(url):
    //    req = urllib2.Request(url)
    //    headers = headers_json(url)
    //    for key, value in headers.iteritems():
    //            req.add_header(key, value)
    //    resp = urllib2.urlopen(req)
    //    html = resp.read()
    //            return pq(html)

    //    def headers_json(url):
    //    headers = headers_html(url)
    //            return headers

    //    def fetch_json(url):
    //    req = urllib2.Request(url)
    //    headers = headers_html(url)
    //    for key, value in headers.iteritems():
    //            req.add_header(key, value)
    //    resp = urllib2.urlopen(req)
    //            return resp.read()

    //    def index_bugcrowd_curated():
    //    html = fetch_html("https://www.bugcrowd.com/bug-bounty-list/")
    //    for row in x(html, "//main/table/tbody/tr"):
    //    print "===================================================="
    //    print "link", x(row, "//td[nth-child(1)]/a[href]")
    //    print "reward-money", x(row, "//td[nth-child(3)]/picture")
    //    print "reward-other", x(row, "//td[nth-child(4)]/picture")
    //    print "reward-ack", x(row, "//td[nth-child(5)]/picture")

    //    def index_bugcrowd_programs():
    //    def url(page):
    //            return "https://bugcrowd.com/programs?page=" + page
    //
    //            html = fetch_html(url(1))
    //    while x(html, ".next") is not None:
    //            for row in x(html, "//ul/li.bounty"):
    //    print "===================================================="
    //    print "link", x(row, "h4/a[href]")
    //    print "reward-money", x(row, ".stat")
    //    print "reward-other", x(row, ".stat")
    //    print "reward-ack", x(row, ".stat")
    //    next_url = x(html, ".next/a[href]")
    //    html = fetch_html(next_url)

    //    def index_vulnerability_lab_curated():
    //    html = fetch_html("https://www.vulnerability-lab.com/list-of-bug-bounty-programs.php")
    //    for row in x(html, "//blockquote/table[last-child]/tr"):
    //    print "===================================================="
    //    print "link", x(row, "//td[nth-child(1)]/a[href]")
    //    print "reward-money", x(row, "//td[nth-child(2)]/span[non-empty]")
    //    print "reward-other", x(row, "//td[nth-child(3)]/span[non-empty]")
    //    print "reward-ack", x(row, "//td[nth-child(4)]/span[non-empty]")

    //    def index_hackerone_curated():
    //    def url(page):
    //            return "https://hackerone.com/programs/search?query=ibb^%^3Ano^&sort=published_at^%^3Adescending^&page=" + page
    //
    //            json = fetch_json(url(1))
    //    batch_index = 1
    //    batch_size = j(json, 'limit')
    //    batch_last = j(json, 'total') / batch_size
    //    while batch_index <= batch_last:
    //            for row in j(json, "results"):
    //    print "===================================================="
    //    print "link", j(row, "url")
    //    print "reward-money", j(row, "meta.offers_bounties")
    //    print "reward-other", j(row, "offers_rewards")
    //    print "reward-ack", j(row, "offers_thanks")
    //    batch_index = batch_index + 1
    //    json = fetch_json(url(batch_index))

}
