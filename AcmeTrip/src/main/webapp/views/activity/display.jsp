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


<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${activity.getAppropriated()==true }">
		<input type="button" value="<spring:message code="activity.isInappropriated" />" 
			onclick="javascript: window.location.assign('activity/manager/appropriated.do?activityId=${activity.id}')" />			
	</jstl:if>
	<jstl:if test="${activity.getAppropriated()==false }">
		<input type="button" value="<spring:message code="activity.isAppropriated" />" 
			onclick="javascript: window.location.assign('activity/manager/appropriated.do?activityId=${activity.id}')" />			
	</jstl:if>
</security:authorize>
<br>

<acme:jstlOut code="activity.title" value="${activity.title }"/>
<acme:jstlOut code="activity.description" value="${activity.description }"/>
<b><spring:message code="activity.photos" />: </b>
<br>
<jstl:forEach var="photo" items="${activity.getPhotos() }">
	<img width="200px" height="200x" src="${photo}" alt="${ activity.title } photo"/>
</jstl:forEach>
<br>

<h2><spring:message code="activity.trips"/></h2>
<display:table name="trips" id="row" pagesize="5" requestURI="user/display.do" class="displaytag">
	
	<spring:message code="trip.title" var="title" />
	<display:column title="${title}" property="title" />
	
	<spring:message code="trip.display" var="display" />
	<display:column title="${display}">
			<input type="button" value="<spring:message code="trip.display" />" 
					onclick="javascript: window.location.assign('trip/display.do?tripId=${row.id}')" />
	</display:column>
	
</display:table>

<h2><spring:message code="activity.comments"/></h2>

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

<security:authorize access="isAuthenticated()">
	<jstl:if test="${activity.getAppropriated()==true }">
	<input type="button" value="<spring:message code="comment.create" />" 
			onclick="javascript: window.location.assign('comment/actor/edit.do?commentableId=${activity.id}')" />
	</jstl:if>
</security:authorize>

<input type="button" name="cancel" value="<spring:message code="activity.cancel"/>" onclick="javascript: window.location.assign('activity/list.do')" />
