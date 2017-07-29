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
        contextBuilder.on(RESPONSE, ctx -> {
            // TODO[RULE]: If license possible, implement https://github.com/AliasIO/Wappalyzer
            final HttpResponse response = ctx.getResponse();
            if (response instanceof FullHttpResponse) {
                FullHttpResponse fullHttpResponse = (FullHttpResponse) response;
                final String setCookieHeader = fullHttpResponse.headers().get("Set-Cookie");
                // TODO: Kotlin maybe? Would avoid a lot of these null checks and extra if's
                if (setCookieHeader != null && setCookieHeader.contains("JSESSIONID")) {
                    // TODO[RULE]: Vulnerability lookup for the respective technologies and versions
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
