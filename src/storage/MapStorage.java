package storage;

import model.Resume;

public class MapStorage extends AbstractStorage {
    @Override
    public void clear() {
    }

    @Override
    public void save(Resume resume) {
    }

    @Override
    public void update(Resume resume) {
    }

    @Override
    public Resume get(String uuid) {
        return null;
    }

    @Override
    public void delete(String uuid) {
    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    protected int getIndex(String uuid) {
        return 0;
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
    }

    @Override
    protected void doSave(Resume resume) {

    }
}
