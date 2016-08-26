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


<display:table name="dailyplan" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag" keepStatus="true">

	<spring:message code="dailyPlan.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />

	<spring:message code="dailyPlan.day" var="dayHeader" />
	<display:column property="weekDay" title="${dayHeader}"
		format="{0,date,dd/MM/yyyy}" sortable="true" />

	<spring:message code="dailyPlan.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />

	<spring:message code="dailyPlan.display" var="display" />
	<display:column title="${display}">
		<input type="button"
			value="<spring:message code="dailyPlan.display" />"
			onclick="javascript: window.location.assign('dailyPlan/display.do?dailyPlanId=${row.id}')" />
	</display:column>



	<spring:message code="dailyPlan.trip" var="trip" />
	<display:column title="${trip}" sortable="true">
		<input type="button" value="<spring:message code="dailyPlan.trip" />"
			onclick="javascript: window.location.assign('trip/navigateByDailyPlan.do?dailyPlanId=${row.id}')" />
	</display:column>


</display:table>