package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;
import java.util.List;

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
    public List<Resume> getList() {
        return Arrays.asList(Arrays.copyOf(storage, size()));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean hasElement(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        if (size() >= STORAGE_SIZE) {
            throw new StorageException("The storage has no more free space", resume.getUuid());
        } else {
            insert(resume, (Integer) searchKey);
            size++;
        }
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage[(Integer) searchKey] = resume;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    @Override
    protected void doDelete(Object searchKey) {
        displace((Integer) searchKey);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void displace(int index);

    protected abstract void insert(Resume resume, int index);

}
