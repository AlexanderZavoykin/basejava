package storage;

import exception.AlreadyExistsStorageException;
import exception.DoesntExistStorageException;
import exception.StorageException;
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
            throw new AlreadyExistsStorageException(resume.getUuid());
        } else {
            if (size >= STORAGE_SIZE) {
                throw new StorageException("The storage has no more free space", resume.getUuid());
            } else {
                add(resume, index);
                size++;
            }
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new DoesntExistStorageException(resume.getUuid());
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
