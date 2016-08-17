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

<security:authorize access="hasRole('MANAGER')">

	<display:table name="campaigns" id="row" pagesize="5"
		requestURI="campaign/manager/list.do" class="displaytag">

		<spring:message code="campaign.startDate" var="startMoment" />
		<display:column property="startMoment"
			format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" title="${startMoment}"/>

		<spring:message code="campaign.endDate" var="endMoment" />
		<display:column property="endMoment"
			format="{0,date,dd/MM/yyyy HH:mm}" sortable="true" title="${endMoment}"/>

		<spring:message code="campaign.creditCard" var="creditCard" />
		<display:column property="creditCard.creditCardNumber" title="${creditCard}"/>

		<spring:message code="campaign.display" var="display" />
		<display:column title="${display}">
			<input type="button"
				value="<spring:message code="campaign.display" />"
				onclick="javascript: window.location.assign('campaign/manager/display.do?campaignId=${row.id}')" />
		</display:column>

	</display:table>

	<a href="campaign/manager/create.do"><spring:message
			code="master.page.manager.createCamp" /></a>

</security:authorize>