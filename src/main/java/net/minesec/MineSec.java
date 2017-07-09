package net.minesec;

import com.beust.jcommander.JCommander;
import net.minesec.commands.shared.Command;
import net.minesec.commands.shared.Context;
import net.minesec.commands.shared.args.ContextArgs;

import java.io.IOException;
import java.util.Arrays;

import static java.lang.System.exit;
import static net.minesec.commands.Commands.ALL_MAPPED;

/**
 * Copyright (c) 8-7-17, MineSec. All rights reserved.
 */
public class MineSec {

    public static void main(String[] args) throws IOException {
        // print usage
        if (args.length == 0) {
            System.out.println("Usage: <command> [<args>]");
            System.out.println("Available commands:");
            for (String name : ALL_MAPPED.keySet()) {
                System.out.println("- " + name);
            }
            exit(0);
        }

        // print not found
        String choice = args[0];
        Command<?> command = ALL_MAPPED.get(choice);
        if (command == null) {
            System.err.println("'" + choice + "' is not a minesec command.");
            exit(1);
        }

        // set up context and execute command
        String[] choiceArgs = Arrays.copyOfRange(args, 1, args.length);
        ContextArgs contextArgs = new ContextArgs();
        JCommander.newBuilder()
                .addObject(contextArgs)
                .build()
                .parse(choiceArgs);
        Context context = new Context(contextArgs.getInFormat().getJsonFactory(),
                contextArgs.getOutFormat().getJsonFactory(),
                contextArgs.getIn(),
                contextArgs.getOut());
        command.execute(context, dargs -> JCommander.newBuilder()
                .addObject(dargs)
                .build()
                .parse(choiceArgs));
    }

}
