package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        int searchResult = findIndex(resume.getUuid());
        if (searchResult >= 0) {
            System.out.println("ERROR! Resume already exists. Can`t save.");
        } else {
            if (size < STORAGE_SIZE) {
                int insertionPoint = -(searchResult + 1);
                for (int i = size; i > insertionPoint; i--) {
                    storage[i] = storage[i - 1];
                }
                storage[insertionPoint] = resume;
                size++;
            } else {
                System.out.println("ERROR! Not enough free space in the storage. Can`t save.");
            }
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR! Resume is not found. Can`t delete.");
        } else {
            for (int i = index; i < size - 1; i++) {
                storage[i] = storage[i + 1];
            }
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    protected int findIndex(String uuid) {
        Resume r = new Resume();
        r.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, r);
    }
}
