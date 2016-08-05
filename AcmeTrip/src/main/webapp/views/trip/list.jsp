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

<jstl:if test="${requestUri=='/trip/list.do'}">
	<form:form action="trip/search.do" modelAttribute= "String">
		<input type="text" name="search"/>
		<input type="submit" name="searchTrip"
			value="<spring:message code="trip.search" />" />
	</form:form>
</jstl:if>

<display:table name="trips" id="row" pagesize="5" requestURI="${requestUri}" class="displaytag">
	
	<spring:message code="trip.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	
	<spring:message code="trip.startDate" var="startDateHeader" />
	<display:column property="startDate" title="${startDateHeader}"
		format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" />
	
	<spring:message code="trip.endDate" var="endDateHeader" />
	<display:column property="endDate" title="${endDateHeader}"
		format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" />
		
	<spring:message code="trip.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />
	
	<spring:message code="trip.display" var="display" />
	<display:column title="${display}">
			<input type="button" value="<spring:message code="trip.display" />" 
					onclick="javascript: window.location.assign('trip/display.do?tripId=${row.id}')" />
	</display:column>
	
	<spring:message code="trip.dailyPlans" var="dailyPlansHeader" />
	<display:column title="${dailyPlansHeader}">
			<input type="button" value="<spring:message code="trip.dailyPlans" />" 
					onclick="javascript: window.location.assign('dailyPlan/list.do?tripId=${row.id}')" />
	</display:column>
	

</display:table>
<jstl:if test="${!(requestUri=='/trip/user/subscriptions.do')}">
<security:authorize access="hasRole('USER')">
	<input type="button" name="create" value="<spring:message code="trip.create" />"
	 onclick="javascript: window.location.assign('trip/user/create.do')" />
</security:authorize>
</jstl:if>