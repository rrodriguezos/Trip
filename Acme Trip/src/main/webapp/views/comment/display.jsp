
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<h3>
	<jstl:choose>
	<jstl:when test="${comment.getIsAppropiate()==true }">
	<jstl:out value="${comment.getTitle() }" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="comment.notAppropriated"/>
	</jstl:otherwise>
	</jstl:choose>
</h3>

<spring:message code="comment.actor" var="name" />
	<display:column title="${comment.getActor().getName()}">
	</display:column>
<br>

<legend><fmt:formatDate value="${comment.getMoment() }" pattern="dd/MM/yyyy HH:mm" /></legend>
<jstl:choose>
<jstl:when test="${comment.getAppropriated()==true }">
<jstl:out value="${comment.getText() }" />
</jstl:when>
<jstl:otherwise>
<spring:message code="comment.notAppropriated"/>
</jstl:otherwise>
</jstl:choose>


<security:authorize access="hasRole('ADMINISTRATOR')">
	<jstl:if test="${comment.getIsAppropiate()==true }">
		<input type="button" value="<spring:message code="notAppropriated" />" 
			onclick="javascript: window.location.assign('comment/administrator/mark.do?commentId=${comment.id}')" />			
	</jstl:if>
	<jstl:if test="${comment.getIsAppropiate()==false }">
		<input type="button" value="<spring:message code="comment.isAppropriated" />" 
			onclick="javascript: window.location.assign('comment/administrator/mark.do?commentId=${comment.id}')" />			
	</jstl:if>
</security:authorize>

<input type="button" name="back" value="<spring:message code="comment.cancel"/>" onclick="javascript: window.history.back()" />