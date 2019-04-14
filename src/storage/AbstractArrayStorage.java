package storage;

import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

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

    protected abstract void displace(int index);
}
