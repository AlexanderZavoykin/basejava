package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_SIZE = 10000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_SIZE];

    protected abstract void displace(int index);

    protected abstract void insert(Resume resume, int index);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public List<Resume> getList() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean hasElement(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        if (size() >= STORAGE_SIZE) {
            throw new StorageException("The storage has no more free space", resume.getUuid());
        } else {
            insert(resume, searchKey);
            size++;
        }
    }

    @Override
    protected void doUpdate(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected void doDelete(Integer searchKey) {
        displace(searchKey);
        storage[size - 1] = null;
        size--;
    }
}
