package storage;

import exception.ResumeAlreadyExistsStorageException;
import exception.ResumeDoesNotExistStorageException;
import exception.StorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {
    protected static final int STORAGE_SIZE = 10000;
    protected int size = 0;

    @Override
    public abstract void clear();

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ResumeAlreadyExistsStorageException(resume.getUuid());
        } else {
            if (size() >= STORAGE_SIZE) {
                throw new StorageException("The storage has no more free space", resume.getUuid());
            } else {
                insert(resume, index);
                size++;
            }
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

    protected abstract void insert(Resume resume, int index);

    protected abstract void doUpdate(Resume resume, int index);

    protected abstract Resume doGet(int index);

    protected abstract void doDelete(int index);


}
