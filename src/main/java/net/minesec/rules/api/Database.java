package net.minesec.rules.api;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Copyright (c) 29-7-17, MineSec. All rights reserved.
 */
public interface Database {

    /**
     * An active {@link Dao} for the given type
     */
    <T> Dao<T, ?> getDao(Class<T> type) throws SQLException;

}
