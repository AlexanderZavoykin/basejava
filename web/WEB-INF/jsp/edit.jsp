<%@ page import="com.gmail.aazavoykin.model.*" %>
<%@ page import="com.gmail.aazavoykin.model.TextSection" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gmail.aazavoykin.util.DateUtil" %>
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
                    ${contactType.title}
                </dt>
                <dd>
                    <label>
                        <input size="50" type="text" name="${contactType.name()}"
                               value="${resume.getContact(contactType)}"/>
                    </label>
                </dd>
            </dl>
        </c:forEach>
        <br>
        <hr>

        <%-->Sections<--%>
        <%--
        -- Use util method
        <%=HtmlWriter.createSectionForms(resume)%>
        -- or block with tags:
        --%>
        <c:forEach items="<%=SectionType.values()%>" var="sectionType">
            <c:set var="section" value="${resume.getSection(sectionType)}"/>
            <dl>
                <dt>
                    <h2>
                        ${sectionType.title}
                    </h2>
                </dt>
                <br>
                <c:choose>
                    <%-- TextSection --%>
                    <c:when test="${sectionType.equals(SectionType.PERSONAL) || sectionType.equals(SectionType.OBJECTIVE)}">
                        <dd>
                            <textarea rows="4" cols="150" name="${sectionType.name()}">${section.getBody()}</textarea>
                        </dd>
                    </c:when>

                    <%-- ListSection --%>
                    <c:when test="${sectionType.equals(SectionType.ACHIEVEMENT) || sectionType.equals(SectionType.QUALIFICATION)}">
                        <dd>
                            <textarea rows="4" cols="150" name="${sectionType.name()}"><c:forEach var="skill" items="${section.getSkills()}">${skill}</c:forEach></textarea>
                        </dd>
                    </c:when>

                    <%-- OrganizationSection --%>
                    <c:when test="${sectionType.equals(SectionType.EXPERIENCE) || sectionType.equals(SectionType.EDUCATION)}">
                        <dd>
                            <p>
                                Поля, обязательные для заполнения, отмечены *.
                                <br>
                                Для удаления организации оставьте пустым поле с её названием.
                                <br>
                                Для удаления периода оставьте пустым после с его заголовком.
                            </p>
                                <%-- First, get existing organizations to edit --%>
                                    <c:forEach var="org" items="${resume.getSection(sectionType).getOrganizations()}"
                                               varStatus="orgCounter">
                                        <%-- Organization name input --%>
                                    <dl>
                                        <dt><b>Название организации*:</b></dt>
                                        <dd>
                                            <input type="text" size="50"
                                                   placeholder="Оставьте это поле пустым для удаления организации"
                                                   name="${sectionType.name()}_name"
                                                   value="${org.link.name}"/>
                                        </dd>
                                    </dl>
                                        <%-- Organization url input --%>
                                    <dl>
                                        <dt>Url:</dt>
                                        <dd>
                                            <input type="text" size="50"
                                                   name="${sectionType.name()}_${orgCounter.count}_url"
                                                   value="${org.link.url}"/>
                                        </dd>
                                    </dl>
                                        <br>
                                        <%-- Organization periods inputs --%>
                                    <c:forEach var="period" items="${org.periods}">
                                        <dl>
                                            <dt>Начало*:</dt>
                                            <dd>
                                                <input type="text" size="4"
                                                       placeholder="ММ-ГГГГ"
                                                       name="${sectionType.name()}_${orgCounter.count}_start"
                                                       value="${period.startDate.format(DateUtil.HTML_FORMATTER)}">
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt>Окончание*:</dt>
                                            <dd>
                                                <input type="text" size="4"
                                                       placeholder="ММ-ГГГГ"
                                                       name="${sectionType.name()}_${orgCounter.count}_end"
                                                       value="${period.endDate.format(DateUtil.HTML_FORMATTER)}">
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt>Заголовок*:</dt>
                                            <dd>
                                                <input type="text" size="50"
                                                       placeholder="Оставьте это поле пустым для удаления периода"
                                                       name="${sectionType.name()}_${orgCounter.count}_title"
                                                       value="${period.title}">
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt>Описание:</dt>
                                            <dd>
                                    <textarea rows="4" cols="150" name="${sectionType.name()}_${orgCounter.count}_descr">${period.description}</textarea>
                                            </dd>
                                        </dl>
                                    </c:forEach>
                                        <%-- Inputs for new organization`s period --%>
                                        <h4><i>Добавить новый период в организации ${org.link.name}:</i></h4>
                                        <dl>
                                            <dt><i>Начало*:</i></dt>
                                            <dd>
                                                <input type="text" size="4"
                                                       placeholder="ММ-ГГГГ"
                                                       name="${sectionType.name()}_${orgCounter.count}_NEW_start">
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt><i>Окончание*:</i></dt>
                                            <dd>
                                                <input type="text" size="4"
                                                       placeholder="ММ-ГГГГ"
                                                       name="${sectionType.name()}_${orgCounter.count}_NEW_end">
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt><i>Заголовок*:</i></dt>
                                            <dd>
                                                <input type="text" size="50"
                                                       name="${sectionType.name()}_${orgCounter.count}_NEW_title">
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt><i>Описание:</i></dt>
                                            <dd>
                                                <textarea rows="4" cols="150" name="${sectionType.name()}_${orgCounter.count}_NEW_descr"></textarea>
                                            </dd>
                                        </dl>
                                </c:forEach>
                                    <br>

                                <%-- Now, create inputs for adding new organization --%>
                                    <h4><i>Добавить новую организацию:</i></h4>
                                    <dl>
                                        <dt><b><i>Название организации*:</i></b></dt>
                                        <dd>
                                            <input type="text" size="50"
                                                   name="${sectionType.name()}_NEW_name"
                                                   value="${org.link.name}"/>
                                        </dd>
                                    </dl>

                                    <dl>
                                        <dt><i>Url:</i></dt>
                                        <dd>
                                            <input type="text" size="50"
                                                   name="${sectionType.name()}_NEW_url"
                                                   value="${org.link.url}"/>
                                        </dd>
                                    </dl>
                                    <br>
                                    <dl>
                                        <dt><i>Начало*:</i></dt>
                                        <dd>
                                            <input type="text" size="4"
                                                   placeholder="ММ-ГГГГ"
                                                   name="${sectionType.name()}_NEW_start">
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt><i>Окончание*:</i></dt>
                                        <dd>
                                            <input type="text" size="4"
                                                   placeholder="ММ-ГГГГ"
                                                   name="${sectionType.name()}_NEW_end">
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt><i>Заголовок*:</i></dt>
                                        <dd>
                                            <input type="text" size="50"
                                                   name="${sectionType.name()}_NEW_title">
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt><i>Описание:</i></dt>
                                        <dd>
                                            <textarea rows="4" cols="150" name="${sectionType.name()}_NEW_descr"></textarea>
                                        </dd>
                                    </dl>

                        </dd>
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