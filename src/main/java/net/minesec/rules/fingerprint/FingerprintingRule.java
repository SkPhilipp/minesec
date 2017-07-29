package net.minesec.rules.fingerprint;

import com.j256.ormlite.dao.Dao;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import net.minesec.rules.api.ContextBuilder;

import java.sql.SQLException;
import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.RESPONSE;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class FingerprintingRule implements Consumer<ContextBuilder> {

    @Override
    public void accept(ContextBuilder contextBuilder) {
        contextBuilder.addEventListener(RESPONSE, ctx -> {
            final HttpResponse response = ctx.getResponse();
            if (response instanceof FullHttpResponse) {
                FullHttpResponse fullHttpResponse = (FullHttpResponse) response;
                final String setCookieHeader = fullHttpResponse.headers().get("Set-Cookie");
                if (setCookieHeader != null && setCookieHeader.contains("JSESSIONID")) {
                    try {
                        final Dao<Fingerprint, ?> dao = ctx.getDatabase().getDao(Fingerprint.class);
                        final Fingerprint fingerprint = new Fingerprint();
                        fingerprint.setTechnology("java");
                        fingerprint.setTechnology("JSESSIONID cookie");
                        dao.create(fingerprint);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
