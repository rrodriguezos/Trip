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

	<display:column>
	  <spring:message code="trip.comment" var="comments" />
      <a href="comment/list.do?id=<jstl:out value="${row.id}"/>">
        <spring:message code="trip.comment" />
      </a>
    </display:column>

	<display:column>
		<jstl:set var="contains" value="false" />

		<jstl:if test="${userTrips.size() > 0 }">
			<jstl:forEach var="item" items="${userTrips}">
				<jstl:if test="${item.id eq row.id}">
					<jstl:set var="contains" value="true" />
				</jstl:if>
			</jstl:forEach>
		</jstl:if>



		<!-- JOIN BUTTON -->
		<jstl:if test="${contains == false}">
			<spring:message code="trip.join" var="join" />
			<a href="trip/user/joinTrip.do?tripId=${row.id }">
				<button class="btn btn-md btn-default col-xs-12">
					<jstl:out value="${join }"></jstl:out>
				</button>
			</a>
		</jstl:if>

		<!-- JOINED MESSAGE -->
		<jstl:if test="${contains == true}">
			<span class="col-xs-12 bg-success spm-event-joined text-center">
				<spring:message code="trip.joined" var="joined" /> <jstl:out
					value="${joined }"></jstl:out>
			</span>
		</jstl:if>



	</display:column>


	<jstl:if test="${showdisjoin == true}">
		<jstl:if test="${row.users.contains(principal)}">
			<display:column title="${disjoin }">
				<div class="col-xs-6 col-sm-4 col-md-3 spm-events-button">
					<!-- DISJOIN BUTTON -->
					<spring:message code="event.disjoin" var="disjoin" />
					<a href="event/user/disjoinEvent.do?eventId=${row.id }">
						<button class="btn btn-md btn-warning col-xs-12">
							<jstl:out value="${disjoin }"></jstl:out>
						</button>
					</a>
				</div>
			</display:column>
		</jstl:if>
	</jstl:if>

	<security:authorize access="hasRole('USER')">
		<display:column>

			<a href="trip/user/edit.do?tripId=${row.id}"> <spring:message
					code="trip.edit" />
			</a>

		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('USER')">

		<a href="trip/user/create.do"> <spring:message code="trip.create" />
		</a>

	</security:authorize>





</display:table>

