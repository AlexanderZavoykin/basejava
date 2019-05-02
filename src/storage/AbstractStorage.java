package storage;

import exception.ResumeAlreadyExistsStorageException;
import exception.ResumeDoesNotExistStorageException;
import model.Resume;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SearchKey> implements Storage {

    private static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

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

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getList();
        Collections.sort(list, RESUME_COMPARATOR);
        return list;
    }

    private SearchKey getExistingSearchKey(String uuid) {
        SearchKey searchKey = getSearchKey(uuid);
        if (hasElement(searchKey)) {
            throw new ResumeAlreadyExistsStorageException(uuid);
        }
        return searchKey;
    }

    private SearchKey getNotExistingSearchKey(String uuid) {
        SearchKey searchKey = getSearchKey(uuid);
        if (!hasElement(searchKey)) {
            throw new ResumeDoesNotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract List<Resume> getList();

    protected abstract SearchKey getSearchKey(String uuid);

    protected abstract boolean hasElement(SearchKey searchKey);

    protected abstract void doSave(Resume resume, SearchKey searchKey);

    protected abstract void doUpdate(Resume resume, SearchKey searchKey);

    protected abstract Resume doGet(SearchKey searchKey);

    protected abstract void doDelete(SearchKey searchKey);
}
