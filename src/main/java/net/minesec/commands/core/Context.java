package net.minesec.commands.core;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Copyright (c) 8-7-17, MineSec. All rights reserved.
 */
public class Context {

    private final JsonFactory factory;
    private final ObjectMapper mapper;
    private final InputStream in;
    private final OutputStream out;

    public Context() {
        this(new YAMLFactory(), System.in, System.out);
    }

    public Context(JsonFactory factory, InputStream in, OutputStream out) {
        this.factory = factory;
        this.in = in;
        this.out = out;
        this.mapper = new ObjectMapper(factory);
    }

    public JsonGenerator createGenerator() throws IOException {
        return this.factory.createGenerator(this.out, JsonEncoding.UTF8);
    }

    public ObjectMapper getMapper() throws IOException {
        return this.mapper;
    }

    public InputStream getIn() {
        return this.in;
    }

    public OutputStream getOut() {
        return this.out;
    }

    /**
     * Prints the exception's stacktrace and exits the program.
     *
     * @param e cause of exit
     */
    public void exit(Exception e) {
        e.printStackTrace();
        System.exit(1);
    }

}
