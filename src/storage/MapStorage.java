package storage;

import model.Resume;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Collection<Resume> valueCollection = storage.values();
        return valueCollection.toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int getIndex(String uuid) {
        if (storage.containsKey(uuid)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    protected void doUpdate(Resume resume, int index) {
    }

    @Override
    protected Resume doGet(int index) {
        return null;
    }

    @Override
    protected void doDelete(int index) {
        storage.remove(null);
    }

    @Override
    protected void doSave(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }
}
