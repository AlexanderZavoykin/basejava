package storage;


import exception.StorageException;
import model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private StreamSerializer streamSerializer;

    public PathStorage(String dir, StreamSerializer streamSerializer) {
        directory = Paths.get(dir);
        this.streamSerializer = streamSerializer;
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(dir + " is not a directory");
        }
        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not readable or writable");
        }
    }

    @Override
    protected List<Resume> getList() {
        List<Resume> list = new ArrayList<>();
        try {
            Files.list(directory).forEach(path -> list.add(doGet(path)));
        } catch (IOException e) {
            throw new StorageException("Can not get list of resumes", null, e);
        }
        return list;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected boolean hasElement(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            streamSerializer.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Can not save file with this path", path.toString(), e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            streamSerializer.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Can not update file with this path", path.toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Can not get file from this path", path.toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Can not delete file with this path", path.toString(), e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Can not clear directory", directory.toString(), e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Size counting error", null, e);
        }
    }

}
