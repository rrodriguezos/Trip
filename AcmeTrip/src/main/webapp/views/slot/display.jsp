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

<acme:jstlOut code="slot.title" value="${slot.title }"/>
<acme:jstlOut code="slot.description" value="${slot.description }"/>
<acme:jstlOut code="slot.startTime" value="${slot.startTime }"/>
<acme:jstlOut code="slot.endTime" value="${slot.endTime }"/>

<b><spring:message code="slot.photos" />: </b>
<br>
<jstl:forEach var="photo" items="${slot.getPhotos() }">
	<img width="200px" height="200x" src="${photo}" alt="${ slot.title } photo"/>
</jstl:forEach>
<br>


