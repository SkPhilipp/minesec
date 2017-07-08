package net.minesec.commands.attackburst;

import net.minesec.core.Command;

import java.io.IOException;

/**
 * Copyright (c) 29/06/2017, MineSec. All rights reserved.
 */
public class AttackBurstCommand extends Command<AttackBurstCommand.Args> {

    public class Args{}

    @Override
    public String name() {
        return "attack:burst";
    }

    @Override
    public Args argsDefault() {
        return new Args();
    }

    @Override
    public void execute(Args args) throws IOException {
    }

    // fire multiple requests with the same key, see what happens
    // for example: send 10 requests to cash in the same gift card

}
