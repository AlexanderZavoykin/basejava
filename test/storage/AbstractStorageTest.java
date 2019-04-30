package storage;

import exception.ResumeAlreadyExistsStorageException;
import exception.ResumeDoesNotExistStorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {
    protected Storage storage;
    private final String UUID_1 = "uuid_1";
    private final String UUID_2 = "uuid_2";
    private final String UUID_3 = "uuid_3";
    private final String UUID_CHECK = "uuid_check";
    private final Resume RESUME_1 = new Resume(UUID_1, "Mark Twain");
    private final Resume RESUME_2 = new Resume(UUID_2, "Jorge Amado");
    private final Resume RESUME_3 = new Resume(UUID_3, "Ernest Hemingway");
    protected final Resume RESUME_CHECK = new Resume(UUID_CHECK, "Pancho Villa");

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
        storage.save(RESUME_CHECK);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(RESUME_CHECK, storage.get(UUID_CHECK));
    }

    @Test(expected = ResumeAlreadyExistsStorageException.class)
    public void saveAlreadyExisting() {
        storage.save(RESUME_1);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1, "Emiliano Zapata");
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
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(Arrays.asList(RESUME_3, RESUME_2, RESUME_1), list);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

}