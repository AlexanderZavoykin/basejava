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
import java.util.ArrayList;
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
                    List<Organization> orgList = new ArrayList<>();
                    String[] values = request.getParameterValues(st.name() + "_name");
                    if (values != null) {
                        for (int i = 0; i < values.length; i++) {
                            /*
                             ** if organization`s name is not empty
                             ** read the rest of info came from inputs
                             */
                            if (HtmlUtil.notEmpty(values[i])) {
                                List<Organization.Period> periods = new ArrayList<>();
                                String prefix = st.name() + "_" + (i + 1);
                                String[] startDates = request.getParameterValues(prefix + "_start");
                                String[] endDates = request.getParameterValues(prefix + "_end");
                                String[] titles = request.getParameterValues(prefix + "_title");
                                String[] descriptions = request.getParameterValues(prefix + "_descr");
                                for (int k = 0; k < titles.length; k++) {
                                    if (HtmlUtil.notEmpties(titles[k], startDates[k], endDates[k])) {
                                        Organization.Period period = new Organization.Period(DateUtil.toDate(startDates[k]),
                                                DateUtil.toDate(endDates[k]), titles[k]);
                                        if (HtmlUtil.notEmpty(descriptions[k])) {
                                            period.setDescription(descriptions[k]);
                                        }
                                        periods.add(period);
                                    }
                                }
                                /*
                                 ** add new period to organization if there is info from inputs
                                 */
                                String newStartDate = request.getParameter(prefix + "_NEW_start");
                                String newEndDate = request.getParameter(prefix + "_NEW_end");
                                String newTitle = request.getParameter(prefix + "_NEW_title");
                                String newDescription = request.getParameter(prefix + "_NEW_descr");
                                if (HtmlUtil.notEmpties(newStartDate, newEndDate, newTitle)) {
                                    Organization.Period period = new Organization.Period(DateUtil.toDate(newStartDate),
                                            DateUtil.toDate(newEndDate), newTitle);
                                    if (HtmlUtil.notEmpty(newDescription)) {
                                        period.setDescription(newDescription);
                                    }
                                    periods.add(period);
                                }
                                /*
                                 ** create new Organization and add it to orgList
                                 */
                                String[] urls = request.getParameterValues(prefix + "_url");
                                Link link = new Link(values[i]);
                                if (HtmlUtil.notEmpty(urls[i])) {
                                    link.setUrl(urls[i]);
                                }
                                orgList.add(new Organization(link, periods));
                            }
                        }
                    }

                    /*
                     ** add new Organization to orgList if there is info from inputs
                     */
                    String newName = request.getParameter(st.name() + "_NEW_name");
                    String newUrl = request.getParameter(st.name() + "_NEW_url");
                    String newStartDate = request.getParameter(st.name() + "_NEW_start");
                    String newEndDate = request.getParameter(st.name() + "_NEW_end");
                    String newTitle = request.getParameter(st.name() + "_NEW_title");
                    String newDescription = request.getParameter(st.name() + "_NEW_descr");
                    if (HtmlUtil.notEmpty(newName)) {
                        Link link = new Link(newName);
                        if (HtmlUtil.notEmpty(newUrl)) {
                            link.setUrl(newUrl);
                        }
                        if (HtmlUtil.notEmpties(newStartDate, newEndDate, newTitle)) {
                            Organization.Period period = new Organization.Period(DateUtil.toDate(newStartDate),
                                    DateUtil.toDate(newEndDate), newTitle);
                            if (HtmlUtil.notEmpty(newDescription)) {
                                period.setDescription(newDescription);
                            }
                            orgList.add(new Organization(link, period));
                        }
                    }
                    /*
                     ** create new section and replace existing section in Resume r with the new one
                     */
                    r.addSection(st, new OrganizationSection(orgList));
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
