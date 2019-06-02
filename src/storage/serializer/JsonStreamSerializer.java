package storage.serializer;

import model.Resume;
import util.JsonParser;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JsonStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, UTF_8)) {
            JsonParser.write(resume, writer);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is)) {
            return JsonParser.read(reader, Resume.class);
        }
    }
}
