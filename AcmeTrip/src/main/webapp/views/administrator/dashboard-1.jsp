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


<p><b><spring:message code="dashboard-1.1" /></b></p>
<jstl:out value="${numberUsers}"></jstl:out>
<br />
<hr>
<br />

<p><b><spring:message code="dashboard-1.2" /></b></p>
<jstl:out value="${numberTripsRegistered}"></jstl:out>
<br />
<hr>
<br />

<p><b><spring:message code="dashboard-1.3" /></b></p>
<p><b><i><spring:message code="average" /><jstl:out value="${averageNumberTripsPerUser[0]}"/></i></b></p>
<p><b><i><spring:message code="standard" /><jstl:out value="${averageNumberTripsPerUser[1]}"/></i></b></p>
<br/>
<hr>
<br/>

<p><b><spring:message code="dashboard-1.4" /></b></p>
<p><b><i><spring:message code="average" /><jstl:out value="${averageNumberDailyPlansPerTrip[0]}"/></i></b></p>
<p><b><i><spring:message code="standard" /><jstl:out value="${averageNumberDailyPlansPerTrip[1]}"/></i></b></p>
<br/>
<hr>
<br/>

<p><b><spring:message code="dashboard-1.5" /></b></p>

<display:table name="usersCreated80MaximunNumbersOfTrips" id="row" pagesize="5" requestURI="dashboard/administrator/dashboard-1.do" class="displaytag">
	
	<spring:message code="user.name" var="name" />
	<display:column title="${name}" >
		<jstl:out value="${row[0].getName() }" />
	</display:column>
	
	<spring:message code="user.surname" var="surname" />
	<display:column title="${surname}" >
		<jstl:out value="${row[0].getSurname() }" />
	</display:column>
	
	<spring:message code="user.trips" var="trips" />
	<display:column title="${trips}" >
		<jstl:out value="${row[0].getTrips().size() }" />
	</display:column>
	
</display:table>

<jstl:if test="${!empty usersCreated80MaximunNumbersOfTrips }">
	<p><b><i><spring:message code="user.max.trips" /><jstl:out value="${ usersCreated80MaximunNumbersOfTrips.get(0)[1] }" /></i></b></p>
</jstl:if>
<br />
<hr>
<br />

<p><b><spring:message code="dashboard-1.6" /></b></p>

<display:table name="usersInactiveInLastYear" id="row" pagesize="5" requestURI="dashboard/administrator/dashboard-1.do" class="displaytag">
	
	<spring:message code="user.name" var="name" />
	<display:column title="${name}" >
		<jstl:out value="${row.getName() }" />
	</display:column>
	
	<spring:message code="user.surname" var="surname" />
	<display:column title="${surname}" >
		<jstl:out value="${row.getSurname() }" />
	</display:column>
	
</display:table>
<br />
<hr>
<br />