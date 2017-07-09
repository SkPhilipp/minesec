package net.minesec.commands.attackzip;

import net.minesec.commands.shared.Command;
import net.minesec.commands.shared.Context;

import java.io.IOException;

/**
 * Copyright (c) 29/06/2017, MineSec. All rights reserved.
 */
public class AttackZipCommand extends Command<AttackZipCommand.Args> {

    public static class Args {
    }

    @Override
    public String name() {
        return "attack-zip";
    }

    @Override
    public Args defaults() {
        return new Args();
    }

    @Override
    public void execute(Context context, Args args) throws IOException {
        // TODO: gzip bomb ,,, stuff from the tor internet-archive hoster
    }

}
