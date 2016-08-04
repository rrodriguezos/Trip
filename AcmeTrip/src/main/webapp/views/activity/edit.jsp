<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('USER')">

	<form:form action="activity/user/edit.do" modelAttribute="activity">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="appropriated" />
		<form:hidden path="user" />
		<form:hidden path="slots" />
		<form:hidden path="comments" />
		<form:hidden path="manager" />

		<acme:textbox code="activity.title" path="title" />

		<acme:textbox code="activity.description" path="description" />

		<acme:textarea code="activity.photos" path="photos" />
		
		<spring:message code="activity.type" />
		<form:select path="type">
			<form:options items="${types}" itemLabel="name" itemValue="id"/>	
		</form:select>
		<form:errors cssClass="error" path="type"/>
		<br>

		<input type="submit" name="save"
			value="<spring:message code="activity.save" />" />
		<acme:cancel url="activity/list.do" code="activity.cancel" />

	</form:form>

</security:authorize>