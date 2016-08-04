<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<h2>
	<jstl:choose>
	<jstl:when test="${comment.getAppropriated()==true }">
	<jstl:out value="${comment.getTitle() }" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="comment.inAppropriate"/>
	</jstl:otherwise>
	</jstl:choose>
</h2>

<acme:jstlOut code="comment.actor" value="${comment.getActor().getName() }"/>
<br>
<fieldset style="width:50%;">
<legend><fmt:formatDate value="${comment.getMoment() }" pattern="dd/MM/yyyy HH:mm" /></legend>
<jstl:choose>
<jstl:when test="${comment.getAppropriated()==true }">
<jstl:out value="${comment.getText() }" />
</jstl:when>
<jstl:otherwise>
<spring:message code="comment.inAppropriate"/>
</jstl:otherwise>
</jstl:choose>
</fieldset>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<jstl:if test="${comment.getAppropriated()==true }">
		<input type="button" value="<spring:message code="comment.isInappropriated" />" 
			onclick="javascript: window.location.assign('comment/administrator/appropriated.do?commentId=${comment.id}')" />			
	</jstl:if>
	<jstl:if test="${comment.getAppropriated()==false }">
		<input type="button" value="<spring:message code="comment.isAppropriated" />" 
			onclick="javascript: window.location.assign('comment/administrator/appropriated.do?commentId=${comment.id}')" />			
	</jstl:if>
</security:authorize>

<input type="button" name="back" value="<spring:message code="comment.cancel"/>" onclick="javascript: window.history.back()" />