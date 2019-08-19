package com.gmail.aazavoykin.web;

import com.gmail.aazavoykin.Config;
import com.gmail.aazavoykin.model.*;
import com.gmail.aazavoykin.storage.SqlStorage;

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
            sqlStorage.save(r);
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
                    if (value != null && value.trim().length() != 0) {
                        r.addSection(st, new TextSection(value));
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATION:
                    if (value != null && value.trim().length() != 0) {
                        List<String> skills = Arrays.asList(value.split("\n"));
                        r.addSection(st, new ListSection(skills));
                    }
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    // TODO write OrganizationSection to resume
                    break;
            }
        }
        sqlStorage.update(r);
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
