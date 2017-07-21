package net.minesec.spider.rules;

import net.minesec.rules.api.Context;
import net.minesec.rules.api.Rule;
import net.minesec.rules.authorization.CookieWatchRule;
import net.minesec.rules.clickjacking.ClickjackingRule;
import net.minesec.rules.compression.ZipBombRule;
import net.minesec.rules.correlations.IoCorrelationRule;
import net.minesec.rules.fingerprint.FingerprintVulnerbilityLookupRule;
import net.minesec.rules.fingerprint.FingerprintingRule;
import net.minesec.rules.form.FormApiFuzzRule;
import net.minesec.rules.form.FormApiWeaknessRule;
import net.minesec.rules.form.FormDisabledRule;
import net.minesec.rules.form.FormHiddenRule;
import net.minesec.rules.graph.GraphRule;
import net.minesec.rules.image.ImageRule;
import net.minesec.rules.json.JsonApiFuzzRule;
import net.minesec.rules.json.JsonWeaknessRule;
import net.minesec.rules.leaks.CommentsLeakRule;
import net.minesec.rules.leaks.HttpLeakRule;
import net.minesec.rules.logging.LoggingTrafficRule;
import net.minesec.rules.mock.MockedResponseRule;
import net.minesec.rules.pathfind.CommentsRule;
import net.minesec.rules.pathfind.JsonLinksRule;
import net.minesec.rules.pathfind.PageLinksRule;
import net.minesec.rules.pathfind.RobotsTxtRule;
import net.minesec.rules.spider.SpiderPageRule;

import java.util.*;

/**
 * Copyright (c) 16-7-17, MineSec. All rights reserved.
 */
public class Rules {

    private static final Map<Rule.Moment, List<Rule>> ALL;

    static {
        ALL = new HashMap<>();
        put(ALL, new ZipBombRule());
        put(ALL, new CookieWatchRule());
        put(ALL, new ClickjackingRule());
        put(ALL, new IoCorrelationRule());
        put(ALL, new FingerprintingRule());
        put(ALL, new FingerprintVulnerbilityLookupRule());
        put(ALL, new FormApiFuzzRule());
        put(ALL, new FormApiWeaknessRule());
        put(ALL, new FormHiddenRule());
        put(ALL, new FormDisabledRule());
        put(ALL, new GraphRule());
        put(ALL, new ImageRule());
        put(ALL, new JsonApiFuzzRule());
        put(ALL, new JsonWeaknessRule());
        put(ALL, new CommentsLeakRule());
        put(ALL, new HttpLeakRule());
        put(ALL, new LoggingTrafficRule());
        put(ALL, new MockedResponseRule());
        put(ALL, new CommentsRule());
        put(ALL, new JsonLinksRule());
        put(ALL, new PageLinksRule());
        put(ALL, new RobotsTxtRule());
        put(ALL, new SpiderPageRule());
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
