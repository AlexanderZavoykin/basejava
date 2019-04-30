package storage;

import exception.ResumeAlreadyExistsStorageException;
import exception.ResumeDoesNotExistStorageException;
import model.Resume;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    //https://blog.jooq.org/2014/01/31/java-8-friday-goodies-lambdas-and-sorting/
    private static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing((Resume r) -> r.getFullName()).thenComparing(r -> r.getUuid());

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

    protected abstract List<Resume> getList();

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean hasElement(Object searchKey);

    protected abstract void doSave(Resume resume, Object searchKey);

    protected abstract void doUpdate(Resume resume, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doDelete(Object searchKey);
}
