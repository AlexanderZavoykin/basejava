package storage;

import exception.ResumeAlreadyExistsStorageException;
import exception.ResumeDoesNotExistStorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {


    @Override
    public abstract void clear();

    @Override
    public void save(Resume resume) {
        if (hasElement(resume)) {
            throw new ResumeAlreadyExistsStorageException(resume.getUuid());
        } else {
            Object key = getKey(resume.getUuid());
            doSave(resume, key);
        }
    }

    @Override
    public void update(Resume resume) {
        if (!hasElement(resume)) {
            throw new ResumeDoesNotExistStorageException(resume.getUuid());
        } else {
            Object key = getKey(resume.getUuid());
            doUpdate(resume, key);
        }
    }

    @Override
    public Resume get(String uuid) {
        Resume r = new Resume(uuid);
        if (!hasElement(r)) {
            throw new ResumeDoesNotExistStorageException(uuid);
        } else {
            Object key = getKey(uuid);
            return doGet(key);
        }
    }

    @Override
    public void delete(String uuid) {
        Resume r = new Resume(uuid);
        if (!hasElement(r)) {
            throw new ResumeDoesNotExistStorageException(uuid);
        } else {
            Object key = getKey(uuid);
            doDelete(key);
        }
    }

    @Override
    public abstract Resume[] getAll();

    @Override
    public abstract int size();

    protected abstract Object getKey(String uuid);

    protected abstract boolean hasElement(Object key);

    protected abstract void doSave(Resume resume, Object key);

    protected abstract void doUpdate(Resume resume, Object key);

    protected abstract Resume doGet(Object key);

    protected abstract void doDelete(Object key);
}
