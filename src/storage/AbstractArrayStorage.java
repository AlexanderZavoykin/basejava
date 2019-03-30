package storage;

import model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_SIZE = 4;

    protected Resume[] storage = new Resume[STORAGE_SIZE];
    protected int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Resume is not found. Can`t update.");
        } else {
            storage[index] = resume;
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("Resume is not found. Can`t get.");
            return null;
        }
    }

    public Resume[] getAll() {
        Resume[] onlyResumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            onlyResumes[i] = storage[i];
        }
        return onlyResumes;
    }

    public int size() {
        return size;
    }

    public abstract void save(Resume resume);

    public abstract void delete(String uuid);

    protected abstract int findIndex(String uuid);
}
