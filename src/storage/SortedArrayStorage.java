package storage;

import model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> UUID_COMPARATOR = (r1, r2) -> r1.getUuid().compareTo(r2.getUuid());

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
    protected Object getKey(String uuid) {
        Resume r = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, r, UUID_COMPARATOR);
    }
}
