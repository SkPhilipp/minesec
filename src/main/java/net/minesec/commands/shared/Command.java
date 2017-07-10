package net.minesec.commands.shared;

import java.io.IOException;

/**
 * Copyright (c) 10-7-17, MineSec. All rights reserved.
 */
public interface Command {

    void execute(Context context, String... args) throws IOException;

}
