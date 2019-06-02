package storage.serializer;

import exception.StorageException;
import model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            // writing contacts
            dos.writeInt(resume.getContacts().size());
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            // writing sections
            doWriteTextSection(dos, resume, SectionType.PERSONAL);
            doWriteTextSection(dos, resume, SectionType.OBJECTIVE);
            doWriteListSection(dos, resume, SectionType.ACHIEVEMENT);
            doWriteListSection(dos, resume, SectionType.QUALIFICATION);
            doWriteOrgSection(dos, resume, SectionType.EXPERIENCE);
            doWriteOrgSection(dos, resume, SectionType.EDUCATION);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            // reading contacts
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            // reading sections
            doReadTextSection(dis, resume);
            doReadTextSection(dis, resume);
            doReadListSection(dis, resume);
            doReadListSection(dis, resume);
            doReadOrgSection(dis, resume);
            doReadOrgSection(dis, resume);

            return resume;
        }
    }

    private static void doWriteTextSection(DataOutputStream dos, Resume resume, SectionType sectionType) throws IOException {
        try {
            TextSection textSection = (TextSection) resume.getSection(sectionType);
            dos.writeUTF(sectionType.getTitle());
            String body = textSection.getBody();
            if (!body.equals(null)) {
                dos.writeInt(1);
                dos.writeUTF(body);
            } else {
                dos.writeInt(0);
            }
        } catch (IllegalArgumentException e) {
            throw new StorageException("Illegal section type as method argument", null, e);
        }


    }

    private static void doWriteListSection(DataOutputStream dos, Resume resume, SectionType sectionType) throws IOException {
        ListSection listSection = (ListSection) resume.getSection(sectionType);
        dos.writeUTF(sectionType.getTitle());
        dos.writeInt(listSection.getSkills().size());
        for (String skill : listSection.getSkills()) {
            dos.writeUTF(skill);
        }
    }

    private static void doWriteOrgSection(DataOutputStream dos, Resume resume, SectionType sectionType) throws IOException {
        OrganizationSection orgSection = (OrganizationSection) resume.getSection(sectionType);
        dos.writeUTF(sectionType.getTitle());
        dos.writeInt(orgSection.getOrganizations().size());
        for (Organization organization : orgSection.getOrganizations()) {
            dos.writeUTF(organization.getLink().getName());
            dos.writeUTF(organization.getLink().getUrl());
            dos.writeInt(organization.getPeriods().size());
            for (Organization.Period period : organization.getPeriods()) {
                dos.writeUTF(period.getStartDate().toString());
                dos.writeUTF(period.getEndDate().toString());
                dos.writeUTF(period.getTitle());
                dos.writeUTF(period.getDescription());
            }
        }
    }

    private static void doReadTextSection(DataInputStream dis, Resume resume) throws IOException {
        String title = dis.readUTF();
        int check = dis.readInt();
        String body = null;
        if (check == 1) {
            body = dis.readUTF();
        }
        resume.addSection(SectionType.getSectionType(title), new TextSection(body));
    }

    private static void doReadListSection(DataInputStream dis, Resume resume) throws IOException {
        String title = dis.readUTF();
        int size = dis.readInt();
        List<String> list = new LinkedList();
        for (int i = 0; i < size; i++) {
            list.add(dis.readUTF());
        }
        resume.addSection(SectionType.getSectionType(title), new ListSection(list));
    }

    private static void doReadOrgSection(DataInputStream dis, Resume resume) throws IOException {
        String title = dis.readUTF();
        int orgSize = dis.readInt();
        List<Organization> orgList = new LinkedList();
        for (int i = 0; i < orgSize; i++) {
            Organization org = new Organization(new Link(dis.readUTF(), dis.readUTF()), new LinkedList<Organization.Period>());
            int perSize = dis.readInt();
            for (int j = 0; j < perSize; j++) {
                org.addPeriod(new Organization.Period(YearMonth.parse(dis.readUTF()),
                        YearMonth.parse(dis.readUTF()),
                        dis.readUTF(),
                        dis.readUTF()));
            }
            orgList.add(org);
        }
        resume.addSection(SectionType.getSectionType(title), new OrganizationSection(orgList));
    }

}
