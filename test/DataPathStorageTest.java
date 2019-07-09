import storage.PathStorage;
import storage.serializer.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_STRING_PATH, new DataStreamSerializer()));
    }
}