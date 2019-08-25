<%@ page import="com.gmail.aazavoykin.model.ContactType" %>
<%@ page import="com.gmail.aazavoykin.model.ListSection" %>
<%@ page import="com.gmail.aazavoykin.model.SectionType" %>
<%@ page import="com.gmail.aazavoykin.model.TextSection" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.gmail.aazavoykin.model.Resume" scope="request"/>
    <title>
        <%= request.getParameter("action").equals("edit") ? "Резюме " + resume.getFullName() : "Новое резюме" %>
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
        <%--
        -- Use util method
        <%=HtmlWriter.createContactForms(resume)%>
        -- or block with tags:
        --%>
        <c:forEach items="${ContactType.values()}" var="contactType">
            <jsp:useBean id="contactType" type="com.gmail.aazavoykin.model.ContactType"/>
            <dl>
                <dt>
                    <%=contactType.getTitle()%>
                </dt>
                <dd>
                    <label>
                        <input size="50" type="text" name="${contactType.name()}"
                               value="${resume.getContact(contactType)}"/>
                    </label>
                </dd>
            </dl>
        </c:forEach>
        <hr>

        <%-->Sections<--%>
        <%--
        -- Use util method
        <%=HtmlWriter.createSectionForms(resume)%>
        -- or block with tags:
        --%>
        <c:forEach items="${SectionType.values()}" var="sectionType">
            <jsp:useBean id="sectionType" type="com.gmail.aazavoykin.model.SectionType"/>
            <dl>
                <dt>
                    <h3>
                        <%=sectionType.getTitle()%>
                    </h3>
                </dt>
                <br>
                <c:choose>
                    <%-- TextSection --%>
                    <c:when test="${sectionType.equals(SectionType.PERSONAL) || sectionType.equals(SectionType.OBJECTIVE)}">
                        <dd>
                            <% TextSection section = (TextSection) resume.getSection(sectionType); %>
                            <% String body = section != null ? section.getBody() : null; %>
                            <textarea rows="4" cols="150" name="<%=sectionType.name()%>"><c:if
                                    test="<%=body!=null%>"><%=body%>
                            </c:if></textarea>
                        </dd>
                    </c:when>
                    <%-- ListSection --%>
                    <c:when test="${sectionType.equals(SectionType.ACHIEVEMENT) || sectionType.equals(SectionType.QUALIFICATION)}">
                        <dd>
                            <% ListSection section = (ListSection) resume.getSection(sectionType); %>
                            <% List<String> skills = section != null ? section.getSkills() : null; %>
                            <textarea rows="4" cols="150" name="<%=sectionType.name()%>"><c:if test="<%=skills!=null%>"><c:forEach
                                    var="skill" items="<%=skills%>">${skill}</c:forEach></c:if></textarea>
                        </dd>
                    </c:when>
                    <%-- OrganizationSection --%>
                    <c:when test="${sectionType.equals(SectionType.EXPERIENCE) || sectionType.equals(SectionType.EDUCATION)}">


                    </c:when>
                </c:choose>
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