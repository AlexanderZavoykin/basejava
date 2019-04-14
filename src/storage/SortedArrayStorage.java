package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insert(Resume resume, int index) {
        int addingPoint = -index - 1;
        System.arraycopy(storage, addingPoint, storage, addingPoint + 1, size - addingPoint);
        storage[addingPoint] = resume;
    }

    @Override
    protected void displace(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume r = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, r);
    }
}
