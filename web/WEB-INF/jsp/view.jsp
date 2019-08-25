<%@ page import="com.gmail.aazavoykin.model.ListSection" %>
<%@ page import="com.gmail.aazavoykin.model.Organization.Period" %>
<%@ page import="com.gmail.aazavoykin.model.OrganizationSection" %>
<%@ page import="com.gmail.aazavoykin.model.SectionType" %>
<%@ page import="com.gmail.aazavoykin.model.TextSection" %>
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
    <h1>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></h1>
    <%-- Contacts--%>
    <h3>Контакты:</h3>
    <p>
        <c:forEach items="${resume.contacts}" var="contactEntry">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.gmail.aazavoykin.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().getTitle()%>:&nbsp<%=contactEntry.getValue()%>
            <br/>
        </c:forEach>
    </p>
    <hr/>
    <%-- Sections--%>
    <c:forEach items="${resume.sections}" var="sectionEntry">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.gmail.aazavoykin.model.SectionType,
                     com.gmail.aazavoykin.model.AbstractSection>"/>
        <h3>${sectionEntry.key.title}:</h3>
        <%--
        -- Use util method
        <p><%=HtmlWriter.toHtml(sectionEntry.getKey(), sectionEntry.getValue())%></p>
        -- or block of code with tags:
        --%>
        <p>
        <c:choose>
            <c:when test="${sectionEntry.key.equals(SectionType.PERSONAL) || sectionEntry.key.equals(SectionType.OBJECTIVE)}">
                <%= ((TextSection) sectionEntry.getValue()).getBody() %>
            </c:when>
            <c:when test="${sectionEntry.key.equals(SectionType.ACHIEVEMENT) || sectionEntry.key.equals(SectionType.QUALIFICATION)}">
                <ul>
                    <c:forEach var="skill" items="<%=((ListSection) sectionEntry.getValue()).getSkills()%>">
                        <li>${skill}</li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:when test="${sectionEntry.key.equals(SectionType.EDUCATION) || sectionEntry.key.equals(SectionType.EXPERIENCE)}">
                <c:forEach var="organization"
                           items="<%=((OrganizationSection) sectionEntry.getValue()).getOrganizations()%>">
                    <jsp:useBean id="organization" type="com.gmail.aazavoykin.model.Organization"/>
                    <table>
                        <tr>
                            <td colspan="2"><h4>
                                <c:choose>
                                    <c:when test="${organization.link.url == null}">${organization.link.name}</c:when>
                                    <c:otherwise>
                                        <a href=${organization.link.url}>${organization.link.name}</a>
                                    </c:otherwise>
                                </c:choose>
                            </h4></td>
                        </tr>
                        <c:forEach var="period" items="<%=organization.getPeriods()%>">
                            <jsp:useBean id="period" type="com.gmail.aazavoykin.model.Organization.Period"/>
                            <tr>
                                <td style="vertical-align: top"><%=period.getStartDate().format(Period.FORMATTER)%> -
                                    <%=period.getEndDate().format(Period.FORMATTER)%>
                                </td>
                                <td>
                                    <b>
                                            ${period.title}
                                    </b>
                                    <br>
                                        ${period.description}
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:forEach>
            </c:when>
        </c:choose>
        </p>
        <%-- end of alternative block with tags --%>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
