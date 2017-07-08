package net.minesec.commands.core;

import com.beust.jcommander.JCommander;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Copyright (c) 29/06/2017, MineSec. All rights reserved.
 *
 * @param <T> args type
 */
public abstract class Command<T> {

    protected final JsonFactory factory;
    protected final ObjectMapper mapper;
    protected final InputStream in;
    protected final OutputStream out;

    public Command() {
        this(new YAMLFactory(), System.in, System.out);
    }

    public Command(JsonFactory factory, InputStream in, OutputStream out) {
        this.factory = factory;
        this.in = in;
        this.out = out;
        this.mapper = new ObjectMapper(factory);
    }

    protected JsonGenerator openGenerator() throws IOException {
        return this.factory.createGenerator(this.out, JsonEncoding.UTF8);
    }

    public void execute(String[] args) throws IOException {
        T commandArgs = this.defaults();
        JCommander.newBuilder()
                .addObject(commandArgs)
                .build()
                .parse(args);
        this.execute(commandArgs);
    }

    abstract public String name();

    abstract protected T defaults();

    abstract protected void execute(T args) throws IOException;

    public void exit(Exception e) {
        e.printStackTrace();
        System.exit(1);
    }

}
