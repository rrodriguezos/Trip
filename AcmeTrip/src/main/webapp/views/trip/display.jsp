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

<acme:jstlOut code="trip.title" value="${trip.title }"/>
<acme:jstlOut code="trip.description" value="${trip.description }"/>
<acme:jstlOut code="trip.startDate" value="${trip.startDate }"/>
<acme:jstlOut code="trip.endDate" value="${trip.endDate }"/>


<b><spring:message code="trip.photos" />: </b>
<br>
<jstl:forEach var="photo" items="${trip.getPhotos() }">
	<img width="200px" height="200x" src="${photo}" alt="${ trip.title } photo"/>
</jstl:forEach>
<br>

<jstl:if test="${logeado == true}">
	<jstl:if test="${mytrip == false}">
	
		<input type="button" value="<spring:message code="trip.copy" />" 
			onclick="javascript: window.location.assign('trip/user/copyPaste.do?tripId=${trip.id}')" />
		
	</jstl:if>
	
	<jstl:if test="${mytrip == false}">
		<jstl:if test="${joined == false}">
			<input type="button" value="<spring:message code="trip.join" />" 
				onclick="javascript: window.location.assign('trip/user/joinTrip.do?tripId=${trip.id}')" />
		</jstl:if>	
	</jstl:if>
	
	<jstl:if test="${mytrip == false}">
		<jstl:if test="${joined == true}">
			<input type="button" value="<spring:message code="trip.disjoin" />" 
				onclick="javascript: window.location.assign('trip/user/disjoinTrip.do?tripId=${trip.id}')" />
	
		</jstl:if>	
	</jstl:if>
</jstl:if>
<security:authorize access="hasRole('USER')">
  	<jstl:if test="${mytrip == true}">
	
	<input type="button" value="<spring:message code="trip.edit" />" 
			onclick="javascript: window.location.assign('trip/user/edit.do?tripId=${trip.id}')" />

	</jstl:if>
</security:authorize>
<br>
<h2><spring:message code="trip.comments"/></h2>

<display:table name="comments" id="row" pagesize="5" requestURI="${requestUri}" class="displaytag">
	
	<spring:message code="comment.title" var="title" />
	<display:column title="${title}">
	<jstl:choose>
	<jstl:when test="${row.getAppropriated()==true }">
	<jstl:out value="${row.getTitle() }" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="comment.inAppropriate"/>
	</jstl:otherwise>
	</jstl:choose>
	</display:column>
	
	<spring:message code="comment.actor" var="actor" />
	<display:column title="${actor}">
		<jstl:out value="${row.getActor().getName()}"/>
		<jstl:out value="${row.getActor().getSurname() }"/>
	</display:column>

	<spring:message code="comment.display" var="display" />
		<display:column title="${display}">
		<input type="button" value="<spring:message code="comment.display" />" 
			onclick="javascript: window.location.assign('comment/display.do?commentId=${row.id}')" />			
	</display:column>
	
</display:table>
<br>
<security:authorize access="isAuthenticated()">
	<input type="button" value="<spring:message code="comment.create" />" 
			onclick="javascript: window.location.assign('comment/actor/edit.do?commentableId=${trip.id}')" />
</security:authorize>


<input type="button" name="cancel" value="<spring:message code="trip.cancel"/>" onclick="javascript: window.history.back()" />
