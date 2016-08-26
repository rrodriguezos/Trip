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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<display:table name="slots" id="row" pagesize="5"
	requestURI="slot/list.do" class="displaytag">
	<form:hidden path="dailyPlanId" />
	<spring:message code="slot.title" var="title" />
	<display:column property="title" title="${title}" />

	<spring:message code="slot.description" var="description" />
	<display:column property="description" title="${description}" />

	<spring:message code="slot.startTime" var="startTime" />
	<display:column property="startTime" title="${startTime}"
		sortable="true" format="{0, date, dd/MM/yyyy HH:mm}" />

	<spring:message code="slot.endTime" var="endTime" />
	<display:column property="endTime" title="${endTime}" sortable="true"
		format="{0, date, dd/MM/yyyy HH:mm}" />

	<spring:message code="slot.display" var="display" />
	<display:column title="${display}">
		<input type="button" value="<spring:message code="slot.display" />"
			onclick="javascript: window.location.assign('slot/display.do?slotId=${row.id}')" />
	</display:column>
	
	<spring:message code="slot.activity" var="activityHeader" />
	<display:column title="${activityHeader}">
			<input type="button" value="<spring:message code="slot.activity" />" 
					onclick="javascript: window.location.assign('activity/listBySlot.do?slotId=${row.id}')" />
	</display:column>


	<security:authorize access="hasRole('USER')">
		<jstl:if test="${mytrip == true}">
			<spring:message code="slot.delete" var="delete" />
			<display:column title="${delete}">
				<input type="button" name="delete"
					value="<spring:message code="slot.delete" />"
					onclick="javascript: window.location.assign('slot/user/delete.do?slotId=${row.id}')" />
			</display:column>
		</jstl:if>
	</security:authorize>

</display:table>

<security:authorize access="hasRole('USER')">
	<jstl:if test="${mytrip == true}">

		<input type="button" name="create"
			value="<spring:message code="slot.create" />"
			onclick="javascript: window.location.assign('slot/user/create.do?dailyPlanId=${dailyPlanId}')" />

	</jstl:if>
</security:authorize>