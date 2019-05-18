package storage;

import exception.ResumeAlreadyExistsStorageException;
import exception.ResumeDoesNotExistStorageException;
import model.Resume;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SearchKey> implements Storage {

    private final static Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());

    private static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    protected abstract List<Resume> getList();

    protected abstract SearchKey getSearchKey(String uuid);

    protected abstract boolean hasElement(SearchKey searchKey);

    protected abstract void doSave(Resume resume, SearchKey searchKey);

    protected abstract void doUpdate(Resume resume, SearchKey searchKey);

    protected abstract Resume doGet(SearchKey searchKey);

    protected abstract void doDelete(SearchKey searchKey);

    @Override
    public void save(Resume resume) {
        LOGGER.info("save " + resume);
        doSave(resume, getNotExistingSearchKey(resume.getUuid()));
    }

    @Override
    public void update(Resume resume) {
        LOGGER.info("update " + resume);
        doUpdate(resume, getExistingSearchKey(resume.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        LOGGER.info("get " + uuid);
        return doGet(getExistingSearchKey(uuid));
    }

    @Override
    public void delete(String uuid) {
        LOGGER.info("delete " + uuid);
        doDelete(getExistingSearchKey(uuid));
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getList();
        Collections.sort(list, RESUME_COMPARATOR);
        return list;
    }

    private SearchKey getNotExistingSearchKey(String uuid) {
        SearchKey searchKey = getSearchKey(uuid);
        if (hasElement(searchKey)) {
            LOGGER.warning("getNotExistingSearchKey " + uuid);
            throw new ResumeAlreadyExistsStorageException(uuid);
        }
        return searchKey;
    }

    private SearchKey getExistingSearchKey(String uuid) {
        SearchKey searchKey = getSearchKey(uuid);
        if (!hasElement(searchKey)) {
            LOGGER.warning("getExistingSearchKey " + uuid);
            throw new ResumeDoesNotExistStorageException(uuid);
        }
        return searchKey;
    }


}
