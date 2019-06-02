package storage;

import storage.serializer.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_STRING_PATH, new JsonStreamSerializer()));
    }
}