package com.gmail.aazavoykin;

import com.gmail.aazavoykin.storage.FileStorage;
import com.gmail.aazavoykin.storage.serializer.ObjectStreamSerializer;

public class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIRECTORY, new ObjectStreamSerializer()));
    }
}