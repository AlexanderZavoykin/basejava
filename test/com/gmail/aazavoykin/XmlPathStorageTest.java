package com.gmail.aazavoykin;

import com.gmail.aazavoykin.storage.PathStorage;
import com.gmail.aazavoykin.storage.serializer.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_STRING_PATH, new XmlStreamSerializer()));
    }
}