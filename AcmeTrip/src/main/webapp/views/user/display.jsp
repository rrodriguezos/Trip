<%--
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:jstlOut code="user.name" value="${user.name }"/>
<acme:jstlOut code="user.surname" value="${user.surname }"/>
<acme:jstlOut code="user.email" value="${user.email }"/>
<acme:jstlOut code="user.phone" value="${user.phone }"/>

<h2><spring:message code="user.trips"/></h2>
<display:table name="trips" id="row" pagesize="5" requestURI="user/display.do" class="displaytag">
	
	<spring:message code="trip.title" var="title" />
	<display:column title="${title}" property="title" />
	
	<spring:message code="trip.display" var="display" />
	<display:column title="${display}">
			<input type="button" value="<spring:message code="trip.display" />" 
					onclick="javascript: window.location.assign('trip/display.do?tripId=${row.id}')" />
	</display:column>
	
</display:table>

<input type="button" name="cancel" value="<spring:message code="user.cancel"/>" onclick="javascript: window.location.assign('user/list.do')" />
