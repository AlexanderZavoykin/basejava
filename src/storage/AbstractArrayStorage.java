package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_SIZE = 10000;
    protected int size = 0;

    protected Resume[] storage = new Resume[STORAGE_SIZE];

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected void doUpdate(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected Resume doGet(int index) {
        return storage[index];
    }

    @Override
    protected void doDelete(int index) {
        displace(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void doSave(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (size() >= STORAGE_SIZE) {
            throw new StorageException("The storage has no more free space", resume.getUuid());
        } else {
            insert(resume, index);
            size++;
        }
    }

    protected abstract void displace(int index);

    protected abstract void insert(Resume resume, int index);


}
