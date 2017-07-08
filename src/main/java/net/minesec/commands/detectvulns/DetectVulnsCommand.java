package net.minesec.commands.detectvulns;

import net.minesec.commands.core.Command;
import net.minesec.commands.core.Context;

import java.io.IOException;

/**
 * Copyright (c) 29/06/2017, MineSec. All rights reserved.
 */
public class DetectVulnsCommand extends Command<DetectVulnsCommand.Args> {

    public static class Args {
    }

    @Override
    public String name() {
        return "detect-vulns";
    }

    @Override
    public Args defaults() {
        return new Args();
    }

    @Override
    public void execute(Context context, Args args) throws IOException {
    }
    // TODO: vulns (in=${M_HOME}/${target}/tech.yml, out=${M_HOME}/${target}/vulns.yml)
    //       for all ${targets}, opens each ${in} tech.yml-file for reading
    //       searches for vulnerabilities for techs, writes vulnerabilities to ${out} vulns.yml-files


}
