package net.minesec.commands.detecttech;

import net.minesec.commands.shared.Command;
import net.minesec.commands.shared.Context;

import java.io.IOException;

/**
 * Copyright (c) 29/06/2017, MineSec. All rights reserved.
 */
public class DetectTechCommand implements Command {

    @Override
    public void execute(Context context, String... args) throws IOException {
        // TODO: tech (in=${M_HOME}/tech-matcher.yml, out=${M_HOME}/${target}/tech.yml)
        //       for all ${targets}, opens each file of pattern ${tapes} for reading
        //       applies matching logic using the given ${in} tech-matcher.yml-file
        //       matcher evaluated data templates, as per logic, are written to ${out} tech.yml-files
    }

}
