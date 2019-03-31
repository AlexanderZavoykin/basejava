package storage;

import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_SIZE = 10000;

    protected Resume[] storage = new Resume[STORAGE_SIZE];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            System.out.println("ERROR! Resume already exists. Can`t save.");
        } else {
            if (size >= STORAGE_SIZE) {
                System.out.println("ERROR! Not enough free space in the storage. Can`t save.");
            } else {
                add(resume, index);
                size++;
            }
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Resume is not found. Can`t update.");
        } else {
            storage[index] = resume;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("Resume is not found. Can`t get.");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR! Resume is not found. Can`t delete.");
        } else {
            remove(index);
            storage[size - 1] = null;
            size--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    protected abstract void add(Resume resume, int index);

    protected abstract void remove(int index);

    protected abstract int getIndex(String uuid);
}
