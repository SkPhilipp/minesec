package net.minesec.commands.attackzip;

import net.minesec.core.Command;

import java.io.IOException;

/**
 * Copyright (c) 29/06/2017, MineSec. All rights reserved.
 */
public class AttackZipCommand extends Command<AttackZipCommand.Args>{

    public class Args{}

    @Override
    public String name() {
        return "attack:zip";
    }

    @Override
    public Args argsDefault() {
        return new Args();
    }

    @Override
    public void execute(Args args) throws IOException {
    }

    // gzip bomb ,,, stuff from the tor internet-archive hoster

}
