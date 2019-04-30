package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UuidMapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getList() {
        return new ArrayList<Resume>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean hasElement(Object searchkey) {
        return storage.containsKey(searchkey);
    }

    @Override
    protected void doSave(Resume resume, Object searchkey) {
        storage.put((String) searchkey, resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchkey) {
        storage.put((String) searchkey, resume);
    }

    @Override
    protected Resume doGet(Object searchkey) {
        return storage.get((String) searchkey);
    }

    @Override
    protected void doDelete(Object searchkey) {
        storage.remove((String) searchkey);
    }
}
