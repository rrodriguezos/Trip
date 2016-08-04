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

	<form:form action="slot/user/create.do" modelAttribute="slot">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="dailyPlan"/>


		<acme:textbox code="slot.title" path="title" />

		<acme:date code="slot.startTime" path="startTime" readonly="false" />

		<acme:date code="slot.endTime" path="endTime" readonly="false" />

		<acme:textbox code="slot.description" path="description" />

		<acme:textbox code="slot.photos" path="photos" />

		<td>
				<acme:select items="${activities}" itemLabel="title"
					code="slot.activity" path="activity" /> 
		</td>

		<input type="submit" name="save"
			value="<spring:message code="slot.save" />" />
		<acme:cancel url="trip/user/mylist.do" code="slot.cancel" />

	</form:form>

</security:authorize>