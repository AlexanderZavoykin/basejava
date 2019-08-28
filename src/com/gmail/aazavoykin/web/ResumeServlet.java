package com.gmail.aazavoykin.web;

import com.gmail.aazavoykin.Config;
import com.gmail.aazavoykin.model.*;
import com.gmail.aazavoykin.storage.SqlStorage;
import com.gmail.aazavoykin.util.DateUtil;
import com.gmail.aazavoykin.util.HtmlUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private SqlStorage sqlStorage;

    @Override
    public void init() {
        sqlStorage = new SqlStorage(Config.getInstance().getDbUrl(),
                Config.getInstance().getDbUser(),
                Config.getInstance().getDbPassword());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String fullName = request.getParameter("fullName");
        Resume r;
        if ("add".equals(action)) {
            r = new Resume(fullName);
        } else {
            String uuid = request.getParameter("uuid");
            r = sqlStorage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType ct : ContactType.values()) {
            String value = request.getParameter(ct.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(ct, value);
            } else {
                r.getContacts().remove(ct);
            }
        }
        for (SectionType st : SectionType.values()) {
            String value = request.getParameter(st.name());
            switch (st) {
                case PERSONAL:
                case OBJECTIVE:
                    if (HtmlUtil.notEmpty(value)) {
                        r.addSection(st, new TextSection(value));
                    } else {
                        r.getSections().remove(st);
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATION:
                    if (HtmlUtil.notEmpty(value)) {
                        List<String> skills = Arrays.asList(value.split("\n"));
                        r.addSection(st, new ListSection(skills));
                    } else {
                        r.getSections().remove(st);
                    }
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    // update existing organizations info
                    int i = 1;
                    OrganizationSection os = (OrganizationSection) r.getSection(st);
                    if (os != null) {
                        List<Organization> orgs = os.getOrganizations();
                        if (orgs != null) {
                            for (Organization o : orgs) {
                                String orgPrefix = st.name() + "_org" + i;
                                String name = request.getParameter(orgPrefix + "_name");
                                String urlValue = request.getParameter(orgPrefix + "_url");
                                if (HtmlUtil.notEmpty(name)) {
                                    o.getLink().setName(name);
                                }
                                if (HtmlUtil.notEmpty(name)) {
                                    o.getLink().setUrl(urlValue);
                                }
                                int k = 1;
                                List<Organization.Period> periods = o.getPeriods();
                                if (periods != null) {
                                    for (Organization.Period p : periods) {
                                        // update existing periods
                                        String periodPrefix = orgPrefix + "_per" + k;
                                        String title = request.getParameter(periodPrefix + "_title");
                                        String startDate = request.getParameter(periodPrefix + "_start");
                                        String endDate = request.getParameter(periodPrefix + "_end");
                                        String description = request.getParameter(periodPrefix + "_descr");
                                        if (HtmlUtil.notEmpty(title)) {
                                            p.setTitle(title);
                                        }
                                        if (HtmlUtil.notEmpty(startDate)) {
                                            p.setStartDate(DateUtil.toDate(startDate));
                                        }
                                        if (HtmlUtil.notEmpty(endDate)) {
                                            p.setEndDate(DateUtil.toDate(endDate));
                                        }
                                        if (HtmlUtil.notEmpty(description)) {
                                            p.setDescription(description);
                                        }

                                        // delete period if inputs are empty
                                        if (!HtmlUtil.notEmpty(title) && !HtmlUtil.notEmpty(startDate) &&
                                        !HtmlUtil.notEmpty(endDate) && !HtmlUtil.notEmpty(description)) {
                                            periods.remove(p);
                                        }
                                        k++;
                                    }
                                }

                                // save new period:
                                String newStartDate = request.getParameter(orgPrefix + "_perNEW_start");
                                String newEndDate = request.getParameter(orgPrefix + "_perNEW_end");
                                String newTitle = request.getParameter(orgPrefix + "_perNEW_title");

                                if (HtmlUtil.notEmpties(newStartDate, newEndDate, newTitle)) {
                                    Organization.Period period = new Organization.Period();
                                    period.setStartDate(DateUtil.toDate(newStartDate));
                                    period.setEndDate(DateUtil.toDate(newEndDate));
                                    period.setTitle(newTitle);
                                    String newDescription = request.getParameter(orgPrefix + "_perNEW_descr");
                                    if (HtmlUtil.notEmpty(newDescription)) {
                                        period.setDescription(newDescription);
                                    }
                                    o.addPeriod(period);
                                }
                                i++;
                            }
                        }
                    }

                    // save new organization
                    String newName = request.getParameter(st.name() + "_orgNEW_name");
                    String newStartDate = request.getParameter(st.name() + "_orgNEW_start");
                    String newEndDate = request.getParameter(st.name() + "_orgNEW_end");
                    String newTitle = request.getParameter(st.name() + "_orgNEW_title");

                    if (HtmlUtil.notEmpties(newName, newStartDate, newEndDate, newTitle)) {
                        String newUrlPar = st.name() + "_orgNEW_url";
                        String newDescrPar = st.name() +  "_orgNEW_descr";
                        String newUrl = request.getParameter(newUrlPar);
                        String newDescription = request.getParameter(newDescrPar);
                        Organization.Period period = new Organization.Period();
                        period.setStartDate(DateUtil.toDate(newStartDate));
                        period.setEndDate(DateUtil.toDate(newEndDate));
                        period.setTitle(newTitle);
                        if (HtmlUtil.notEmpty(newDescription)) {
                            period.setDescription(newDescription);
                        }
                        Link link = new Link(newName);
                        if (HtmlUtil.notEmpty(newUrl)) {
                            link.setUrl(newUrl);
                        }
                        Organization organization = new Organization(link, period);
                        if (r.getSection(st) == null) {
                            r.addSection(st, new OrganizationSection(organization));
                        } else {
                            ((OrganizationSection) r.getSection(st)).addOrganization(organization);
                        }

                    }
                    break;
            }
        }
        if ("add".equals(action)) {
            sqlStorage.save(r);
        } else {
            sqlStorage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", sqlStorage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "add":
                r = new Resume("");
                break;
            case "view":
            case "edit":
                r = sqlStorage.get(uuid);
                break;
            case "delete":
                sqlStorage.delete(uuid);
                response.sendRedirect("resume");
                return;
            default:
                throw new IllegalStateException("Action " + action + " is not legal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);

    }


}
