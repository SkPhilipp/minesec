package net.minesec;

import net.minesec.commands.core.Command;
import net.minesec.commands.core.Context;

import java.io.IOException;
import java.util.Arrays;

import static java.lang.System.exit;
import static net.minesec.commands.Commands.ALL_MAPPED;

/**
 * Copyright (c) 8-7-17, MineSec. All rights reserved.
 */
public class MineSec {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            usage();
            exit(0);
        }
        String choice = args[0];
        Command<?> command = ALL_MAPPED.get(choice);
        if (command == null) {
            System.err.println("'" + choice + "' is not a minesec command.");
            exit(1);
        }
        String[] choiceArgs = Arrays.copyOfRange(args, 1, args.length);
        Context context = new Context();
        command.execute(context, choiceArgs);
    }

    private static void usage() {
        System.out.println("Usage: <command> [<args>]");
        System.out.println("Available commands:");
        for (String name : ALL_MAPPED.keySet()) {
            System.out.println("- " + name);
        }
    }
}
