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



<display:table name="dailyplans" id="row" pagesize="5"
	requestURI="dailyolan/list.do" class="displaytag">
	<form:hidden path="tripId" />

	<spring:message code="dailyplan.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />

	<spring:message code="dailyplan.weekDay" var="weekDayHeader" />
	<display:column property="weekDay" title="${weekDayHeader}"
		format="{0,date,dd/MM/yyyy}" sortable="true" />

	<spring:message code="dailyplan.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />

	<security:authorize access="hasRole('USER')">
		<jstl:if test="${mytrip == true}">
			<spring:message code="dailyplan.delete" var="delete" />
			<display:column title="${delete}">
				<input type="button" name="delete"
					value="<spring:message code="dailyplan.delete" />"
					onclick="javascript: window.location.assign('dailyplan/user/delete.do?dailyplanId=${row.id}')" />
			</display:column>
		</jstl:if>
	</security:authorize>

	<spring:message code="dailyplan.slots" var="slotsHeader" />
	<display:column title="${slotsHeader}">
		<input type="button" value="<spring:message code="dailyPpan.slots" />"
			onclick="javascript: window.location.assign('slot/list.do?dailyplanId=${row.id}')" />
	</display:column>

</display:table>

<security:authorize access="hasRole('USER')">
	<jstl:if test="${mytrip == true}">


		<input type="button" name="create"
			value="<spring:message code="dailyplan.create" />"
			onclick="javascript: window.location.assign('dailyplan/user/create.do?tripId=${tripId}')" />
	</jstl:if>
</security:authorize>