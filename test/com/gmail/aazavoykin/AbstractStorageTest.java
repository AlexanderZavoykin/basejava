package com.gmail.aazavoykin;

import com.gmail.aazavoykin.exception.ResumeAlreadyExistsStorageException;
import com.gmail.aazavoykin.exception.ResumeDoesNotExistStorageException;
import com.gmail.aazavoykin.model.*;
import com.gmail.aazavoykin.storage.Storage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.gmail.aazavoykin.ResumeTestData.*;

public abstract class AbstractStorageTest {
    protected static final int STORAGE_SIZE = 1000;
    static final String STORAGE_STRING_PATH = "E:\\JAVA\\basejava\\storage";
    static final File STORAGE_DIRECTORY = Config.getInstance().getStorageDir();
    //new File(STORAGE_STRING_PATH);

    protected Storage storage;

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
        //doesn`t work with serialized resume
        //Assert.assertSame(newResume, storage.get(UUID_1));
        for (Map.Entry<ContactType, String> e : newResume.getContacts().entrySet()) {
            System.out.println(e.getKey().toString() + "-" + e.getValue());
        }
        Assert.assertEquals(newResume, storage.get(UUID_1));

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
        Assert.assertEquals(new ArrayList<>(Arrays.asList(RESUME_3, RESUME_2, RESUME_1)), list);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

}