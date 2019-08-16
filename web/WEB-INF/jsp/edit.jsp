<%@ page import="com.gmail.aazavoykin.model.ContactType" %>
<%@ page import="com.gmail.aazavoykin.model.SectionType" %>
<%@ page import="com.gmail.aazavoykin.util.HtmlWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.gmail.aazavoykin.model.Resume" scope="request"/>
    <title>
        <c:choose>
            <c:when test="${param.get(\"action\").equals(\"edit\")}">Резюме ${resume.fullName}}</c:when>
            <c:when test="${param.get(\"action\").equals(\"add\")}">Новое резюме</c:when>
        </c:choose>
    </title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form id="edit_form" action="resume" method="post" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" value="${resume.fullName}" size="40"/></dd>
        </dl>

        <%-->
        -- Contacts
        <--%>
        <h3>Контакты: </h3>
        <c:forEach items="<%=ContactType.values()%>" var="с_type">
            <jsp:useBean id="с_type" type="com.gmail.aazavoykin.model.ContactType"/>
            <dl>
                <dt><%=с_type.getTitle()%>
                </dt>
                <dd><input type="text" name="${с_type.name()}" value="${resume.getContact(с_type)}" size="50"/></dd>
            </dl>
        </c:forEach>
        <hr>

        <%-->
        -- Sections
        <--%>
        <c:forEach items="${resume.sections}" var="sectionEntry">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.gmail.aazavoykin.model.SectionType,
                     com.gmail.aazavoykin.model.AbstractSection>"/>
            <%=HtmlWriter.createSectionForm(sectionEntry.getKey(), sectionEntry.getValue())%>
        </c:forEach>

        <hr>

        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>