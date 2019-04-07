package storage;

import exception.ResumeDoesNotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private final String UUID_1 = "uuid_1";
    private final String UUID_2 = "uuid_2";
    private final String UUID_3 = "uuid_3";
    private final String UUID_4 = "uuid_4";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test (expected = ResumeDoesNotExistStorageException.class)
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
        Assert.assertNull(storage.get(UUID_1));
        Assert.assertNull(storage.get(UUID_2));
        Assert.assertNull(storage.get(UUID_3));
    }

    @Test
    public void save() {
        storage.save(new Resume(UUID_4));
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(UUID_4, storage.get(UUID_4).getUuid());
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        Assert.assertEquals(newResume, storage.get(UUID_1));
    }

    @Test
    public void get() {
        Assert.assertEquals(UUID_1, storage.get(UUID_1).getUuid());
        Assert.assertEquals(UUID_2, storage.get(UUID_2).getUuid());
        Assert.assertEquals(UUID_3, storage.get(UUID_3).getUuid());
    }

    @Test (expected = ResumeDoesNotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_3);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_3);
    }

    @Test
    public void getAll() {
        Resume[] checkStorage = new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        Assert.assertArrayEquals(checkStorage, storage.getAll());
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test (expected = StorageException.class)
    public void saveToFullStorage() {
            try {
                for (int i = 1; i <= 9997; i++) {
                    storage.save(new Resume());
                }
            } catch (StorageException e) {
                Assert.fail();
            }
            storage.save(new Resume());
    }

    @Test (expected = ResumeDoesNotExistStorageException.class)
    public void getNotExisting() {
        storage.get(UUID_4);
    }


}