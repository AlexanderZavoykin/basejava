/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[4];
    int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume resume) {
        if (find(resume) == -1) {
            if (size < storage.length) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("ERROR! Not enough free space in the storage. Can`t save.");
            }
        } else {
            System.out.println("ERROR! Resume already exists. Can`t save.");
        }
    }

    public int find(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(resume.uuid)) {
                return i;
            }
        }
        return -1;
    }

    public int find(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void update(Resume resume) {
        if (find(resume) == -1) {
            System.out.println("Resume is not found. Can`t update.");
        } else {
            storage[find(resume)] = resume;
        }
    }

    public Resume get(String uuid) {
        if (find(uuid) == -1) {
            System.out.println("Resume is not found. Can`t get.");
            return null;
        } else {
            return storage[find(uuid)];
        }
    }

    public void delete(String uuid) {
        boolean isFound = false;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = storage[size - 1];
                size--;
                storage[size] = null;
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("ERROR! Resume is not found. Can`t delete.");
        }
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
