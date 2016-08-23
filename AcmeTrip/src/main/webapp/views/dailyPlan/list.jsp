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

<jstl:if test="${tieneBanner==true }" >
	<fieldset>
	<legend><spring:message code="banner.publi"/></legend>
	<img width="500px" height="100x" src="${banner.getPhoto()}"/>
	</fieldset>
	<br>
</jstl:if>


<display:table name="dailyPlans" id="row" pagesize="5"
	requestURI="dailyPlan/list.do" class="displaytag">
	<form:hidden path="tripId" />

	<spring:message code="dailyPlan.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />

	<spring:message code="dailyPlan.day" var="weekDayHeader" />
	<display:column property="weekDay" title="${weekDayHeader}"
		format="{0,date,dd/MM/yyyy}" sortable="true" />

	<spring:message code="dailyPlan.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />
	
	<spring:message code="dailyPlan.display" var="display" />
	<display:column title="${display}">
		<input type="button" value="<spring:message code="dailyPlan.display" />"
			onclick="javascript: window.location.assign('dailyPlan/display.do?dailyPlanId=${row.id}')" />
	</display:column>
	
	<security:authorize access="hasRole('USER')">
		<jstl:if test="${mytrip == true}">
		<spring:message code="dailyPlan.delete" var="delete" />
		<display:column title="${delete}">
		<input type="button" name="delete"
			value="<spring:message code="dailyPlan.delete" />"
			onclick="javascript: window.location.assign('dailyPlan/user/delete.do?dailyPlanId=${row.id}')" />
		</display:column>
		</jstl:if>
	</security:authorize>
	
	<spring:message code="dailyPlan.slots" var="slotsHeader" />
	<display:column title="${slotsHeader}">
		<input type="button" value="<spring:message code="dailyPlan.slots" />"
			onclick="javascript: window.location.assign('slot/list.do?dailyPlanId=${row.id}')" />
	</display:column>

</display:table>

<security:authorize access="hasRole('USER')">
	<jstl:if test="${mytrip == true}">


		<input type="button" name="create"
			value="<spring:message code="dailyPlan.create" />"
			onclick="javascript: window.location.assign('dailyPlan/user/create.do?tripId=${tripId}')" />
	</jstl:if>
</security:authorize>