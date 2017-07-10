package net.minesec.commands.tape;

import net.minesec.commands.shared.Command;
import net.minesec.commands.shared.Context;

import java.io.IOException;

/**
 * Copyright (c) 29/06/2017, MineSec. All rights reserved.
 */
public class TapeCommand implements Command {

    @Override
    public void execute(Context context, String... args) throws IOException {
        // TODO: start [target:none] [port:8888] [out-pattern:${M_HOME}/${target}/tape-${now}.zip]
        //       begins tape daemon on ${port} which writes proxied traffic to an archive file
        //       prints "tape-port<tab>out"
        // TODO: stop [target:none] [port:8888]
        //       stops all tape daemons active for ${target} or on ${port}
    }

}
