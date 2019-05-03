package model;

import java.time.YearMonth;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

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
                new QualitySection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise " +
                        "технологиям"));

        //fill PERSONAL section
        resume.getSections().put(SectionType.PERSONAL,
                new QualitySection("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                        "Пурист кода и архитектуры."));

        //fill ACHIEVEMENT section
        SkillSection achievementSection = new SkillSection();
        achievementSection.addSkill("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. " +
                "Более 1000 выпускников.");
        achievementSection.addSkill("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievementSection.addSkill("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера.");
        achievementSection.addSkill("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, " +
                "Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievementSection.addSkill("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии " +
                "через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы " +
                "по JMX (Jython/ Django).");
        achievementSection.addSkill("Реализация протоколов по приему платежей всех основных платежных системы России " +
                "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        resume.getSections().put(SectionType.ACHIEVEMENT,
                achievementSection);

        //fill QUALIFICATION section
        SkillSection qualificationSection = new SkillSection();
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

        //fill EXPERIENCE section
        OrganizationSection experienceSection = new OrganizationSection();
        Organization experience_1 = new Organization();
        experience_1.setName("Java Online Projects");
        experience_1.setStartDate(YearMonth.of(2013, 10));
        experience_1.setEndDate(YearMonth.now()); // TODO create method to convert to String "now"
        experience_1.setFunction("Автор проекта.");
        experience_1.setDescription("Создание, организация и проведение Java онлайн проектов и стажировок.");
        experienceSection.addOrganization(experience_1);

        Organization experience_2 = new Organization();
        experience_2.setName("Wrike");
        experience_2.setStartDate(YearMonth.of(2014, 10));
        experience_2.setEndDate(YearMonth.of(2016, 1));
        experience_2.setFunction("Старший разработчик (backend)");
        experience_2.setDescription("Проектирование и разработка онлайн платформы управления проектами Wrike " +
                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, " +
                "авторизация по OAuth1, OAuth2, JWT SSO.");
        experienceSection.addOrganization(experience_2);

        Organization experience_3 = new Organization();
        experience_3.setName("RIT Center");
        experience_3.setStartDate(YearMonth.of(2012, 4));
        experience_3.setEndDate(YearMonth.of(2014, 10));
        experience_3.setFunction("Java архитектор");
        experience_3.setDescription("Организация процесса разработки системы ERP для разных окружений: релизная " +
                "политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. " +
                "Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения " +
                "(почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера " +
                "документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, " +
                "Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, " +
                "PL/Python");
        experienceSection.addOrganization(experience_3);

        Organization experience_4 = new Organization();
        experience_4.setName("Luxoft (Deutsche Bank)");
        experience_4.setStartDate(YearMonth.of(2010, 12));
        experience_4.setEndDate(YearMonth.of(2012, 4));
        experience_4.setFunction("Ведущий программист");
        experience_4.setDescription("Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, " +
                "SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация " +
                "RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического " +
                "трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        experienceSection.addOrganization(experience_4);

        Organization experience_5 = new Organization();
        experience_5.setName("Yota");
        experience_5.setStartDate(YearMonth.of(2008, 6));
        experience_5.setEndDate(YearMonth.of(2010, 12));
        experience_5.setFunction("Ведущий специалист");
        experience_5.setDescription("Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                "(GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация " +
                "администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, " +
                "Django, ExtJS)");
        experienceSection.addOrganization(experience_5);

        Organization experience_6 = new Organization();
        experience_6.setName("Enkata");
        experience_6.setStartDate(YearMonth.of(2007, 3));
        experience_6.setEndDate(YearMonth.of(2008, 6));
        experience_6.setFunction("Разработчик ПО");
        experience_6.setDescription("Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, " +
                "Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
        experienceSection.addOrganization(experience_6);

        Organization experience_7 = new Organization();
        experience_7.setName("Siemens AG");
        experience_7.setStartDate(YearMonth.of(2005, 1));
        experience_7.setEndDate(YearMonth.of(2007, 2));
        experience_7.setFunction("Разработчик ПО");
        experience_7.setDescription("Разработка информационной модели, проектирование интерфейсов, реализация и " +
                "отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        experienceSection.addOrganization(experience_7);

        Organization experience_8 = new Organization();
        experience_8.setName("Enkata");
        experience_8.setStartDate(YearMonth.of(1997, 9));
        experience_8.setEndDate(YearMonth.of(2005, 1));
        experience_8.setFunction("Инженер по аппаратному и программному тестированию");
        experience_8.setDescription("Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 " +
                "S12 (CHILL, ASM).");
        experienceSection.addOrganization(experience_8);

        //fill EDUCATION section
        OrganizationSection educationSection = new OrganizationSection();
        Organization education_1 = new Organization();
        education_1.setName("Coursera");
        education_1.setStartDate(YearMonth.of(2013, 3));
        education_1.setEndDate(YearMonth.of(2013, 5));
        education_1.setFunction("\"Functional Programming Principles in Scala\" by Martin Odersky");
        educationSection.addOrganization(education_1);

        Organization education_2 = new Organization();
        education_2.setName("Luxoft");
        education_2.setStartDate(YearMonth.of(2011, 3));
        education_2.setEndDate(YearMonth.of(2011, 4));
        education_2.setFunction("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");
        educationSection.addOrganization(education_2);

        Organization education_3 = new Organization();
        education_3.setName("Siemens AG");
        education_3.setStartDate(YearMonth.of(2005, 1));
        education_3.setEndDate(YearMonth.of(2005, 4));
        education_3.setFunction("3 месяца обучения мобильным IN сетям (Берлин)");
        educationSection.addOrganization(education_3);

        Organization education_4 = new Organization();
        education_4.setName("Alcatel");
        education_4.setStartDate(YearMonth.of(1997, 9));
        education_4.setEndDate(YearMonth.of(1998, 3));
        education_4.setFunction("6 месяцев обучения цифровым телефонным сетям (Москва)");
        educationSection.addOrganization(education_4);

        Organization education_5 = new Organization();
        education_5.setName("Санкт-Петербургский национальный исследовательский университет информационных " +
                "технологий, механики и оптики");
        education_5.setStartDate(YearMonth.of(1993, 9));
        education_5.setEndDate(YearMonth.of(1996, 7));
        education_5.setFunction("Аспирантура (программист С, С++)");
        educationSection.addOrganization(education_5);

        Organization education_6 = new Organization();
        education_6.setName("Санкт-Петербургский национальный исследовательский университет информационных " +
                "технологий, механики и оптики");
        education_6.setStartDate(YearMonth.of(1987, 9));
        education_6.setEndDate(YearMonth.of(1993, 7));
        education_6.setFunction("Инженер (программист Fortran, C)");
        educationSection.addOrganization(education_6);

        Organization education_7 = new Organization();
        education_7.setName("Заочная физико-техническая школа при МФТИ");
        education_7.setStartDate(YearMonth.of(1984, 9));
        education_7.setEndDate(YearMonth.of(1987, 6));
        education_7.setFunction("Закончил с отличием");
        educationSection.addOrganization(education_7);

    }

}
