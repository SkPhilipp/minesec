package net.minesec.rules.api.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.H2DatabaseType;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import net.minesec.rules.api.Database;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Copyright (c) 29-7-17, MineSec. All rights reserved.
 */
public class DatabaseImpl implements Database {

    private final DataSourceConnectionSource dataSourceConnectionSource;

    public DatabaseImpl(DataSource dataSource) throws SQLException {
        this.dataSourceConnectionSource = new DataSourceConnectionSource(dataSource, new H2DatabaseType());
    }

    @Override
    public <T> Dao<T, ?> getDao(Class<T> type) throws SQLException {
        return DaoManager.createDao(dataSourceConnectionSource, type);
    }


}
