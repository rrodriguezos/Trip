<%--
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<security:authorize access="hasRole('MANAGER')">


	<acme:jstlOut code="campaign.startDate"
		value="${campaign.startMoment }" />
	<acme:jstlOut code="campaign.endDate" value="${campaign.endMoment }" />
	<jstl:if test="${edita==true}">
		<input type="button" value="<spring:message code="campaign.edit" />"
			onclick="javascript: window.location.assign('campaign/manager/edit.do?campaignId=${campaign.id}')" />
	</jstl:if>
	<br>
	<b><spring:message code="campaign.banner" />: </b>
	<br>
	<jstl:forEach var="banner" items="${campaign.getBanners()}">
		<img width="200px" height="200x" src="${banner.getPhoto()}" /><br>
		<acme:jstlOut code="campaign.price"	value="${banner.getPrice()} Euro" /><br>
	</jstl:forEach>
	<br>	
	<jstl:if test="${edita==true}">
		<input type="button"
			value="<spring:message code="campaign.addBanner" />"
			onclick="javascript: window.location.assign('campaign/manager/createBanner.do?campaignId=${campaign.id}')" />
		<input type="submit" name="delete"
			value="<spring:message code="campaign.delete"/>"
			onclick="javascript: window.location.assign('campaign/manager/delete.do?campaignId=${campaign.id}')" />
	</jstl:if>


</security:authorize>
<br>

<br>
<input type="button" name="cancel"
	value="<spring:message code="campaign.cancel"/>"
	onclick="javascript: window.history.back()" />
