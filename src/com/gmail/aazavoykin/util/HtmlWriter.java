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
                for (String s : ((ListSection) section).getSkills()) {
                    result += "<li>" + s + "</li>";
                }
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

    public static String createSectionForm(SectionType sectionType, AbstractSection section) {
        String result = "<dl><dt><h3>" + sectionType.getTitle() + ":</h3></dt><br>";
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                result += "<dd><textarea form = \"edit_form\" rows = \"4\" cols=\"150\" type=\"text\" name=\"" + sectionType.name() + "\">" +
                        ((TextSection) section).getBody() +
                        "</textarea>" + "</dd>";
                break;
            case ACHIEVEMENT:
            case QUALIFICATION:
                List<String> skills = ((ListSection) section).getSkills();
                int inputNum = Math.max(skills.size(), 5);
                result += "<dd>";
                for (int i = 0; i < inputNum; i++) {
                    String skill = i < skills.size() ? skills.get(i) : "";
                    result += "<li><input type=\"text\" name=\"" + sectionType.name() + "\" value=\"" + skill +
                            "\" size=\"150\">";
                }
                result += "</dd></dl>";
                break;
            case EXPERIENCE:
            case EDUCATION:
                // TODO creating block of forms for each organization
                break;
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
