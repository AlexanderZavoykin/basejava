package com.gmail.aazavoykin;

import com.gmail.aazavoykin.storage.PathStorage;
import com.gmail.aazavoykin.storage.serializer.ObjectStreamSerializer;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_STRING_PATH, new ObjectStreamSerializer()));
    }
}