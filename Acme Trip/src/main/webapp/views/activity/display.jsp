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


<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${activity.isAppropiate==true }">
	<b><spring:message code="activity.state" />: </b>
	<br>
		<input type="button"
			value="<spring:message code="activity.changeStateToNotApropiated" />"
			onclick="javascript: window.location.assign('activity/manager/mark.do?activityId=${activity.id}')" />
	</jstl:if>
	<jstl:if test="${activity.isAppropiate==false }">
	<b><spring:message code="activity.state" />: </b>
	<br>
		<input type="button"
			value="<spring:message code="activity.changeStateToApropiated" />"
			onclick="javascript: window.location.assign('activity/manager/mark.do?activityId=${activity.id}')" />
	</jstl:if>
</security:authorize>
<br>
<br>
<br>
<b><spring:message code="activity.title" />: </b>
<form:input path="activity.title" readonly="true" />
<br>
<br>
<b><spring:message code="activity.description" />: </b>
<form:input path="activity.description" readonly="true" />
<br>
<b><spring:message code="activity.photos" />: </b>
<br>
<jstl:forEach var="photo" items="${activity.photos }">
	<img width="200px" height="200x" src="${photo}"
		alt="${ activity.title } photo" />
</jstl:forEach>
<br>

<h2>
	<spring:message code="activity.trips" />
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


<spring:message code="activity.comment" var="comments" />
			<input type="button" value="<spring:message code="activity.comment" />" 
					onclick="javascript: window.location.assign('comment/list.do?id=<jstl:out value="${row.id}"/>')" />


<input type="button" name="cancel"
	value="<spring:message code="activity.cancel"/>"
	onclick="javascript: window.location.assign('activity/list.do')" />