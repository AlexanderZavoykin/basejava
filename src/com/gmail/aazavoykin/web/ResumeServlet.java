package com.gmail.aazavoykin.web;

import com.gmail.aazavoykin.Config;
import com.gmail.aazavoykin.exception.ResumeDoesNotExistStorageException;
import com.gmail.aazavoykin.model.Resume;
import com.gmail.aazavoykin.storage.SqlStorage;

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        if (uuid != null) {
            try {
                Resume resume = sqlStorage.get(uuid);
                response.getWriter().write(resume.getUuid() + " " + resume.getFullName());
            } catch (ResumeDoesNotExistStorageException e) {
                response.getWriter().write("Resume with uuid " + uuid + " does not exist");
            }
        } else {
            drawTable(response);
        }
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
