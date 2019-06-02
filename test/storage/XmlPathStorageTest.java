package storage;

import storage.serializer.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_STRING_PATH, new XmlStreamSerializer()));
    }
}