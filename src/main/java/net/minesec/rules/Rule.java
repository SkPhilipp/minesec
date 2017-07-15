package net.minesec.rules;

/**
 * Copyright (c) 15-7-17, MineSec. All rights reserved.
 */
public interface Rule {

    void process(Object request, Object response, Object meta);

}
