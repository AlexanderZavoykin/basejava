package storage;

import exception.ResumeAlreadyExistsStorageException;
import exception.ResumeDoesNotExistStorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {


    @Override
    public abstract void clear();

    @Override
    public void save(Resume resume) {
        Object key = getKey(resume.getUuid());
        if (hasElement(resume)) {
            throw new ResumeAlreadyExistsStorageException(resume.getUuid());
        } else {
            doSave(resume, key);
        }
    }

    @Override
    public void update(Resume resume) {
        Object key = getKey(resume.getUuid());

        if (!hasElement(resume)) {
            throw new ResumeDoesNotExistStorageException(resume.getUuid());
        } else {
            doUpdate(resume, key);
        }
    }

    @Override
    public Resume get(String uuid) {
        Resume r = new Resume(uuid);
        Object key = getKey(uuid);
        if (hasElement(r)) {
            return doGet(key);
        } else {
            throw new ResumeDoesNotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        Resume r = new Resume(uuid);
        Object key = getKey(uuid);
        if (!hasElement(r)) {
            throw new ResumeDoesNotExistStorageException(uuid);
        } else {
            doDelete(key);
        }
    }


    @Override
    public abstract Resume[] getAll();

    @Override
    public abstract int size();

    protected abstract Object getKey(String uuid);

    protected abstract boolean hasElement(Resume resume);

    protected abstract void doSave(Resume resume, Object key);

    protected abstract void doUpdate(Resume resume, Object key);

    protected abstract Resume doGet(Object key);

    protected abstract void doDelete(Object key);
}
