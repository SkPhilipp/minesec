package net.minesec.commands.core;

import com.beust.jcommander.JCommander;

import java.io.IOException;

/**
 * Copyright (c) 29/06/2017, MineSec. All rights reserved.
 *
 * @param <T> args type
 */
public abstract class Command<T> {

    public void execute(Context context, String[] args) throws IOException {
        T commandArgs = this.defaults();
        JCommander.newBuilder()
                .addObject(commandArgs)
                .build()
                .parse(args);
        this.execute(context, commandArgs);
    }

    abstract public String name();

    abstract protected T defaults();

    abstract protected void execute(Context context, T args) throws IOException;

}
