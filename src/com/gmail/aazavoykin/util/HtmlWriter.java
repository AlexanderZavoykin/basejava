package com.gmail.aazavoykin.util;

import com.gmail.aazavoykin.model.*;

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
                "<td colspan=\"2\"><h3>";
        String title = o.getLink().getUrl() == null ? o.getLink().getName() :
                "<a href=\"" + o.getLink().getUrl() + "\">" + o.getLink().getName() + "</a>";
        result += title;
        result += "</h3></td></tr>";
        for (Organization.Period p : o.getPeriods()) {
            result += toHtml(p);
        }
        result += "</table>";
        return result;
    }


}
