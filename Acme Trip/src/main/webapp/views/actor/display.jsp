<%--display.jsp
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

<div>
	<b><spring:message code="actor.name" />: </b>
	<jstl:out value="${user.name}" />
</div>

<div>
	<b><spring:message code="actor.surname" />: </b>
	<jstl:out value="${user.surname}" />
</div>

<div>
	<b><spring:message code="actor.emailAddress" />: </b>
	<jstl:out value="${user.emailAddress}" />
</div>

<div>
	<b><spring:message code="actor.phone" />: </b>
	<jstl:out value="${user.phone}" />
</div>


<h2>
	<spring:message code="actor.trips" />
</h2>
<display:table name="trips" id="row" pagesize="5"
	requestURI="user/display.do" class="displaytag">

	<spring:message code="trip.title" var="title" />
	<display:column title="${title}" property="title" />

	<spring:message code="trip.display" var="display" />
	<display:column title="${display}">
		<input type="button" value="<spring:message code="trip.display" />"
			onclick="javascript: window.location.assign('trip/display.do?tripId=${row.id}')" />
	</display:column>

</display:table>

<input type="button" name="back"
	value="<spring:message code="actor.back"/>"
	onclick="javascript: window.location.assign('user/list.do')" />