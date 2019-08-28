<%@ page import="com.gmail.aazavoykin.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Перечень резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<hr/>
<section>
    <table border=1 cellpadding="8" cellspacing="0">
        <tr>
            <td align='middle'><b>Имя</b></td>
            <td align='middle'><b>E-mail</b></td>
            <td></td>
            <td></td>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.gmail.aazavoykin.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td>${resume.getContact(ContactType.EMAIL)}</td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.png"
                                                                          title="Редактировать"
                                                                          alt="Редактировать"
                                                                          width="30" height="30"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"
                                                                            title="Удалить"
                                                                            alt="Удалить"
                                                                            width="30" height="30"></a></td>
            </tr>
        </c:forEach>
    </table>
    <h4><a href="resume?uuid=${resume.uuid}&action=add"><img src="img/add.png"
                                                             alt="Добавить новое резюме"
                                                             title="Добавить новое резюме"
                                                             width="30" height="30"></a></h4>
    <hr/>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
