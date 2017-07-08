package net.minesec.commands.tape;

import net.minesec.commands.core.Command;
import net.minesec.commands.core.Context;

import java.io.IOException;

/**
 * Copyright (c) 29/06/2017, MineSec. All rights reserved.
 */
public class TapeCommand extends Command<TapeCommand.Args> {

    public static class Args {
    }

    @Override
    public String name() {
        return "tape";
    }

    @Override
    public Args defaults() {
        return new Args();
    }

    @Override
    public void execute(Context context, Args args) throws IOException {
    }
    //# m tape start [target:none]
    //#              [port:8888]
    //#              [out-pattern:${M_HOME}/${target}/tape-${now}.zip]
    //#     begins tape daemon on ${port} which writes proxied traffic to an archive file
    //#     prints "tape-port<tab>out"
    //#
    //
    //# m tape stop [target:none]
    //#             [port:8888]
    //#     stops all tape daemons active for ${target} or on ${port}

}
