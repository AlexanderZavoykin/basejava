package storage.serializer;

import model.*;
import util.XmlParser;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class XmlStreamSerializer implements StreamSerializer {
    private XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(Resume.class, Organization.class, Organization.Period.class,
                ListSection.class, TextSection.class, OrganizationSection.class);
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, UTF_8)) {
            xmlParser.marshal(resume, writer);
        }

    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is)) {
            return xmlParser.unmarshal(reader);
        }

    }
}
