package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void add(Resume resume) {
        int insertionPoint = -(getIndex(resume.getUuid()) + 1);
        System.arraycopy(storage, insertionPoint, storage, insertionPoint + 1, size - insertionPoint);
        storage[insertionPoint] = resume;
        size++;
    }

    @Override
    protected void remove(String uuid) {
        int removingPoint = getIndex(uuid);
        System.arraycopy(storage, removingPoint + 1, storage, removingPoint, size - removingPoint - 1);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume r = new Resume();
        r.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, r);
    }
}
