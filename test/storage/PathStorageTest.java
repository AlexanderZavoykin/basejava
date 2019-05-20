package storage;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_STRING_PATH, new ObjectStreamSerializer()));
    }
}