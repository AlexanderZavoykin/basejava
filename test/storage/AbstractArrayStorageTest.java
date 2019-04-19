package storage;

import exception.StorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest{

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveToFullStorage() {
        storage.clear();
        try {
            for (int i = 1; i <= AbstractArrayStorage.STORAGE_SIZE; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("Test is failed: storage was fullfilled before");
        }
        storage.save(new Resume());
    }



}