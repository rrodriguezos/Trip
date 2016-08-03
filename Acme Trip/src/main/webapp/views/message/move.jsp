<%--
 * move.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="message/move.do" modelAttribute="message">
	<form:hidden path="id"/>
	<form:hidden path="version" />
	<form:hidden path="sender"/>
	<form:hidden path="moment"/>
	<form:hidden path="subject"/>
	<form:hidden path="body"/>
	<form:hidden path="messagePriority"/>
	<form:hidden path="recipient"/>
	
	<jstl:if test="${ folders.size()!=0 }">
	<spring:message code="message.folder" />
	<form:select path="folder">
		<form:options items="${folders}" itemLabel="name" itemValue="id"/>	
	</form:select>
	<form:errors cssClass="error" path="folder"/>
	<br>
	
	<input type="submit" name="save" value="<spring:message code="message.save" />" />
	</jstl:if>
	
	<input type="button" name="cancel" value="<spring:message code="message.cancel" />" onclick="javascript: window.history.back()" />
	
</form:form>
