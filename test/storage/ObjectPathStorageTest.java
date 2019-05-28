package storage;

import storage.serializer.ObjectStreamSerializer;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_STRING_PATH, new ObjectStreamSerializer()));
    }
}