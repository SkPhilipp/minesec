package net.minesec.commands.detectvulns;

import net.minesec.commands.shared.Command;
import net.minesec.commands.shared.Context;

import java.io.IOException;

/**
 * Copyright (c) 29/06/2017, MineSec. All rights reserved.
 */
public class DetectVulnsCommand implements Command {

    @Override
    public void execute(Context context, String... args) throws IOException {
    }
    // TODO: vulns (in=${M_HOME}/${target}/tech.yml, out=${M_HOME}/${target}/vulns.yml)
    //       for all ${targets}, opens each ${in} tech.yml-file for reading
    //       searches for vulnerabilities for techs, writes vulnerabilities to ${out} vulns.yml-files


}
