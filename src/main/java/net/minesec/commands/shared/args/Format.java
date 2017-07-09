package net.minesec.commands.shared.args;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * Copyright (c) 9-7-17, MineSec. All rights reserved.
 */
public enum Format {

    JSON(new YAMLFactory()), YAML(new YAMLFactory());

    private final JsonFactory jsonFactory;

    Format(JsonFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
    }

    public JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }
}
