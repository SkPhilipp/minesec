package net.minesec.commands.shared;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Copyright (c) 8-7-17, MineSec. All rights reserved.
 */
@Getter
public class Context {

    private static final JsonFactory YAML = new YAMLFactory();
    private static final ObjectMapper YAML_MAPPER = new ObjectMapper(YAML);

    private final InputStream in;
    private final OutputStream out;

    public Context(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    public JsonGenerator createGenerator() throws IOException {
        JsonGenerator generator = YAML.createGenerator(this.out, JsonEncoding.UTF8);
        generator.setCodec(YAML_MAPPER);
        return generator;
    }

    public ObjectMapper getMapper() throws IOException {
        return YAML_MAPPER;
    }

    public InputStream getIn() {
        return this.in;
    }

    public OutputStream getOut() {
        return this.out;
    }

}
