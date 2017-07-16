package net.minesec.rules;

import net.minesec.rules.core.PageRule;
import net.minesec.rules.core.TrafficRule;
import net.minesec.rules.logging.LoggingTrafficRule;
import net.minesec.rules.spider.SpiderPageRule;

import java.util.ArrayList;

/**
 * Copyright (c) 16-7-17, MineSec. All rights reserved.
 */
public class Rules {

    public static final ArrayList<TrafficRule> TRAFFIC_RULES = new ArrayList<>();
    public static final ArrayList<PageRule> PAGE_RULES = new ArrayList<>();

    static {
        TRAFFIC_RULES.add(new LoggingTrafficRule());
        PAGE_RULES.add(new SpiderPageRule());
    }
}
