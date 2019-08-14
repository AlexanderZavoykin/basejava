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
    <h4>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit">Edit</a></h4>
    <p>
    <c:forEach items="${resume.contacts}" var="contactEntry">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<com.gmail.aazavoykin.model.ContactType, java.lang.String>"/>
        <%=contactEntry.getKey().toString()%>:&nbsp<%=contactEntry.getValue()%>
        <br/>
    </c:forEach>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
