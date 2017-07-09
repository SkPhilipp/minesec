package net.minesec.commands.shared.args;

import com.beust.jcommander.IStringConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Copyright (c) 9-7-17, MineSec. All rights reserved.
 */
public class InputStreamConverter implements IStringConverter<InputStream> {

    @Override
    public InputStream convert(String value) {
        try {
            return new FileInputStream(new File(value));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No file for: " + value);
        }
    }
}