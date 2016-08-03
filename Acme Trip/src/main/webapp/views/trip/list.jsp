<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<!-- Listing trips -->

<display:table name="trips" id="row" class="displaytag"
	requestURI="${requestURI}" pagesize="5" keepStatus="true">

	<spring:message code="trip.title" var="title" />
	<display:column property="title" title="${title}" sortable="true" />

	<jstl:if test="${requestURI != 'trip/user/list.do'}">
		<spring:message code="trip.number-of" var="number" />
		<display:column title="${number}" sortable="true">
			<jstl:out value="${numbers[row_rowNum-1]}" />
		</display:column>
	</jstl:if>

	<spring:message code="trip.description" var="description" />
	<display:column property="description" title="${description}"
		sortable="true" />



	<spring:message code="trip.startDate" var="startDate" />
	<display:column property="startDate" title="${startDate}"
		sortable="true" />

	<spring:message code="trip.endDate" var="endDate" />
	<display:column property="endDate" title="${endDate}" sortable="true" />

	<spring:message code="trip.photos" var="photos" />
	<display:column title="${photos}" sortable="true">
		<jstl:forEach var="photo" items="${row.photos}">
			<img height="150px" src="<jstl:out value="${photo}" />">
		</jstl:forEach>
	</display:column>
	
	<spring:message code="trip.display" var="display" />
	<display:column title="${display}">
			<input type="button" value="<spring:message code="trip.display" />" 
					onclick="javascript: window.location.assign('trip/display.do?tripId=${row.id}')" />
	</display:column>
	
	<spring:message code="trip.dailyplans" var="dailyplansHeader" />
	<display:column title="${dailyplansHeader}">
			<input type="button" value="<spring:message code="trip.dailyplans" />" 
					onclick="javascript: window.location.assign('dailyplan/list.do?tripId=${row.id}')" />
	</display:column>	


	<security:authorize access="hasRole('USER')">
		<display:column>

			<a href="trip/user/edit.do?tripId=${row.id}"> <spring:message
					code="trip.edit" />
			</a>

		</display:column>
	</security:authorize>
	
	<spring:message code="trip.comment" var="comments" />
	<display:column title="${comments}">
			<input type="button" value="<spring:message code="trip.comment" />" 
					onclick="javascript: window.location.assign('comment/list.do?id=<jstl:out value="${row.id}"/>')" />
	</display:column>	
	

	<security:authorize access="hasRole('USER')">

		<a href="trip/user/create.do"> <spring:message code="trip.create" />
		</a>

	</security:authorize>





</display:table>

