<%--display.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
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

<div>
	<b><spring:message code="slot.title" />: </b>
	<jstl:out value="${title}" />
</div>
<div>
	<b><spring:message code="slot.description" />: </b>
	<jstl:out value="${description}" />
</div>

<div>
	<b><spring:message code="slot.startTime" />: </b>
	<fmt:formatDate value="${startTime}"
		pattern="dd/MM/yyyy HH:mm" />
</div>
<div>
	<b><spring:message code="slot.endTime" />: </b>
	<fmt:formatDate value="${endTime}"
		pattern="dd/MM/yyyy HH:mm" />
</div>


<b><spring:message code="slot.photos" />: </b>
<br>
<jstl:forEach var="photo" items="${slot.getPhotos() }">
	<img width="175px" height="175x" src="${photo}"
		alt="${ slot.title } photo" />
</jstl:forEach>
<br>