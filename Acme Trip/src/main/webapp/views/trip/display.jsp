<%--
 *
 * Copyright (C) 2016 Universidad de Sevilla
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<!-- Display trip -->

<b><spring:message code="trip.title" />: </b>
<form:input path="trip.title" readonly="true" />
<br>
<b><spring:message code="trip.description" />: </b>
<form:input path="trip.description" readonly="true" />
<br>
<b><spring:message code="trip.startDate" />: </b>
<form:input path="trip.startDate" readonly="true" />
<br>
<b><spring:message code="trip.endDate" />: </b>
<form:input path="trip.endDate" readonly="true" />
<br>

<b><spring:message code="trip.photos" />: </b>
<br>
<jstl:forEach var="photo" items="${trip.photos }">
	<img width="200px" height="200x" src="${photo}"
		alt="${ trip.title } photo" />
</jstl:forEach>
<br>

<jstl:if test="${principal == true}">
	<jstl:if test="${isMyTrip == false}">

		<input type="button" value="<spring:message code="trip.copyPaste" />"
			onclick="javascript: window.location.assign('trip/user/copyPaste.do?tripId=${trip.id}')" />

	</jstl:if>

	<jstl:if test="${isMyTrip == false}">
		<jstl:if test="${joinedTrip == false}">
			<input type="button" value="<spring:message code="trip.joinTrip" />"
				onclick="javascript: window.location.assign('trip/user/joinTrip.do?tripId=${trip.id}')" />
		</jstl:if>
	</jstl:if>

	<jstl:if test="${isMyTrip == false}">
		<jstl:if test="${joinedTrip == true}">
			<input type="button"
				value="<spring:message code="trip.disjoinTrip" />"
				onclick="javascript: window.location.assign('trip/user/disjoinTrip.do?tripId=${trip.id}')" />

		</jstl:if>
	</jstl:if>
</jstl:if>
<security:authorize access="hasRole('USER')">
	<jstl:if test="${isMyTrip == true}">

		<input type="button" value="<spring:message code="trip.edit" />"
			onclick="javascript: window.location.assign('trip/user/edit.do?tripId=${trip.id}')" />

	</jstl:if>
</security:authorize>
<br>

<acme:cancel code="trip.back" url="trip/list.do" />

