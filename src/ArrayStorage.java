/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume resume) {
        storage[size] = resume;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
           if (storage[i].uuid.equals(uuid)) {
               return storage[i];
           }
        }
        return null;
        }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                for (int k = i; k < size - 1; k++) {
                    storage[k]  = storage[k+1];
                }
                break;
            }
        }
        storage[size] = null;
        size--;
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
