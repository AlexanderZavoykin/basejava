package storage;


import exception.StorageException;
import model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File>{
    protected File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not a directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable or writable");
        }


        this.directory = directory;
    }

    @Override
    protected List<Resume> getList() {
        return null;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean hasElement(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }



    @Override
    protected void doUpdate(Resume resume, File file) {

    }

    @Override
    protected Resume doGet(File file) {
        return null;
    }

    @Override
    protected void doDelete(File file) {

    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

}
