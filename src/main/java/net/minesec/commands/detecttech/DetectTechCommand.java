package net.minesec.commands.detecttech;

import net.minesec.core.Command;

import java.io.IOException;

/**
 * Copyright (c) 29/06/2017, MineSec. All rights reserved.
 */
public class DetectTechCommand extends Command<DetectTechCommand.Args> {

    public static class Args {
    }

    @Override
    public String name() {
        return "detect:tech";
    }

    @Override
    public Args defaults() {
        return new Args();
    }

    @Override
    public void execute(Args args) throws IOException {
    }
    //    # modules:
    //    #   tech (in=${M_HOME}/tech-matcher.yml, out=${M_HOME}/${target}/tech.yml)
    //    #     for all ${targets}, opens each file of pattern ${tapes} for reading
    //    #     applies matching logic using the given ${in} tech-matcher.yml-file
    //    #     matcher evaluated data templates, as per logic, are written to ${out} tech.yml-files

}
