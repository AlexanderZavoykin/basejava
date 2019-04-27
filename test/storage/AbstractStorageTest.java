package storage;

import exception.ResumeAlreadyExistsStorageException;
import exception.ResumeDoesNotExistStorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public abstract class AbstractStorageTest {
    protected Storage storage;
    private final String UUID_1 = "uuid_1";
    private final String UUID_2 = "uuid_2";
    private final String UUID_3 = "uuid_3";
    private final String UUID_CHECK = "uuid_check";
    private final String FULL_NAME_1 = "Mark Twain";
    private final String FULL_NAME_2 = "Jorge Amado";
    private final String FULL_NAME_3 = "Ernest Hemingway";
    private final String FULL_NAME_CHECK = "Pancho Villa";
    private final Resume RESUME_1 = new Resume(UUID_1, FULL_NAME_1);
    private final Resume RESUME_2 = new Resume(UUID_2, FULL_NAME_2);
    private final Resume RESUME_3 = new Resume(UUID_3, FULL_NAME_3);
    private final Resume RESUME_CHECK = new Resume(UUID_CHECK, FULL_NAME_CHECK);

    protected AbstractStorageTest(Storage storage) {
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
        Resume[] checkStorage = new Resume[]{new Resume(UUID_1, FULL_NAME_1), new Resume(UUID_2, FULL_NAME_2), new Resume(UUID_3, FULL_NAME_3)};
        Arrays.sort(checkStorage, AbstractStorage.FULL_NAME_COMPARATOR);
        Assert.assertArrayEquals(checkStorage, storage.getAllSorted().toArray());
        Assert.assertEquals(checkStorage.length, storage.size());
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

}