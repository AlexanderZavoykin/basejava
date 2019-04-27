package storage;

import model.Resume;

import java.util.*;

public class UuidMapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        Collection<Resume> valueCollection = storage.values();
        List<Resume> resumeList = new ArrayList<>(valueCollection);
        Collections.sort(resumeList, FULL_NAME_COMPARATOR);
        return resumeList;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Object getKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean hasElement(Object key) {
        return storage.containsKey(key);
    }

    @Override
    protected void doSave(Resume resume, Object key) {
        storage.put((String) key, resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object key) {
        storage.put((String) key, resume);
    }

    @Override
    protected Resume doGet(Object key) {
        return storage.get((String) key);
    }

    @Override
    protected void doDelete(Object key) {
        storage.remove((String) key);
    }
}
