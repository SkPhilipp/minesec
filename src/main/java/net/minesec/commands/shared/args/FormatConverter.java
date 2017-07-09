package net.minesec.commands.shared.args;

import com.beust.jcommander.IStringConverter;

/**
 * Copyright (c) 9-7-17, MineSec. All rights reserved.
 */
public class FormatConverter implements IStringConverter<Format> {
    @Override
    public Format convert(String value) {
        return Format.valueOf(value.toUpperCase());
    }
}