package storage;

import exception.ResumeAlreadyExistsStorageException;
import exception.ResumeDoesNotExistStorageException;
import model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    protected static final Comparator<Resume> FULL_NAME_COMPARATOR = (o1, o2) -> {
        if (!o1.getFullName().equals(o2.getFullName())) {
            return o1.getFullName().compareTo(o2.getFullName());
        } else {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    };

    @Override
    public abstract void clear();

    @Override
    public void save(Resume resume) {
        doSave(resume, getExistingKey(resume.getUuid()));
    }

    @Override
    public void update(Resume resume) {
        doUpdate(resume, getNotExistingKey(resume.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        return doGet(getNotExistingKey(uuid));
    }

    @Override
    public void delete(String uuid) {
        doDelete(getNotExistingKey(uuid));
    }

    private Object getExistingKey(String uuid) {
        Object key = getKey(uuid);
        if (hasElement(key)) {
            throw new ResumeAlreadyExistsStorageException(uuid);
        }
        return key;
    }

    private Object getNotExistingKey(String uuid) {
        Object key = getKey(uuid);
        if (!hasElement(key)) {
            throw new ResumeDoesNotExistStorageException(uuid);
        }
        return key;
    }

    @Override
    public abstract List<Resume> getAllSorted();

    @Override
    public abstract int size();

    protected abstract Object getKey(String uuid);

    protected abstract boolean hasElement(Object key);

    protected abstract void doSave(Resume resume, Object key);

    protected abstract void doUpdate(Resume resume, Object key);

    protected abstract Resume doGet(Object key);

    protected abstract void doDelete(Object key);
}
