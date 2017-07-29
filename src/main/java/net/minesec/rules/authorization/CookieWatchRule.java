package net.minesec.rules.authorization;

import com.j256.ormlite.dao.Dao;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import net.minesec.rules.api.ContextBuilder;

import java.sql.SQLException;
import java.util.Date;
import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.RESPONSE;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class CookieWatchRule implements Consumer<ContextBuilder> {

    @Override
    public void accept(ContextBuilder contextBuilder) {
        contextBuilder.on(RESPONSE, ctx -> {
            final HttpResponse response = ctx.getResponse();
            if (response instanceof FullHttpResponse) {
                FullHttpResponse fullHttpResponse = (FullHttpResponse) response;
                final String setCookieHeader = fullHttpResponse.headers().get("Set-Cookie");
                if (setCookieHeader != null) {
                    try {
                        final Dao<CookieChange, ?> dao = ctx.getDatabase().getDao(CookieChange.class);
                        CookieChange cookieChange = new CookieChange();
                        cookieChange.setCookie(setCookieHeader);
                        cookieChange.setMoment(new Date());
                        dao.create(cookieChange);
                    } catch (SQLException e) {
                        // TODO: Shared exception logging
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}