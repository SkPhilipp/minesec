package net.minesec.commands.detectvulns;

import net.minesec.core.Command;

import java.io.IOException;

/**
 * Copyright (c) 29/06/2017, MineSec. All rights reserved.
 */
public class DetectVulnsCommand extends Command<DetectVulnsCommand.Args> {

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
    //#   vulns (in=${M_HOME}/${target}/tech.yml, out=${M_HOME}/${target}/vulns.yml)
    //#     for all ${targets}, opens each ${in} tech.yml-file for reading
    //#     searches for vulnerabilities for techs, writes vulnerabilities to ${out} vulns.yml-files


}
