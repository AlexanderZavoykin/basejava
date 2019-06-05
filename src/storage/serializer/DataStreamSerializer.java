package storage.serializer;

import model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.LinkedList;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            // writing contacts
            dos.writeInt(resume.getContacts().size());
            resume.getContacts().entrySet().stream().forEach((entry) -> {
                try {
                    dos.writeUTF(entry.getKey().name());
                    dos.writeUTF(entry.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // writing sections
            dos.writeInt(resume.getSections().size());
            resume.getSections().entrySet().stream().forEach((entry) -> {
                        String sectionName = entry.getKey().name();
                        try {
                            doWriteSection(dos, sectionName, entry.getValue());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
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
            int sectionNum = dis.readInt();
            for (int i = 0; i < sectionNum; i++) {
                String sectionName = dis.readUTF();
                switch (sectionName) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        doReadTextSection(dis, resume, sectionName);
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATION":
                        doReadListSection(dis, resume, sectionName);
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        doReadOrgSection(dis, resume, sectionName);
                        break;
                }


            }


            return resume;
        }
    }

    private void doWriteSection(DataOutputStream dos, String sectionName, AbstractSection section) throws IOException {
        dos.writeUTF(sectionName);
        switch (sectionName) {
            case "PERSONAL":
            case "OBJECTIVE":
                dos.writeUTF(((TextSection) section).getBody());
                break;
            case "ACHIEVEMENT":
            case "QUALIFICATION":
                doWriteListSection(dos, (ListSection) section);
                break;
            case "EXPERIENCE":
            case "EDUCATION":
                doWriteOrgSection(dos, (OrganizationSection) section);
                break;
        }
    }


    public void doWriteListSection(DataOutputStream dos, ListSection listSection) throws IOException {
        dos.writeInt(listSection.getSkills().size());
        for (String skill : listSection.getSkills()) {
            dos.writeUTF(skill);
        }
    }

    public void doWriteOrgSection(DataOutputStream dos, OrganizationSection orgSection) throws IOException {
        dos.writeInt(orgSection.getOrganizations().size());
        for (Organization organization : orgSection.getOrganizations()) {
            dos.writeUTF(organization.getLink().getName());

            String url = organization.getLink().getUrl();
            writeStringOrSpace(dos, url);

            dos.writeInt(organization.getPeriods().size());
            for (Organization.Period period : organization.getPeriods()) {
                dos.writeUTF(period.getStartDate().toString());
                dos.writeUTF(period.getEndDate().toString());
                dos.writeUTF(period.getTitle());

                String description = period.getDescription();
                writeStringOrSpace(dos, description);
            }
        }
    }

    private static void writeStringOrSpace(DataOutputStream dos, String url) throws IOException {
        if (url != null) {
            dos.writeUTF(url);
        } else {
            dos.writeUTF("");
        }
    }


    private static void doReadTextSection(DataInputStream dis, Resume resume, String sectionName) throws IOException {
        String body = dis.readUTF();
        resume.addSection(SectionType.valueOf(sectionName), new TextSection(body));
    }

    private static void doReadListSection(DataInputStream dis, Resume resume, String sectionName) throws IOException {
        int size = dis.readInt();
        List<String> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            list.add(dis.readUTF());
        }
        resume.addSection(SectionType.valueOf(sectionName), new ListSection(list));
    }

    private static void doReadOrgSection(DataInputStream dis, Resume resume, String sectionName) throws IOException {
        int orgSize = dis.readInt();
        List<Organization> orgList = new LinkedList<>();
        for (int i = 0; i < orgSize; i++) {
            String name = dis.readUTF();
            String url = dis.readUTF();
            if (url.equals("")) {
                url = null;
            }
            Organization org = new Organization(new Link(name, url), new LinkedList<Organization.Period>());
            int perSize = dis.readInt();
            for (int j = 0; j < perSize; j++) {
                String startDate = dis.readUTF();
                String endDate = dis.readUTF();
                String title = dis.readUTF();
                String description = dis.readUTF();
                if (description.equals("")) {
                    description = null;
                }
                org.addPeriod(new Organization.Period(YearMonth.parse(startDate),
                        YearMonth.parse(endDate),
                        title,
                        description));
            }
            orgList.add(org);
        }
        resume.addSection(SectionType.valueOf(sectionName), new OrganizationSection(orgList));
    }

}
