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

	<form:form action="dailyPlan/user/create.do" modelAttribute="dailyPlan">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="slots" />
		<form:hidden path="trip"/>


		<acme:textbox code="dailyPlan.title" path="title" />

		<acme:date code="dailyPlan.day" path="weekDay" readonly="false" />

		<acme:textbox code="dailyPlan.description" path="description" />

		<acme:textbox code="dailyPlan.photos" path="photos" />



		<input type="submit" name="save"
			value="<spring:message code="dailyPlan.save" />" />
		<acme:cancel url="trip/user/mylist.do" code="dailyPlan.cancel" />

	</form:form>

</security:authorize>