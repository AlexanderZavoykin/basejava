package storage;


import exception.StorageException;
import model.Resume;
import storage.serializer.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;
    private StreamSerializer streamSerializer;

    public FileStorage(File directory, StreamSerializer streamSerializer) {
        this.streamSerializer = streamSerializer;
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
        List<Resume> list = new ArrayList<>();
        File[] fileArray = directory.listFiles();
        if (fileArray != null) {
            for (File f : fileArray) {
                list.add(doGet(f));
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
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Can not create new file", file.getName(), e);
        }
        doUpdate(resume, file);

    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            streamSerializer.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Can not update file", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Can not get resume from file", file.getName(), e);
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
        String[] array = directory.list();
        if (array != null) {
            return array.length;
        } else {
            throw new StorageException("Can not get String array of files", null);
        }
    }
}
