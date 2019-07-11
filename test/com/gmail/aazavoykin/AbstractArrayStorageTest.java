package com.gmail.aazavoykin;

import com.gmail.aazavoykin.exception.StorageException;
import com.gmail.aazavoykin.model.Resume;
import com.gmail.aazavoykin.storage.AbstractArrayStorage;
import com.gmail.aazavoykin.storage.Storage;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    AbstractArrayStorageTest(Storage storage) {
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