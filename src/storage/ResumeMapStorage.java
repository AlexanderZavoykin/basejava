package storage;

import model.Resume;

import java.util.*;

public class ResumeMapStorage extends AbstractStorage {
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
    protected Resume getKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean hasElement(Object resume) {
        return storage.containsValue(resume);
    }

    @Override
    protected void doSave(Resume r, Object resume) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doUpdate(Resume r, Object resume) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void doDelete(Object resume) {
        storage.remove(((Resume) resume).getUuid());
    }
}
