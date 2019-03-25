/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume resume) {
        if (find(resume.getUuid()) != -1) {
            System.out.println("ERROR! Resume already exists. Can`t save.");
        } else {
            if (size < storage.length) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("ERROR! Not enough free space in the storage. Can`t save.");
            }
        }
    }

    public void update(Resume resume) {
        if (find(resume.getUuid()) == -1) {
            System.out.println("Resume is not found. Can`t update.");
        } else {
            storage[find(resume.getUuid())] = resume;
        }
    }

    public Resume get(String uuid) {
        if (find(uuid) != -1) {
            return storage[find(uuid)];
        } else {
            System.out.println("Resume is not found. Can`t get.");
            return null;
        }
    }

    public void delete(String uuid) {
        if (find(uuid) == -1) {
            System.out.println("ERROR! Resume is not found. Can`t delete.");
        } else {
            storage[find(uuid)] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    private int find(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public Resume[] getAll() {
        Resume[] onlyResumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            onlyResumes[i] = storage[i];
        }
        return onlyResumes;
    }

    public int size() {
        return size;
    }
}
