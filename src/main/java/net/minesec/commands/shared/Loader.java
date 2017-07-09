package net.minesec.commands.shared;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright (c) 29/06/2017, MineSec. All rights reserved.
 */
public class Loader {

    private static final String MATCH_PREFIX = "\\$\\{";
    private static final String MATCH_SUFFIX = "\\}";
    private static final String RESOLVE_SEPARATOR = "/";
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            URL resource = Loader.class.getClassLoader().getResource("user.yaml");
            JsonNode jsonNode = mapper.readTree(resource);
            System.out.println(jsonNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JsonNode resolve(String reference, JsonNode variables) {
        JsonNode result = variables;
        String[] fields = reference.split(RESOLVE_SEPARATOR);
        for (String field : fields) {
            if (!result.has(field)) {
                return null;
            }
            result = result.get(field);
        }
        return result;
    }

    public String interpolate(String template, JsonNode context) {
        String interpolated = template;
        Pattern pattern = Pattern.compile(MATCH_PREFIX + "(.+?)" + MATCH_SUFFIX);
        Matcher matcher = pattern.matcher(interpolated);
        while (matcher.find()) {
            String group = matcher.group(1);
            JsonNode value = resolve(group, context);
            interpolated = interpolated.replaceAll(MATCH_PREFIX + group + MATCH_SUFFIX, value.asText());
        }
        return interpolated;
    }

    public JsonNode load(URL path) throws IOException {
        return YAML_MAPPER.readTree(path);
    }

    public JsonNode config(Collection<URL> paths, String reference) {
        for (URL path : paths) {
            try {
                JsonNode node = load(path);
                JsonNode resolved = resolve(reference, node);
                if (resolved != null) {
                    return resolved;
                }
            } catch (IOException ignored) {
            }
        }
        return null;
    }

}
