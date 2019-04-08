package storage;

import exception.ResumeAlreadyExistsStorageException;
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
    private final String UUID_CHECK = "uuid_check";
    private final Resume RESUME_1 = new Resume(UUID_1);
    private final Resume RESUME_2 = new Resume(UUID_2);
    private final Resume RESUME_3 = new Resume(UUID_3);
    private final Resume RESUME_CHECK = new Resume(UUID_CHECK);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(new Resume(UUID_CHECK));
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(RESUME_CHECK, storage.get(UUID_CHECK));
    }

    @Test(expected = ResumeAlreadyExistsStorageException.class)
    public void saveAlreadyExisting() {
        storage.save(RESUME_1);
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

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        Assert.assertSame(newResume, storage.get(UUID_1));
    }

    @Test(expected = ResumeDoesNotExistStorageException.class)
    public void updateNotExisting() {
        storage.update(RESUME_CHECK);
    }

    @Test
    public void get() {
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
        Assert.assertEquals(RESUME_2, storage.get(UUID_2));
        Assert.assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = ResumeDoesNotExistStorageException.class)
    public void getNotExisting() {
        storage.get(UUID_CHECK);
    }

    @Test(expected = ResumeDoesNotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_3);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_3);
    }

    @Test(expected = ResumeDoesNotExistStorageException.class)
    public void deleteNotExisting() {
        storage.delete(UUID_CHECK);
    }

    @Test
    public void getAll() {
        Resume[] checkStorage = new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        Assert.assertArrayEquals(checkStorage, storage.getAll());
        Assert.assertEquals(checkStorage.length, storage.size());
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

}