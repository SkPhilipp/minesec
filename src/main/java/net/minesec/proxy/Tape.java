package net.minesec.proxy;

import net.minesec.core.Module;

import java.io.IOException;

/**
 * Copyright (c) 29/06/2017, MineSec. All rights reserved.
 */
public class Tape extends Module {

    @Override
    public void execute() throws IOException {

    }

    //# m tape start [target:none]
    //#              [port:8888]
    //#              [out-pattern:${M_HOME}/${target}/tape-${now}.zip]
    //#     begins proxy daemon on ${port} which writes proxied traffic to an archive file
    //#     prints "tape-port<tab>out"
    //#
    //
    //# m tape stop [target:none]
    //#             [port:8888]
    //#     stops all proxy daemons active for ${target} or on ${port}

}
