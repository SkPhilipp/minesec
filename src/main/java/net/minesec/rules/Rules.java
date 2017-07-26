package net.minesec.rules;

import net.minesec.rules.api.Rule;
import net.minesec.rules.authorization.CookieWatchRule;
import net.minesec.rules.clickjacking.ClickjackingRule;
import net.minesec.rules.compression.ZipBombRule;
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
import net.minesec.rules.mock.ScriptedRule;
import net.minesec.rules.pathfind.CommentsRule;
import net.minesec.rules.pathfind.JsonLinksRule;
import net.minesec.rules.pathfind.PageLinksRule;
import net.minesec.rules.pathfind.RobotsTxtRule;
import net.minesec.rules.spider.SpiderPageRule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Copyright (c) 16-7-17, MineSec. All rights reserved.
 */
public class Rules {

    private static final List<Rule> ALL;

    static {
        ALL = new ArrayList<>();
        ALL.add(new ZipBombRule());
        ALL.add(new CookieWatchRule());
        ALL.add(new ClickjackingRule());
        ALL.add(new FingerprintingRule());
        ALL.add(new FingerprintVulnerbilityLookupRule());
        ALL.add(new FormApiFuzzRule());
        ALL.add(new FormApiWeaknessRule());
        ALL.add(new FormHiddenRule());
        ALL.add(new FormDisabledRule());
        ALL.add(new GraphRule());
        ALL.add(new ImageRule());
        ALL.add(new JsonApiFuzzRule());
        ALL.add(new JsonWeaknessRule());
        ALL.add(new CommentsLeakRule());
        ALL.add(new HttpLeakRule());
        ALL.add(new ScriptedRule());
        ALL.add(new CommentsRule());
        ALL.add(new JsonLinksRule());
        ALL.add(new PageLinksRule());
        ALL.add(new RobotsTxtRule());
        ALL.add(new SpiderPageRule());
    }

    public static void invokeAll(Consumer<Rule> invoker) {
        ALL.parallelStream().forEach(rule -> {
            try {
                invoker.accept(rule);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
