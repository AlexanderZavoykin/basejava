package com.gmail.aazavoykin.util;

import com.gmail.aazavoykin.model.*;

import java.util.List;

public class HtmlWriter {

    public static String toHtml(SectionType sectionType, AbstractSection section) {
        String result = "";
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                result = ((TextSection) section).getBody();
                break;
            case ACHIEVEMENT:
            case QUALIFICATION:
                result += "<ul>";
                for (String s : ((ListSection) section).getSkills()) {
                    result += "<li>" + s + "</li>";
                }
                result += "</ul>";
                break;
            case EXPERIENCE:
            case EDUCATION:
                for (Organization o : ((OrganizationSection) section).getOrganizations()) {
                    result += toHtml(o);
                }
                break;
        }
        return result;
    }

    public static String createContactForms(Resume resume) {
        String result = "";
        for (ContactType contactType : ContactType.values()) {
            result += "<dl><dt>" + contactType.getTitle() + "</dt>" +
                    "<dd><input type=\"text\" name=\"${" + contactType.name() + "}\" ";
            String value = resume.getContact(contactType);
            if (value != null) {
                result += "value=\"" + value + "\"";
            }
            result += "size=\"50\"/></dd>\n" + "</dl>";
        }
        return result;
    }

    public static String createSectionForms(Resume resume) {
        String result = "";
        for (SectionType sectionType : SectionType.values()) {
            result += "<dl><dt><h3>" + sectionType.getTitle() + ":</h3></dt><br>";
            AbstractSection aSection = resume.getSection(sectionType);
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    result += "<dd><textarea form = \"edit_form\" rows = \"4\" cols=\"150\" name=\"" + sectionType.name() + "\">";
                    if (aSection != null) {
                        String body = ((TextSection) aSection).getBody();
                        if (body != null) {
                            result += body;
                        }
                    }
                    result += "</textarea>" + "</dd>";
                    break;
                case ACHIEVEMENT:
                case QUALIFICATION:
                    result += "<dd><textarea form = \"edit_form\" rows = \"4\" cols=\"150\" name=\"" + sectionType.name() + "\">";
                    if (aSection != null) {
                        List<String> skills = ((ListSection) aSection).getSkills();
                        if (skills != null) {
                            for (String skill : skills) {
                                result += skill + "\n";
                            }
                        }
                    }
                    result += "</textarea>" + "</dd>";
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    // TODO creating block of forms for each organization
                    break;
            }
        }
        return result;
    }

    private static String toHtml(Organization.Period p) {
        String description = p.getDescription() == null ? "" : p.getDescription();
        return "<tr>" +
                "<td style=\"vertical-align: top\">" + p.getStartDate().format(Organization.Period.FORMATTER) + " - " +
                p.getEndDate().format(Organization.Period.FORMATTER) + "</td>" +
                "<td><b>" + p.getTitle() + "</b><br>" + description + "</td>" +
                "</tr>";
    }

    private static String toHtml(Organization o) {
        String result = "<table><tr>" +
                "<td colspan=\"2\"><h4>";
        String title = o.getLink().getUrl() == null ? o.getLink().getName() :
                "<a href=\"" + o.getLink().getUrl() + "\">" + o.getLink().getName() + "</a>";
        result += title;
        result += "</h4></td></tr>";
        for (Organization.Period p : o.getPeriods()) {
            result += toHtml(p);
        }
        result += "</table>";
        return result;
    }


}
