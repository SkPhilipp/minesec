package net.minesec.commands;

import net.minesec.commands.attackburst.AttackBurstCommand;
import net.minesec.commands.attackzip.AttackZipCommand;
import net.minesec.commands.detecttech.DetectTechCommand;
import net.minesec.commands.detectvulns.DetectVulnsCommand;
import net.minesec.commands.index.IndexCommand;
import net.minesec.commands.tape.TapeCommand;
import net.minesec.core.Command;

/**
 * Copyright (c) 8-7-17, MineSec. All rights reserved.
 */
public class Commands {

    public static final Command<?>[] ALL = new Command[]{
            new AttackBurstCommand(),
            new AttackZipCommand(),
            new DetectTechCommand(),
            new DetectVulnsCommand(),
            new IndexCommand(),
            new TapeCommand()
    };

}
