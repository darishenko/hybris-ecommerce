<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:useBean id="questions" scope="request" type="java.util.List<com.myapp.questions.data.QuestionData>"/>
<%--@elvariable id="fontSize" type="java.lang.Integer"--%>

<html>
<body>
<div style="font-size: ${fontSize}">
    <h4 style="font-weight: bold">
        <spring:message code="text.title.questionsCMSComponent"/>
    </h4>
    <table border="1">
        <thead style="background: #0f828f; font-weight: bold">
        <tr>
            <td>
                <spring:message code="text.customer"/>
            </td>
            <td>
                <spring:message code="text.question"/>
            </td>
            <td>
                <spring:message code="text.customer"/>
            </td>
            <td>
                <spring:message code="text.answer"/>
            </td>
        </tr>
        </thead>
        <c:forEach items="${questions}" var="question">
            <tr style="font-style: italic">
                <td> ${question.questionCustomerName}</td>
                <td> ${question.question}</td>

                <td> ${question.answerCustomerName}</td>
                <td> ${question.answer}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>