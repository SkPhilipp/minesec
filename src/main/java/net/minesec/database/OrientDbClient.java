package net.minesec.database;

import com.orientechnologies.orient.client.remote.OServerAdmin;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.db.OPartitionedDatabasePoolFactory;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentPool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;

import java.io.IOException;

/**
 * Copyright (c) 24-7-17, MineSec. All rights reserved.
 */
public class OrientDbClient {

    public static void main(String[] args) throws IOException {
        final OPartitionedDatabasePoolFactory oPartitionedDatabasePoolFactory = new OPartitionedDatabasePoolFactory();
        oPartitionedDatabasePoolFactory.setMaxPoolSize(20);
        final OPartitionedDatabasePool oPartitionedDatabasePool = oPartitionedDatabasePoolFactory.get("remote:localhost/minesec", "admin", "admin");
        try (ODatabaseDocumentTx ignored = oPartitionedDatabasePool.acquire()) {
            ODocument doc = new ODocument("Person");
            doc.field("name", "Luke");
            doc.field("surname", "Skywalker");
            doc.field("city", new ODocument("City").field("name", "Rome").field("country", "Italy"));
            doc.save();
        } finally {
            oPartitionedDatabasePool.close();
        }
    }
}