package storage;

import exception.ResumeAlreadyExistsStorageException;
import exception.ResumeDoesNotExistStorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {


    @Override
    public abstract void clear();

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ResumeAlreadyExistsStorageException(resume.getUuid());
        } else {
            doSave(resume, index);
        }
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new ResumeDoesNotExistStorageException(resume.getUuid());
        } else {
            doUpdate(resume, index);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return doGet(index);
        } else {
            throw new ResumeDoesNotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new ResumeDoesNotExistStorageException(uuid);
        } else {
            doDelete(index);
        }
    }

    @Override
    public abstract Resume[] getAll();

    @Override
    public abstract int size();

    protected abstract int getIndex(String uuid);

    protected abstract void doUpdate(Resume resume, int index);

    protected abstract Resume doGet(int index);

    protected abstract void doDelete(int index);

    protected abstract void doSave(Resume resume, int index);


}
