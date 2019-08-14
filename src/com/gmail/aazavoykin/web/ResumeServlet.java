package com.gmail.aazavoykin.web;

import com.gmail.aazavoykin.Config;
import com.gmail.aazavoykin.model.ContactType;
import com.gmail.aazavoykin.model.Resume;
import com.gmail.aazavoykin.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = sqlStorage.get(uuid);
        r.setFullName(fullName);
        for (ContactType ct : ContactType.values()) {
            String value = request.getParameter(ct.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(ct, value);
            } else {
                r.getContacts().remove(ct);
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
        Resume r = null;
        switch (action) {
            case "create":
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

    private void drawTable(HttpServletResponse response) throws IOException {
        List<Resume> list = sqlStorage.getAllSorted();
        PrintWriter writer = response.getWriter();
        writer.println("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Перечень резюме</title>\n" +
                "</head>\n" +
                "<body>");

        writer.println("<table border = 1>");
        writer.println("<tr>\n" +
                "<td><b>Имя</b></td>");
        for (Resume r : list) {
            writer.println("<tr>");
            writer.println("<td><a href=\"resume?uuid=" + r.getUuid() + "\">" + r.getFullName() + "</a></td>");
            //response.getWriter().println("<td>" + r.getFullName() + "</td>");
            writer.println("</tr>");
        }
        writer.println("</table>");

        writer.println("</body>\n" +
                "</html>");
    }

}
