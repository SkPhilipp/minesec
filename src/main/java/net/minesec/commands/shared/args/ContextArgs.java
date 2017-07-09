package net.minesec.commands.shared.args;

import com.beust.jcommander.Parameter;
import lombok.Data;

import java.io.InputStream;
import java.io.OutputStream;

import static net.minesec.commands.shared.args.Format.YAML;

/**
 * Copyright (c) 9-7-17, MineSec. All rights reserved.
 */
@Data
public class ContextArgs {

    @Parameter(names = {"--in-format"})
    Format inFormat = YAML;
    @Parameter(names = {"--in"}, converter = InputStreamConverter.class)
    InputStream in = System.in;
    @Parameter(names = {"--out-format"}, converter = FormatConverter.class)
    Format outFormat = YAML;
    @Parameter(names = {"--out"}, converter = OutputStreamConverter.class)
    OutputStream out = System.out;
}
