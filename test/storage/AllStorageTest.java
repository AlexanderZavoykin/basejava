package storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import storage.serializer.DataStreamSerializer;

@RunWith(Suite.class)
@Suite.SuiteClasses({ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        UuidMapStorageTest.class,
        ResumeMapStorageTest.class,
        ObjectFileStorageTest.class,
        ObjectPathStorageTest.class,
        XmlPathStorageTest.class,
        JsonPathStorageTest.class,
        DataStreamSerializer.class
})
public class AllStorageTest {
}
