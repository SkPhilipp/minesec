package net.minesec.commands.shared;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Copyright (c) 8-7-17, MineSec. All rights reserved.
 */
public class Context {

    private final InputStream in;
    private final OutputStream out;
    private final JsonFactory inJsonFactory;
    private final JsonFactory outJsonFactory;

    public Context(JsonFactory inJsonFactory, JsonFactory outJsonFactory, InputStream in, OutputStream out) {
        this.inJsonFactory = inJsonFactory;
        this.outJsonFactory = outJsonFactory;
        this.in = in;
        this.out = out;
    }

    public JsonGenerator createOutGenerator() throws IOException {
        return this.outJsonFactory.createGenerator(this.out, JsonEncoding.UTF8);
    }

    public ObjectMapper getOutMapper() throws IOException {
        return new ObjectMapper(this.outJsonFactory);
    }

    public ObjectMapper getInMapper() throws IOException {
        return new ObjectMapper(this.inJsonFactory);
    }

    public InputStream getIn() {
        return this.in;
    }

    public OutputStream getOut() {
        return this.out;
    }

}
