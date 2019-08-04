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

public abstract class AbstractStorageTest {
    protected static final int STORAGE_SIZE = 1000;
    static final String STORAGE_STRING_PATH = "E:\\JAVA\\basejava\\storage";
    static final File STORAGE_DIRECTORY = Config.getInstance().getStorageDir();
    //new File(STORAGE_STRING_PATH);

    protected Storage storage;
    private static final String UUID_1 = "uuid_1";
    private static final String UUID_2 = "uuid_2";
    private static final String UUID_3 = "uuid_3";
    private static final String UUID_CHECK = "uuid_check";
    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_CHECK;

    static {
        RESUME_1 = new Resume(UUID_1, "Mark Twain");
        RESUME_2 = new Resume(UUID_2, "Jorge Amado");
        RESUME_3 = new Resume(UUID_3, "Ernest Hemingway");
        RESUME_CHECK = new Resume(UUID_CHECK, "Pancho Villa");

        RESUME_1.addContact(ContactType.EMAIL, "resume_1@gmail.com");
        RESUME_1.addContact(ContactType.SKYPE, "resume_1_skype");
        RESUME_1.addSection(SectionType.PERSONAL, new TextSection("Personal"));
        RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("Objective"));
        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListSection("Achievement_1", "Achievement_2", "Achievement_3"));
        RESUME_1.addSection(SectionType.QUALIFICATION, new ListSection("Qualification_1", "Qualification_2"));
        /*RESUME_1.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization(new Link("CompanyName", "CompanyURL"),
                new Organization.Period(YearMonth.of(1990, 01), YearMonth.of(1992, 06),
                        "Title", "Description"),
                new Organization.Period(YearMonth.of(1990, 01), YearMonth.of(1992, 06),
                        "Title", "Description"))));
        RESUME_1.addSection(SectionType.EDUCATION, new OrganizationSection(
                new Organization(new Link("University", null),
                        new Organization.Period(YearMonth.of(1982, 01), YearMonth.of(1985, 01),
                                "Title", null),
                        new Organization.Period(YearMonth.of(1985, 02), YearMonth.of(1989, 12),
                                "Title", "Description"))));*/

        RESUME_2.addContact(ContactType.EMAIL, "resume_2@gmail.com");
        RESUME_2.addContact(ContactType.SKYPE, "resume_2_skype");
        /*RESUME_2.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization(new Link("Company", null),
                        new Organization.Period(YearMonth.of(2000, 01), YearMonth.of(2002, 01),
                                "Title", null),
                        new Organization.Period(YearMonth.of(2005, 01), YearMonth.of(2008, 01),
                                "Title", "Description"))));*/
    }


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
        Assert.assertEquals(new ArrayList<Resume>(Arrays.asList(RESUME_3, RESUME_2, RESUME_1)), list);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

}