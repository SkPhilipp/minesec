package net.minesec;

import net.minesec.commands.attackzip.AttackZipCommand;
import net.minesec.commands.detecttech.DetectTechCommand;
import net.minesec.commands.detectvulns.DetectVulnsCommand;
import net.minesec.commands.index.IndexCommand;
import net.minesec.commands.shared.Command;
import net.minesec.commands.shared.Context;
import net.minesec.commands.tape.TapeCommand;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;

/**
 * Copyright (c) 8-7-17, MineSec. All rights reserved.
 */
public class MineSec {
    public static final Map<String, Command> ALL;

    static {
        ALL = new HashMap<>();
        ALL.put("index", new IndexCommand());
        ALL.put("attack-zip", new AttackZipCommand());
        ALL.put("detect-tech", new DetectTechCommand());
        ALL.put("detect-vulns", new DetectVulnsCommand());
        ALL.put("tape", new TapeCommand());
    }

    public static void main(String[] args) throws IOException {
        // print usage
        if (args.length == 0) {
            System.out.println("Usage: <command> [<args>]");
            System.out.println("Available commands:");
            for (String name : ALL.keySet()) {
                System.out.println("- " + name);
            }
            exit(0);
        }

        // print not found
        String choice = args[0];
        Command command = ALL.get(choice);
        if (command == null) {
            System.err.println("'" + choice + "' is not a minesec command.");
            exit(1);
        }

        // set up context and execute command
        Context context = new Context(System.in, System.out);
        command.execute(context, Arrays.copyOfRange(args, 1, args.length));
    }

}
