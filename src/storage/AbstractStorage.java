package storage;

import exception.ResumeAlreadyExistsStorageException;
import exception.ResumeDoesNotExistStorageException;
import model.Resume;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    protected static final Comparator<Resume> FULL_NAME_COMPARATOR =
            (r1, r2) -> r1.getFullName().compareTo(r2.getFullName());

    protected static final Comparator<Resume> UUID_COMPARATOR =
            (r1, r2) -> r1.getUuid().compareTo(r2.getUuid());

    @Override
    public void save(Resume resume) {
        doSave(resume, getExistingSearchKey(resume.getUuid()));
    }

    @Override
    public void update(Resume resume) {
        doUpdate(resume, getNotExistingSearchKey(resume.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        return doGet(getNotExistingSearchKey(uuid));
    }

    @Override
    public void delete(String uuid) {
        doDelete(getNotExistingSearchKey(uuid));
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (hasElement(searchKey)) {
            throw new ResumeAlreadyExistsStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!hasElement(searchKey)) {
            throw new ResumeDoesNotExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getList();
        Collections.sort(list, FULL_NAME_COMPARATOR.thenComparing(UUID_COMPARATOR));
        return list;
    }

    protected abstract List<Resume> getList();

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean hasElement(Object searchKey);

    protected abstract void doSave(Resume resume, Object searchKey);

    protected abstract void doUpdate(Resume resume, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doDelete(Object searchKey);
}
