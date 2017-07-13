package net.minesec.index;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Copyright (c) 29/06/2017, MineSec. All rights reserved.
 */
public class BugBountyIndexer {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper(new JsonFactory());

    private Map<String, String> headers(URI uri) {
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

    private Map<String, String> jsonHeaders(URI uri) {
        Map<String, String> headers = headers(uri);
        headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.put("X-Requested-With", "XMLHttpRequest");
        return headers;
    }

    private Element fetch(URI uri) throws IOException {
        Map<String, String> headers = headers(uri);
        return Jsoup.connect(uri.toString()).headers(headers).get().body();
    }

    private JsonNode fetchJson(URI uri) throws IOException {
        Map<String, String> headers = jsonHeaders(uri);
        byte[] bytes = Jsoup.connect(uri.toString()).headers(headers).ignoreContentType(true).execute().bodyAsBytes();
        return JSON_MAPPER.readTree(bytes);
    }

    void indexBugcrowdCurated(Consumer<BugBounty> sink) throws IOException {
        Element fetch = fetch(URI.create("https://www.bugcrowd.com/bug-bounty-list/"));
        fetch.select("main table tbody tr").forEach(element -> {
            BugBounty build = BugBounty.builder()
                    .source("https://www.bugcrowd.com/bug-bounty-list/")
                    .page(element.child(0).select("a").attr("abs:href"))
                    .rewardMonetary(!element.child(2).select("picture").isEmpty())
                    .rewardOther(!element.child(3).select("picture").isEmpty())
                    .rewardAcknowledgement(!element.child(4).select("picture").isEmpty())
                    .build();
            sink.accept(build);
        });
    }

    void indexBugcrowdPrograms(Consumer<BugBounty> sink) throws IOException {
        Element page = fetch(URI.create("https://bugcrowd.com/programs?page=1"));
        while (!page.select(".next").isEmpty()) {
            page.select("ul li.bounty").forEach(element -> {
                String reward = element.select(".stat").text();
                BugBounty build = BugBounty.builder()
                        .source("https://bugcrowd.com/programs")
                        .page(element.select("h4 a").attr("abs:href"))
                        .rewardMonetary(reward.contains("$"))
                        .rewardOther(false)
                        .rewardAcknowledgement(reward.contains("Kudos"))
                        .build();
                sink.accept(build);
            });
            String href = page.select(".next a").attr("abs:href");
            page = fetch(URI.create(href));
        }
    }

    void indexVulnerabilityLabCurated(Consumer<BugBounty> sink) throws IOException {
        Element fetch = fetch(URI.create("https://www.vulnerability-lab.com/list-of-bug-bounty-programs.php"));
        fetch.select("blockquote table").last().select("tr").forEach(element -> {
            BugBounty build = BugBounty.builder()
                    .source("https://www.vulnerability-lab.com/list-of-bug-bounty-programs.php")
                    .page(element.child(0).select("a").attr("abs:href"))
                    .rewardMonetary(element.child(1).text().isEmpty())
                    .rewardOther(element.child(2).text().isEmpty())
                    .rewardAcknowledgement(element.child(3).text().isEmpty())
                    .build();
            sink.accept(build);
        });
    }

    private URI indexHackeroneCuratedUrl(int page) {
        return URI.create("https://hackerone.com/programs/search?query=ibb%3Ano&sort=published_at%3Adescending&page=" + page);
    }

    void indexHackeroneCurated(Consumer<BugBounty> sink) throws IOException {
        JsonNode node = fetchJson(indexHackeroneCuratedUrl(1));
        int batchIndex = 1;
        int batchSize = node.get("limit").asInt();
        int batchLast = node.get("total").asInt() / batchSize;
        while (batchIndex < batchLast) {
            for (JsonNode result : node.get("results")) {
                BugBounty build = BugBounty.builder()
                        .source("https://hackerone.com/programs/search")
                        .page("https://hackerone.com" + result.get("url").textValue())
                        .rewardMonetary(result.has("meta") && result.get("meta").has("offers_bounties"))
                        .rewardOther(result.has("offers_rewards"))
                        .rewardAcknowledgement(result.has("offers_thanks"))
                        .build();
                sink.accept(build);
            }
            batchIndex++;
            node = fetchJson(indexHackeroneCuratedUrl(batchIndex));
        }
    }

}
