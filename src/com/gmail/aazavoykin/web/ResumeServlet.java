package com.gmail.aazavoykin.web;

import com.gmail.aazavoykin.Config;
import com.gmail.aazavoykin.exception.ResumeDoesNotExistStorageException;
import com.gmail.aazavoykin.model.Resume;
import com.gmail.aazavoykin.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    SqlStorage sqlStorage;

    @Override
    public void init() throws ServletException {
        sqlStorage = new SqlStorage(Config.getInstance().getDbUrl(),
                Config.getInstance().getDbUser(),
                Config.getInstance().getDbPassword());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        response.getWriter().println("<table border = 1>");
        for (Resume r : list) {
            response.getWriter().println("<tr>");
            response.getWriter().println("<td>"+r.getUuid()+"</td>");
            response.getWriter().println("<td>"+r.getFullName()+"</td>");
            response.getWriter().println("</tr>");
        }
        response.getWriter().println("</table>");
    }
}
