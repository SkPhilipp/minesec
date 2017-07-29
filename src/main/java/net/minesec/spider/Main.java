package net.minesec.spider;

import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePoolFactory;
import net.minesec.rules.api.Context;
import net.minesec.rules.api.ContextBuilder;
import net.minesec.rules.api.ContextBuilderImpl;
import net.minesec.rules.authorization.CookieWatchRule;
import net.minesec.rules.clickjacking.ClickjackingRule;
import net.minesec.rules.compression.ZipBombRule;
import net.minesec.rules.fingerprint.FingerprintingRule;
import net.minesec.rules.form.FormApiFuzzRule;
import net.minesec.rules.graph.GraphRule;
import net.minesec.rules.image.ImageRule;
import net.minesec.rules.json.JsonApiFuzzRule;
import net.minesec.rules.leaks.CommentsLeakRule;
import net.minesec.rules.leaks.HttpLeakRule;
import net.minesec.rules.mock.ScriptedRule;
import net.minesec.rules.pathfind.PathfindRule;
import net.minesec.rules.spider.SpiderPageRule;
import org.littleshoot.proxy.mitm.RootCertificateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.PAGELOAD;

/**
 * Copyright (c) 10-7-17, MineSec. All rights reserved.
 */
class Main {

    private static final int SESSIONS_LIMIT = 10;
    private static final int DB_POOL_LIMIT = 100;
    private static final List<Consumer<ContextBuilder>> ALL;

    static {
        ALL = new ArrayList<>();
        ALL.add(new ZipBombRule());
        ALL.add(new CookieWatchRule());
        ALL.add(new ClickjackingRule());
        ALL.add(new FingerprintingRule());
        ALL.add(new FormApiFuzzRule());
        ALL.add(new GraphRule());
        ALL.add(new ImageRule());
        ALL.add(new JsonApiFuzzRule());
        ALL.add(new CommentsLeakRule());
        ALL.add(new HttpLeakRule());
        ALL.add(new ScriptedRule());
        ALL.add(new PathfindRule());
        ALL.add(new SpiderPageRule());
    }

    public static void main(String[] args) throws InterruptedException, RootCertificateException {
        final OPartitionedDatabasePoolFactory dbPoolFactory = new OPartitionedDatabasePoolFactory(DB_POOL_LIMIT);
        dbPoolFactory.setMaxPoolSize(DB_POOL_LIMIT);
        final OPartitionedDatabasePool dbPool = dbPoolFactory.get("remote:localhost/minesec", "admin", "admin");
        ContextBuilder contextBuilder = new ContextBuilderImpl(dbPool);
        ALL.parallelStream().forEach(consumer -> consumer.accept(contextBuilder));
        try (WebDriverPool webDriverPool = new WebDriverPool(8080, SESSIONS_LIMIT, contextBuilder)) {
            webDriverPool.queue(webDriver -> {
                webDriver.get(args[0]);
                WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
                webDriverWait.until(jsDriver -> ((JavascriptExecutor) jsDriver).executeScript("return document.readyState").equals("complete"));
                final Context context = contextBuilder.build(webDriverPool, webDriver, null, null);
                context.dispatch(PAGELOAD);
            });
            Thread.sleep(1000 * 60 * 60 * 24);
        } finally {
            dbPool.close();
        }
    }
}
