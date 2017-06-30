package net.minesec.core;

import com.fasterxml.jackson.databind.JsonNode;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

/**
 * Copyright (c) 30/06/2017, MineSec. All rights reserved.
 */
public class LoaderTest {

    private final Loader loader = new Loader();
    private final URL resource1 = getClass().getClassLoader().getResource("sample1.yaml");
    private final URL resource2 = getClass().getClassLoader().getResource("sample2.yaml");

    @Test
    public void interpolate() throws IOException {
        JsonNode node = loader.load(resource1);
        String interpolated = loader.interpolate("Mine${company/suffix}", node);
        Assert.assertThat(interpolated, CoreMatchers.equalTo("MineSec"));
    }

    @Test
    public void load() throws IOException {
        JsonNode node = loader.resolve("company/name", loader.load(resource1));
        Assert.assertThat(node.asText(), CoreMatchers.equalTo("MineSec"));
    }

    @Test
    public void config() {
        JsonNode node1 = loader.config(Arrays.asList(resource1, resource2), "company/name");
        Assert.assertThat(node1.asText(), CoreMatchers.equalTo("MineSec"));
        JsonNode node2 = loader.config(Arrays.asList(resource2, resource1), "company/name");
        Assert.assertThat(node2.asText(), CoreMatchers.equalTo("MineSec2"));
        JsonNode node3 = loader.config(Arrays.asList(resource2, resource1), "invalid/key");
        Assert.assertNull(node3);
    }

}
