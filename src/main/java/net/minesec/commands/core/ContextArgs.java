package net.minesec.commands.core;

import com.beust.jcommander.Parameter;
import lombok.Data;

import java.io.InputStream;
import java.io.OutputStream;

import static net.minesec.commands.core.Format.YAML;

/**
 * Copyright (c) 9-7-17, MineSec. All rights reserved.
 */
@Data
public class ContextArgs {
    @Parameter(names = {"--in-format"})
    Format inFormat = YAML;
    @Parameter(names = {"--in"})
    InputStream in = System.in;
    @Parameter(names = {"--out-format"})
    Format outFormat = YAML;
    @Parameter(names = {"--out"})
    OutputStream out = System.out;
}
