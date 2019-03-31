package storage;

import model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void add(Resume resume) {
        storage[size] = resume;
        size++;
    }

    @Override
    protected void remove(String uuid) {
        storage[getIndex(uuid)] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
