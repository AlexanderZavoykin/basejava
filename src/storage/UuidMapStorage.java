package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UuidMapStorage extends AbstractStorage<String> {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getList() {
        return new ArrayList<>(storage.values());
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
    protected boolean hasElement(String searchkey) {
        return storage.containsKey(searchkey);
    }

    @Override
    protected void doSave(Resume resume, String searchkey) {
        storage.put(searchkey, resume);
    }

    @Override
    protected void doUpdate(Resume resume, String searchkey) {
        storage.put(searchkey, resume);
    }

    @Override
    protected Resume doGet(String searchkey) {
        return storage.get(searchkey);
    }

    @Override
    protected void doDelete(String searchkey) {
        storage.remove(searchkey);
    }
}
