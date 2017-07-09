package net.minesec.commands.shared.args;

import com.beust.jcommander.IStringConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Copyright (c) 9-7-17, MineSec. All rights reserved.
 */
public class OutputStreamConverter implements IStringConverter<OutputStream> {

    @Override
    public OutputStream convert(String value) {
        try {
            return new FileOutputStream(new File(value));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("No file for: " + value);
        }
    }
}