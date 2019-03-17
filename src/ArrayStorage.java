/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
            size = 0;
        }
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        Resume resume = null;
        for (int i = 0; i < size; i++) {
           if (storage[i].uuid.equals(uuid)) {
               resume = storage[i];
           }
        }
        return resume;
    }

    void delete(String uuid) {
        int key = size;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                key = i;
                break;
            }
        }
        if (key < size) {
            for (int k = key; k < size - 1; k++) {
                storage[k]  = storage[k+1];
                size--;
            }
        }

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] onlyResumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            onlyResumes[i] = storage[i];
        }
        return onlyResumes;
    }

    int size() {
        return size;
    }
}
