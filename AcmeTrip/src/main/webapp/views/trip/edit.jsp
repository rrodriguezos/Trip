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

	<form:form action="trip/user/edit.do" modelAttribute="trip">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="user" />
		<form:hidden path="dailyPlans" />
		<form:hidden path="comments" />
		<form:hidden path="users" />


		<acme:textbox code="trip.title" path="title" />

		<acme:date code="trip.startDate" path="startDate" readonly="false" />

		<acme:date code="trip.endDate" path="endDate" readonly="false" />

		<acme:textbox code="trip.description" path="description" />

		<acme:textbox code="trip.photos" path="photos" />
		
		<jstl:if test="${trip.id == 0}">
		<input type="submit" name="save"
			value="<spring:message code="trip.save" />" />
		</jstl:if>
		
		<jstl:if test="${trip.id != 0}">
			<input type="submit" name="save"
			value="<spring:message code="trip.save" />" onclick="return confirm('<spring:message code="trip.confirm.delete.dailyPlans" />')" />
			<input type="submit" name="delete" value="<spring:message code="trip.delete"/>" onclick="return confirm('<spring:message code="trip.confirm.delete" />')"/>
		</jstl:if>
		
		<acme:cancel url="trip/user/mylist.do" code="trip.cancel" />

	</form:form>

</security:authorize>