package net.minesec.rules.graph;

import com.j256.ormlite.dao.Dao;
import net.minesec.rules.api.ContextBuilder;
import net.minesec.rules.api.Database;

import java.sql.SQLException;
import java.util.Date;
import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.*;
import static net.minesec.rules.api.impl.Utilities.silenced;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class GraphRule implements Consumer<ContextBuilder> {

    @Override
    public void accept(ContextBuilder contextBuilder) {
        try {
            final Database database = contextBuilder.getDatabase();
            database.setup(Pageload.class);
            database.setup(Request.class);
            database.setup(Response.class);

            contextBuilder.addEventListener(PAGELOAD, silenced(ctx -> {
                final Dao<Pageload, ?> dao = ctx.getDatabase().getDao(Pageload.class);
                final Pageload pageload = new Pageload();
                pageload.setContextId(ctx.getId());
                pageload.setMoment(new Date());
                dao.createOrUpdate(pageload);
            }));

            contextBuilder.addEventListener(RESPONSE, silenced(ctx -> {
                final Dao<Response, ?> dao = ctx.getDatabase().getDao(Response.class);
                final Response response = new Response();
                response.setContentType(ctx.getRequest().headers().get("Content-Type"));
                response.setStatus(ctx.getResponse().getStatus().code());
                response.setContextId(ctx.getId());
                // TODO: Make others use contextId, never a new Date()
                response.setMoment(new Date());
                dao.createOrUpdate(response);
            }));

            contextBuilder.addEventListener(REQUEST, silenced(ctx -> {
                final Dao<Request, ?> dao = ctx.getDatabase().getDao(Request.class);
                Request request = new Request();
                request.setUrl(ctx.getRequest().getUri());
                request.setContextId(ctx.getId());
                request.setMoment(new Date());
                dao.createOrUpdate(request);
            }));

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

}
