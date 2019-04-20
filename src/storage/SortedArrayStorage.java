package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insert(Resume resume, Object key) {
        int index = (Integer) key;
        int addingPoint = - index - 1;
        System.arraycopy(storage, addingPoint, storage, addingPoint + 1, size - addingPoint);
        storage[addingPoint] = resume;
    }

    @Override
    protected void displace(Object key) {
        int index = (Integer) key;
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected Object getKey(String uuid) {
        Resume r = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, r);
    }
}
