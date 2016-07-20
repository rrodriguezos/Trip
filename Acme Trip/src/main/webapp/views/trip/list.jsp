<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<!-- Listing trips -->

<form action="trip/searchResult.do" method="get">
	<table class="formTable">
		<tr>
			<td><label for="q"><spring:message	code="service.searchtext" />:</label></td>
			<td><input type="text" name="q"></td>
		</tr>
		
		<tr>
			<td><input type="submit" value="<spring:message	code="common.search" />"></td>
		</tr>
	</table>
</form>

<jstl:if test="${q != null}">
  <h2><spring:message	code="search.title" />: <jstl:out value="${q}"/></h2>
</jstl:if>

<display:table name="trips" id="row" class="displaytag" requestURI="${requestURI}" pagesize="5" keepStatus="true" >
	
	<spring:message	code="trip.title"  var="title"/>
	<display:column property="title" title="${title}" sortable="true" />
	
	<jstl:if test="${requestURI != 'trip/user/list.do'}">
	<spring:message	code="trip.number-of"  var="number"/>
	<display:column title="${number}" sortable="true">
				<jstl:out value="${numbers[row_rowNum-1]}"/>
	</display:column>
	</jstl:if>
	
	<spring:message	code="trip.description"  var="description"/>
	<display:column property="description" title="${description}" sortable="true" />
	
	<spring:message	code="trip.dailyplans"  var="dailyplans"/>
	<display:column title="${dailyplans}">
		<a href="trip/tripDetails.do?tripId=<jstl:out value="${row.id}"/>">
			<jstl:out value="${dailyplans}"/>
			</a>
	</display:column>
	
	<spring:message	code="trip.startDate"  var="startDate"/>
	<display:column property="startDate" title="${startDate}" sortable="true" />
	
	<spring:message	code="trip.endDate"  var="endDate"/>
	<display:column property="endDate" title="${endDate}" sortable="true" />
	
	<spring:message	code="trip.photos"  var="photos"/>
	<display:column title="${photos}">
		<a href="trip/tripDetails.do?tripId=<jstl:out value="${row.id}"/>">
			<jstl:out value="${photos}"/>
			</a>
	</display:column>	

	
	<display:column>
      <a href="comment/list.do?id=<jstl:out value="${row.id}"/>">
        <spring:message code="trip.comment" />
      </a>
    </display:column>

	<security:authorize access="hasRole('USER')">
		<spring:message	code="common.manage" var="management"/>
		<display:column title="${management}">
				<a href="trip/user/edit.do?tripId=<jstl:out value="${row.id}"/>">
					<spring:message	code="common.edit" />
				</a>
		</display:column>
		
  <div>
    <a href="trip/user/create.do"><spring:message code="trip.create" /></a>
  </div>

		
		
	</security:authorize>	
	
	
	</display:table>

