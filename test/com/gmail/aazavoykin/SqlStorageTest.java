package com.gmail.aazavoykin;

import com.gmail.aazavoykin.storage.SqlStorage;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(new SqlStorage(Config.getInstance().getDbUrl(),
                Config.getInstance().getDbUser(),
                Config.getInstance().getDbPassword()));
    }




}