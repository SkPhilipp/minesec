package net.minesec.rules;

import net.minesec.rules.logging.LoggingTrafficRule;
import net.minesec.rules.mock.MockedResponseRule;
import net.minesec.rules.spider.SpiderPageRule;

import java.util.*;

/**
 * Copyright (c) 16-7-17, MineSec. All rights reserved.
 */
public class Rules {

    private static final Map<Rule.Moment, List<Rule>> ALL;

    static {
        ALL = new HashMap<>();
        put(ALL, new LoggingTrafficRule());
        put(ALL, new SpiderPageRule());
        put(ALL, new MockedResponseRule());
    }

    private static void put(Map<Rule.Moment, List<Rule>> rules, Rule rule) {
        final Rule.Moment moment = rule.moment();
        List<Rule> ruleList = rules.computeIfAbsent(moment, k -> new ArrayList<>());
        ruleList.add(rule);
    }

    public static void invokeAll(Rule.Moment moment, Context context) {
        ALL.getOrDefault(moment, Collections.emptyList()).parallelStream().forEach(rule -> {
            try {
                rule.apply(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
