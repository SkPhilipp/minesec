package net.minesec.rules.clickjacking;

import com.j256.ormlite.dao.Dao;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import net.minesec.rules.api.ContextBuilder;

import java.sql.SQLException;
import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.RESPONSE;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class ClickjackingRule implements Consumer<ContextBuilder> {

    @Override
    public void accept(ContextBuilder contextBuilder) {
        contextBuilder.addEventListener(RESPONSE, ctx -> {
            final HttpResponse response = ctx.getResponse();
            final HttpHeaders headers = response.headers();
            final String xFrameOptions = headers.get("X-Frame-Options");
            if (xFrameOptions != null) {
                if (!xFrameOptions.equals("DENY")
                        && !xFrameOptions.equals("SAMEORIGIN")) {
                    try {
                        final Dao<ClickjackablePage, ?> dao = ctx.getDatabase().getDao(ClickjackablePage.class);
                        ClickjackablePage clickjackablePage = new ClickjackablePage();
                        clickjackablePage.setFrameOptions(xFrameOptions);
                        clickjackablePage.setTrigger("Header is not on DENY");
                        clickjackablePage.setUrl(ctx.getRequest().getUri());
                        dao.create(clickjackablePage);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    final Dao<ClickjackablePage, ?> dao = ctx.getDatabase().getDao(ClickjackablePage.class);
                    ClickjackablePage clickjackablePage = new ClickjackablePage();
                    clickjackablePage.setFrameOptions("");
                    clickjackablePage.setTrigger("Header is missing");
                    clickjackablePage.setUrl(ctx.getRequest().getUri());
                    dao.create(clickjackablePage);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
