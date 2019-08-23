<%@ page import="com.gmail.aazavoykin.util.HtmlWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.gmail.aazavoykin.model.Resume" scope="request"/>
    <title>
        <%=
            request.getParameter("action").equals("edit") ? "Резюме " + resume.getFullName() : "Новое резюме"
        %>
    </title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form id="edit_form" action="resume" method="post" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="action" value="<%=request.getParameter("action").equals("edit") ? "edit" : "add"%>">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" value="${resume.fullName}" size="40"/></dd>
        </dl>

        <%-->Contacts<--%>
        <h3>Контакты: </h3>
        <%=HtmlWriter.createContactForms(resume)%>
        <hr>

        <%-->Sections<--%>
        <%=HtmlWriter.createSectionForms(resume)%>
        <hr>

        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>