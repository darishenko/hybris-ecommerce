<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div style="font-size: ${fontSize}">
    <spring:message code="text.title.questionsCMSComponent"/>
    <c:forEach items="${questions}" var="question">
        <div>
            <spring:message code="text.customer"/>${question.questionCustomer}
            <spring:message code="text.question"/>${question.question}
        </div>
        <c:if test="${not empty question.answer}">
            <spring:message code="text.customer"/>${question.answerCustomer}
            <spring:message code="text.answer"/>${question.answer}
        </c:if>
    </c:forEach>
</div>