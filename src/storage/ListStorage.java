package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Object getKey(String uuid) {
        int index = 0;
        for (Resume r : storage) {
            if (r.getUuid().equals(uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    protected boolean hasElement(Object key) {
        Resume r = new Resume((String) key);
        return storage.contains(r);
    }

    @Override
    protected void doSave(Resume resume, Object key) {
        storage.add(resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object key) {
        storage.set((int) key, resume);
    }

    @Override
    protected Resume doGet(Object key) {
        return storage.get((int) key);
    }

    @Override
    protected void doDelete(Object key) {
        storage.remove((int) key);
    }

}
