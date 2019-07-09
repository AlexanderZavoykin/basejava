import exception.StorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Test;
import storage.AbstractArrayStorage;
import storage.Storage;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveToFullStorage() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_SIZE; i++) {
                storage.save(new Resume("new" + i, "John Smith"));
            }
        } catch (StorageException e) {
            Assert.fail("Test is failed: storage was fullfilled before");
        }
        storage.save(new Resume("one more", "Jane Dow"));
    }
}