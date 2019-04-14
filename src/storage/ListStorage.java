package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    List<Resume> storage = new ArrayList();

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
    protected int getIndex(String uuid) {
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
    protected void insert(Resume resume, int index) {
        storage.add(resume);
    }

    @Override
    protected void doUpdate(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected Resume doGet(int index) {
        return storage.get(index);
    }

    @Override
    protected void doDelete(int index) {
        storage.remove(index);
    }

}
