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

<jstl:if test="${tieneBanner==true }" >
	<fieldset>
	<legend><spring:message code="banner.publi"/></legend>
	<img width="500px" height="100x" src="${banner.getPhoto()}"/>
	</fieldset>
	<br>
</jstl:if>


<acme:jstlOut code="dailyPlan.title" value="${dailyPlan.title }"/>
<acme:jstlOut code="dailyPlan.description" value="${dailyPlan.description }"/>
<acme:jstlOut code="dailyPlan.day" value="${dailyPlan.weekDay }"/>

<b><spring:message code="dailyPlan.photos" />: </b>
<br>
<jstl:forEach var="photo" items="${dailyPlan.getPhotos() }">
	<img width="200px" height="200x" src="${photo}" alt="${ dailyPlan.title } photo"/>
</jstl:forEach>
<br>


