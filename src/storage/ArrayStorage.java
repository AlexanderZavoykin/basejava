package storage;

import model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        if (findIndex(resume.getUuid()) != -1) {
            System.out.println("ERROR! Resume already exists. Can`t save.");
        } else {
            if (size >= STORAGE_SIZE) {
                System.out.println("ERROR! Not enough free space in the storage. Can`t save.");
            } else {
                storage[size] = resume;
                size++;
            }
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("ERROR! Resume is not found. Can`t delete.");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
