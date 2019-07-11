package com.gmail.aazavoykin;

import com.gmail.aazavoykin.storage.PathStorage;
import com.gmail.aazavoykin.storage.serializer.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_STRING_PATH, new JsonStreamSerializer()));
    }
}