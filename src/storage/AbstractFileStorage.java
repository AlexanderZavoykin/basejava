package storage;


import exception.StorageException;
import model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

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

    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected List<Resume> getList() {
        List<Resume> list = new ArrayList<>();
        File[] fileArray = directory.listFiles();
        if (fileArray != null) {
            for (File f : fileArray) {
                try {
                    list.add(doRead(new FileInputStream(f)));
                } catch (IOException e) {
                    throw new StorageException("IO error", f.getName(), e);
                }
            }
            return list;
        }
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
            doWrite(resume, new FileOutputStream(file));
        } catch (IOException e) {
            throw new StorageException("Saving error", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, new FileOutputStream(file));
        } catch (IOException e) {
            throw new StorageException("Updating error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Getting error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        file.delete();
    }

    @Override
    public void clear() {
        File[] fileArray = directory.listFiles();
        if (fileArray != null)
            for (File f : fileArray) {
                doDelete(f);
            }
    }

    @Override
    public int size() {
        File[] fileArray = directory.listFiles();
        int size = 0;
        if (fileArray != null) {
            for (File f : fileArray) {
                if (f.isFile()) {
                    size++;
                }
            }
        }
        return size;
    }
}
