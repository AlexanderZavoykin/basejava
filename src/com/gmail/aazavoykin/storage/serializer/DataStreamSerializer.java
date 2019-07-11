package com.gmail.aazavoykin.storage.serializer;

import com.gmail.aazavoykin.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {

    interface DataWriter<T> {
        void write(T t) throws IOException;
    }

    interface DataHandler {
        void handle() throws IOException;
    }

    interface DataReader {
        void read() throws IOException;
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            // writing contacts
            doWriteCollection(dos, resume.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            // writing sections
            doWriteCollection(dos, resume.getSections().entrySet(),
                    entry -> {
                        String sectionName = entry.getKey().name();
                        dos.writeUTF(sectionName);
                        switch (sectionName) {
                            case "PERSONAL":
                            case "OBJECTIVE":
                                dos.writeUTF(((TextSection) entry.getValue()).getBody());
                                break;
                            case "ACHIEVEMENT":
                            case "QUALIFICATION":
                                doWriteCollection(dos, ((ListSection) entry.getValue()).getSkills(),
                                        dos::writeUTF);
                                break;
                            case "EXPERIENCE":
                            case "EDUCATION":
                                doWriteCollection(dos, ((OrganizationSection) entry.getValue()).getOrganizations(),
                                        organization -> {
                                            dos.writeUTF(organization.getLink().getName());
                                            String url = organization.getLink().getUrl();
                                            writeEmptyString(dos, url);
                                            doWriteCollection(dos, organization.getPeriods(),
                                                    period -> {
                                                        dos.writeUTF(period.getStartDate().toString());
                                                        dos.writeUTF(period.getEndDate().toString());
                                                        dos.writeUTF(period.getTitle());

                                                        String description = period.getDescription();
                                                        writeEmptyString(dos, description);
                                                    });
                                        });
                                break;
                        }
                    });
        }
    }
    
    private <T> void doWriteCollection(DataOutputStream dos, Collection<T> collection, DataWriter<T> dataWriter) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            dataWriter.write(t);
        }
    }

    private static void writeEmptyString(DataOutputStream dos, String s) throws IOException {
        if (s != null) {
            dos.writeUTF(s);
        } else {
            dos.writeUTF("");
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            // reading contacts
            walk(dis, () -> {
                String contactType = dis.readUTF();
                String contactBody = dis.readUTF();
                resume.addContact(ContactType.valueOf(contactType), contactBody);
            });

            //reading sections
            // start of walking through sections
            walk(dis, () -> {
                String sectionName = dis.readUTF();
                switch (sectionName) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        String body = dis.readUTF();
                        resume.addSection(SectionType.valueOf(sectionName), new TextSection(body));
                        break;

                    case "ACHIEVEMENT":
                    case "QUALIFICATION":
                        List<String> skillList = new LinkedList<>();

                        // start of walking through skills
                        walk(dis, () -> {
                            String skill = dis.readUTF();
                            skillList.add(skill);
                        }); // end of walking through skills
                        resume.addSection(SectionType.valueOf(sectionName), new ListSection(skillList));
                        break;
                    case "EXPERIENCE":
                    case "EDUCATION":
                        List<Organization> orgList = new LinkedList<>();

                        // start of walking through organizations
                        walk(dis, () -> {
                            String orgName = dis.readUTF();
                            String orgUrl = dis.readUTF();
                            if (orgUrl.equals("")) {
                                orgUrl = null;
                            }

                            List<Organization.Period> periods = new LinkedList<>();

                            // start of walking through periods
                            walk(dis, () -> {
                                String startDate = dis.readUTF();
                                String endDate = dis.readUTF();
                                String title = dis.readUTF();
                                String description = dis.readUTF();

                                //System.out.println(startDate);
                                //System.out.println(endDate);
                                //System.out.println(title);
                                //System.out.println(description);

                                if (description.equals("")) {
                                    description = null;
                                }
                                periods.add(new Organization.Period(YearMonth.parse(startDate),
                                        YearMonth.parse(endDate),
                                        title,
                                        description));
                            }); //end of walking through periods

                            Organization organization = new Organization((new Link(orgName, orgUrl)),
                                    periods);
                            orgList.add(organization);

                        });  //end of walking through organizations

                        resume.addSection(SectionType.valueOf(sectionName), new OrganizationSection(orgList));
                        break;
                }  // end of switch statement

            });  //end of walking sections cycle
            return resume;
        }
    }

    private void walk(DataInputStream dis, DataHandler dataHandler) throws IOException {
        int size = dis.readInt();
        //System.out.println(size);
        for (int i = 0; i < size; i++) {
            dataHandler.handle();
        }
    }


}


