package net.minesec.commands;

import net.minesec.commands.attackzip.AttackZipCommand;
import net.minesec.commands.detecttech.DetectTechCommand;
import net.minesec.commands.detectvulns.DetectVulnsCommand;
import net.minesec.commands.index.IndexCommand;
import net.minesec.commands.shared.Command;
import net.minesec.commands.tape.TapeCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 8-7-17, MineSec. All rights reserved.
 */
public class Commands {

    public static final Command<?>[] ALL = new Command[]{
            new AttackZipCommand(),
            new DetectTechCommand(),
            new DetectVulnsCommand(),
            new IndexCommand(),
            new TapeCommand()
    };

    public static final Map<String, Command<?>> ALL_MAPPED;

    static {
        ALL_MAPPED = new HashMap<>();
        for (Command<?> command : ALL) {
            ALL_MAPPED.put(command.name(), command);
        }
    }
}
