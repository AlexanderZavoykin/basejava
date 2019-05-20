package storage;

import model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public interface StreamSerializer {
    void doWrite(Resume resume, BufferedOutputStream bos) throws IOException;

    Resume doRead(BufferedInputStream bis) throws IOException;
}
