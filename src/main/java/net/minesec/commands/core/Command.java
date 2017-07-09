package net.minesec.commands.core;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Copyright (c) 29/06/2017, MineSec. All rights reserved.
 *
 * @param <T> args type
 */
public abstract class Command<T> {

    public void execute(Context context, Consumer<T> argsModifier) throws IOException {
        T commandArgs = this.defaults();
        argsModifier.accept(commandArgs);
        this.execute(context, commandArgs);
    }

    abstract public String name();

    abstract protected T defaults();

    abstract protected void execute(Context context, T args) throws IOException;

}
