<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<!-- Listing trips -->

<display:table name="trips" id="row" class="displaytag" requestURI="${requestURI}" pagesize="5" keepStatus="true">
	
	
	<spring:message	code="trip.title"  var="title"/>
	<display:column property="title" title="${title}" sortable="true" />
	
	<spring:message	code="trip.description"  var="description"/>
	<display:column property="description" title="${title}" sortable="true" />
	
	<spring:message	code="trip.startDate"  var="startDate"/>
	<display:column property="startDate" title="${startDate}" sortable="true"  format="{0, date, dd/MM/yyyy HH:mm}" />
	
	<spring:message	code="trip.endDate"  var="endDate"/>
	<display:column property="endDate" title="${endDate}" sortable="true"  format="{0, date, dd/MM/yyyy HH:mm}" />
	
  	
  	<spring:message	code="trip.photos"  var="photos"/>	
	<display:column title="${photos}" sortable="true" >
	<jstl:forEach var="photo" items="${row.photos}">		
		<img height="150px" src="<jstl:out value="${photo}" />">
	</jstl:forEach>
	</display:column>
	
	<display:column>
      <a href="comment/list.do?id=<jstl:out value="${row.id}"/>">
        <spring:message code="trip.comment" />
      </a>
    </display:column>
    
   <spring:message	code="trip.dailyplans"  var="dailyplans"/>
	<display:column title="${dailyplans}" sortable="true">
      <a href="dailyplan/listByTrip.do?tripId=<jstl:out value="${row.id}"/> "><spring:message code="trip.dailyplans"/></a>
  	</display:column>	
  	
	


</display:table>

