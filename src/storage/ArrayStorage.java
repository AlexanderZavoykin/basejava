package storage;

import model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insert(Resume resume, Object key) {
        storage[size] = resume;
    }

    @Override
    protected void displace(Object key) {
        storage[(Integer) key] = storage[size - 1];
    }

    @Override
    protected Object getKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
