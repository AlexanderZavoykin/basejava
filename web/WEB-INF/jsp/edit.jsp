<%@ page import="com.gmail.aazavoykin.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.gmail.aazavoykin.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form action="resume" method="post" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" value="${resume.fullName}" size="40"></dd>
        </dl>
        <h3>Контакты: </h3>
        <c:forEach items="<%=ContactType.values()%>" var="type">
            <jsp:useBean id="type" type="com.gmail.aazavoykin.model.ContactType"/>
            <dl>
                <dt><%=type.getTitle()%></dt>
                <dd><input type="text" name="${type.name()}" value="${resume.getContact(type)}" size="50"></dd>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>

    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>