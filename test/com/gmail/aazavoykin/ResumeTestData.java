package com.gmail.aazavoykin;

import com.gmail.aazavoykin.model.*;

import java.time.YearMonth;

public class ResumeTestData {

    static final String UUID_1 = "uuid_1";
    static final String UUID_2 = "uuid_2";
    static final String UUID_3 = "uuid_3";
    static final String UUID_CHECK = "uuid_check";
    public static final Resume RESUME_1;
    static final Resume RESUME_2;
    static final Resume RESUME_3;
    static final Resume RESUME_CHECK;

    static {
        RESUME_1 = new Resume(UUID_1, "Mark Twain");
        RESUME_2 = new Resume(UUID_2, "Jorge Amado");
        RESUME_3 = new Resume(UUID_3, "Ernest Hemingway");
        RESUME_CHECK = new Resume(UUID_CHECK, "Pancho Villa");

        RESUME_1.addContact(ContactType.EMAIL, "resume_1@gmail.com");
        RESUME_1.addContact(ContactType.SKYPE, "resume_1_skype");
        RESUME_1.addSection(SectionType.PERSONAL, new TextSection("Personal"));
        RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("Objective"));
        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListSection("Achievement_1", "Achievement_2", "Achievement_3"));
        RESUME_1.addSection(SectionType.QUALIFICATION, new ListSection("Qualification_1", "Qualification_2"));
        RESUME_1.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization(new Link("CompanyName", "CompanyURL"),
                new Organization.Period(YearMonth.of(1990, 1), YearMonth.of(1992, 6),
                        "Title", "Description"),
                new Organization.Period(YearMonth.of(1990, 1), YearMonth.of(1992, 6),
                        "Title", "Description"))));
        RESUME_1.addSection(SectionType.EDUCATION, new OrganizationSection(
                new Organization(new Link("University", null),
                        new Organization.Period(YearMonth.of(1982, 1), YearMonth.of(1985, 1),
                                "Title", null),
                        new Organization.Period(YearMonth.of(1985, 2), YearMonth.of(1989, 12),
                                "Title", "Description"))));

        RESUME_2.addContact(ContactType.EMAIL, "resume_2@gmail.com");
        RESUME_2.addContact(ContactType.SKYPE, "resume_2_skype");
        RESUME_2.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization(new Link("Company", null),
                        new Organization.Period(YearMonth.of(2000, 1), YearMonth.of(2002, 1),
                                "Title", null),
                        new Organization.Period(YearMonth.of(2005, 1), YearMonth.of(2008, 1),
                                "Title", "Description"))));
    }


    /*private static Resume createTestResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        // fill contacts
        resume.getContacts().put(ContactType.PHONE, "+7(921) 855-0482");
        resume.getContacts().put(ContactType.SKYPE, "grigory.kislin");
        resume.getContacts().put(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.getContacts().put(ContactType.LINKEDIN_PROFILE, "https://www.linkedin.com/in/gkislin");
        resume.getContacts().put(ContactType.GITHUB_PROFILE, "https://github.com/gkislin");
        resume.getContacts().put(ContactType.STACKOVERFLOW_PROFILE, "https://stackoverflow.com/users/548473/gkislin");
        resume.getContacts().put(ContactType.HOME_PAGE, "http://gkislin.ru/");

        //fill OBJECTIVE section
        resume.getSections().put(SectionType.OBJECTIVE,
                new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise " +
                        "технологиям"));

        //fill PERSONAL section
        resume.getSections().put(SectionType.PERSONAL,
                new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                        "Пурист кода и архитектуры."));

        //fill ACHIEVEMENT section
        ListSection achievementSection = new ListSection();
        achievementSection.addSkill("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 1000 выпускников.");
        achievementSection.addSkill("Реализация двухфакторной аутентификации для онлайн платформы управления " +
                "проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievementSection.addSkill("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера.");
        achievementSection.addSkill("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, " +
                "Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievementSection.addSkill("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных " +
                "сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации " +
                "о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и " +
                "мониторинга системы по JMX (Jython/ Django).");
        achievementSection.addSkill("Реализация протоколов по приему платежей всех основных платежных системы России " +
                "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        resume.getSections().put(SectionType.ACHIEVEMENT,
                achievementSection);

        //fill QUALIFICATION section
        ListSection qualificationSection = new ListSection();
        qualificationSection.addSkill("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationSection.addSkill("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationSection.addSkill("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualificationSection.addSkill("MySQL, SQLite, MS SQL, HSQLDB");
        qualificationSection.addSkill("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        qualificationSection.addSkill("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        qualificationSection.addSkill("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, " +
                "Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, " +
                "GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, " +
                "Selenium (htmlelements).");
        qualificationSection.addSkill("Python: Django.");
        qualificationSection.addSkill("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualificationSection.addSkill("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualificationSection.addSkill("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, " +
                "StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, " +
                "BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualificationSection.addSkill("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        qualificationSection.addSkill("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, " +
                "Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        qualificationSection.addSkill("Отличное знание и опыт применения концепций ООП, SOA, шаблонов");
        qualificationSection.addSkill("проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualificationSection.addSkill("Родной русский, английский \"upper intermediate\"");
        resume.getSections().put(SectionType.QUALIFICATION, qualificationSection);

        //fill EXPERIENCE section
        OrganizationSection experienceSection = new OrganizationSection();
        Organization experience_1 = new Organization(new Link("Java Online Projects", "http://javaops.ru/"),
                new LinkedList<>());
        experience_1.addPeriod(new Organization.Period(YearMonth.of(2013, 10),
                DateUtil.NOW,
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок."));
        experienceSection.addOrganization(experience_1);

        Organization experience_2 = new Organization(new Link("Wrike", "https://www.wrike.com/"),
                new LinkedList<>());
        experience_2.addPeriod(new Organization.Period(YearMonth.of(2014, 10),
                YearMonth.of(2016, 1),
                "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы " +
                "управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)." +
                " Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        experienceSection.addOrganization(experience_2);

        Organization experience_3 = new Organization(new Link("RIT Center"),
                new LinkedList<>());
        experience_1.addPeriod(new Organization.Period(YearMonth.of(2012, 4),
                YearMonth.of(2014, 10),
                "Java архитектор", "Организация процесса " +
                "разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI " +
                "(Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), " +
                "AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: " +
                "CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                "Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. " +
                "Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, " +
                "Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, " +
                "PL/Python"));
        experienceSection.addOrganization(experience_3);

        Organization experience_4 = new Organization(new Link("Luxoft (Deutsche Bank)",
                "http://www.luxoft.ru/"),
                new LinkedList<>());
        experience_4.addPeriod(new Organization.Period(YearMonth.of(2010, 12),
                YearMonth.of(2012, 4),
                "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, " +
                "Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. " +
                "Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области " +
                "алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        experienceSection.addOrganization(experience_4);

        Organization experience_5 = new Organization(new Link("Yota", "https://www.yota.ru/"),
                new LinkedList<>());
        experience_5.addPeriod(new Organization.Period(YearMonth.of(2008, 6),
                YearMonth.of(2010, 12),
                "Ведущий специалист", "Дизайн и имплементация " +
                "Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, " +
                "Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга " +
                "фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
        experienceSection.addOrganization(experience_5);

        Organization experience_6 = new Organization(new Link("Enkata", "http://enkata.com/"),
                new LinkedList<>());
        experience_6.addPeriod(new Organization.Period(YearMonth.of(2007, 3),
                YearMonth.of(2008, 6),
                "Разработчик ПО", "Реализация клиентской " +
                "(Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения " +
                "(OLAP, Data mining)."));
        experienceSection.addOrganization(experience_6);

        Organization experience_7 = new Organization(new Link("Siemens AG",
                "https://new.siemens.com/ru/ru.html"),
                new LinkedList<>());
        experience_7.addPeriod(new Organization.Period(YearMonth.of(2005, 1),
                YearMonth.of(2007, 2),
                "Разработчик ПО", "Разработка информационной " +
                "модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens " +
                "@vantage (Java, Unix)."));
        experienceSection.addOrganization(experience_7);

        Organization experience_8 = new Organization(new Link("Alcatel", "http://www.alcatel.ru/"),
                new LinkedList<>());
        experience_8.addPeriod(new Organization.Period(YearMonth.of(1997, 9),
                YearMonth.of(2005, 1),
                "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 " +
                        "S12 (CHILL, ASM)."));
        experienceSection.addOrganization(experience_8);

        resume.getSections().put(SectionType.EXPERIENCE, experienceSection);

        //fill EDUCATION section
        OrganizationSection educationSection = new OrganizationSection();

        Organization education_1 = new Organization(new Link("Coursera",
                "https://www.coursera.org/learn/progfun1"),
                new LinkedList<>());
        education_1.addPeriod(new Organization.Period(YearMonth.of(2013, 3),
                YearMonth.of(2013, 5), "\"Functional Programming Principles in Scala\" by " +
                "Martin Odersky", ""));
        educationSection.addOrganization(education_1);

        Organization education_2 = new Organization(new Link("Luxoft",
                "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy__analiz_is_kontseptualnoe_" +
                        "modelirovanie_na_uml_dlya_sistemnyh_analitikov_.html"),
                new LinkedList<>());
        education_2.addPeriod(new Organization.Period(YearMonth.of(2011, 3),
                YearMonth.of(2011, 4),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", ""));
        educationSection.addOrganization(education_2);

        Organization education_3 = new Organization(new Link("Siemens AG", "https://new.siemens.com/ru/ru.html"),
                new LinkedList<>());
        education_3.addPeriod(new Organization.Period(YearMonth.of(2005, 1),
                YearMonth.of(2005, 4),
                "3 месяца обучения мобильным IN сетям (Берлин)", ""));
        educationSection.addOrganization(education_3);

        Organization education_4 = new Organization(new Link("Alcatel", "http://www.alcatel.ru/"),
                new LinkedList<>());
        education_4.addPeriod(new Organization.Period(YearMonth.of(1997, 9),
                YearMonth.of(1998, 3),
                "6 месяцев обучения цифровым телефонным сетям (Москва)",
                ""));
        educationSection.addOrganization(education_4);

        Organization education_5 = new Organization(new Link("Санкт-Петербургский национальный исследовательский " +
                "университет информационных технологий, механики и оптики", "http://www.ifmo.ru/ru/"),
                new LinkedList<>());
        education_5.addPeriod(new Organization.Period(YearMonth.of(1993, 9),
                YearMonth.of(1996, 7),
                "Аспирантура (программист С, С++)", ""));
        education_5.addPeriod(new Organization.Period(YearMonth.of(1987, 9),
                YearMonth.of(1993, 7),
                "Инженер (программист Fortran, C)", ""));
        educationSection.addOrganization(education_5);

        Organization education_6 = new Organization(new Link("Заочная физико-техническая школа при МФТИ",
                "http://www.school.mipt.ru/"),
                new LinkedList<>());
        education_6.addPeriod(new Organization.Period(YearMonth.of(1984, 9),
                YearMonth.of(1987, 6),
                "Закончил с отличием",
                ""));
        educationSection.addOrganization(education_6);

        resume.getSections().put(SectionType.EDUCATION, educationSection);

        return resume;
    }

    private static void printTestResume(Resume resume) {
        System.out.println(resume.getFullName());

        for (ContactType ct : ContactType.values()) {
            System.out.print(ct.getTitle() + ": ");
            System.out.println(resume.getContacts().get(ct));
        }

        for (SectionType st : SectionType.values()) {
            System.out.println(st.getTitle());
            System.out.print(resume.getSections().get(st).toString());
        }
    }

    public static void main(String[] args) {
        Resume resume = createTestResume("uuid", "Григорий Кислин");
        printTestResume(resume);
    }*/

}
