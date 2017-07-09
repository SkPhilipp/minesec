package net.minesec.commands.shared;

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
        JsonNode node = this.loader.load(this.resource1);
        String interpolated = this.loader.interpolate("Mine${company/suffix}", node);
        Assert.assertThat(interpolated, CoreMatchers.equalTo("MineSec"));
    }

    @Test
    public void load() throws IOException {
        JsonNode node = this.loader.resolve("company/name", this.loader.load(this.resource1));
        Assert.assertThat(node.asText(), CoreMatchers.equalTo("MineSec"));
    }

    @Test
    public void config() {
        JsonNode node1 = this.loader.config(Arrays.asList(this.resource1, this.resource2), "company/name");
        Assert.assertThat(node1.asText(), CoreMatchers.equalTo("MineSec"));
        JsonNode node2 = this.loader.config(Arrays.asList(this.resource2, this.resource1), "company/name");
        Assert.assertThat(node2.asText(), CoreMatchers.equalTo("MineSec2"));
        JsonNode node3 = this.loader.config(Arrays.asList(this.resource2, this.resource1), "invalid/key");
        Assert.assertNull(node3);
    }

}
