package com.gmail.aazavoykin;

import com.gmail.aazavoykin.storage.PathStorage;
import com.gmail.aazavoykin.storage.serializer.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_STRING_PATH, new DataStreamSerializer()));
    }
}