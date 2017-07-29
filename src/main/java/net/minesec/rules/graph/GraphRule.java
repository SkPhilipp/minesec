package net.minesec.rules.graph;

import com.j256.ormlite.dao.Dao;
import net.minesec.rules.api.ContextBuilder;

import java.sql.SQLException;
import java.util.Date;
import java.util.function.Consumer;

import static net.minesec.rules.api.ContextBuilder.ContextEvent.PAGELOAD;
import static net.minesec.rules.api.ContextBuilder.ContextEvent.RESPONSE;

/**
 * Copyright (c) 21-7-17, MineSec. All rights reserved.
 */
public class GraphRule implements Consumer<ContextBuilder> {

    @Override
    public void accept(ContextBuilder contextBuilder) {
        contextBuilder.on(RESPONSE, ctx -> {
            try {
                final Dao<Request, ?> dao = ctx.getDatabase().getDao(Request.class);
                Request request = new Request();
                request.setUrl(ctx.getRequest().getUri());
                request.setContextId(ctx.getId());
                request.setContentType(ctx.getRequest().headers().get("Content-Type"));
                request.setMoment(new Date());
                dao.createOrUpdate(request);
                // TODO: Consumers should be able to throw exceptions
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        contextBuilder.on(RESPONSE, ctx -> {
            try {
                final Dao<Response, ?> dao = ctx.getDatabase().getDao(Response.class);
                final Response response = new Response();
                response.setStatus(ctx.getResponse().getStatus().code());
                response.setContextId(ctx.getId());
                // TODO: Make others use contextId, never a new Date()
                response.setMoment(new Date());
                dao.createOrUpdate(response);
                // TODO: Consumers should be able to throw exceptions
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        contextBuilder.on(PAGELOAD, ctx -> {
            try {
                final Dao<Pageload, ?> dao = ctx.getDatabase().getDao(Pageload.class);
                final Pageload pageload = new Pageload();
                pageload.setContextId(ctx.getId());
                pageload.setMoment(new Date());
                dao.createOrUpdate(pageload);
                // TODO: Consumers should be able to throw exceptions
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

}
