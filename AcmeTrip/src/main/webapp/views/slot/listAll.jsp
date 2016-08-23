
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!-- Listing activity -->
<div>

	<display:table name="slots" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag" keepStatus="true">
		<!-- Attributes -->
		<spring:message code="slot.title" var="title" />
		<display:column property="title" title="${title}" sortable="true" />

		<spring:message code="slot.description" var="description" />
		<display:column property="description" title="${title}"
			sortable="true" />

		<spring:message code="slot.startTime" var="startTime" />
		<display:column property="startTime" title="${startTime}"
			sortable="true" />

		<spring:message code="slot.endTime" var="endTime" />
		<display:column property="endTime" title="${endTime}" sortable="true" />

		<spring:message code="slot.photos" var="photos" />
		<display:column title="${photos}" sortable="true">
			<jstl:forEach var="photo" items="${row.photos}">
				<img height="150px" src="<jstl:out value="${photo}" />">
			</jstl:forEach>
		</display:column>

		<spring:message code="slot.dailyplan" var="dailyplan" />
		<display:column title="${dailyplan}" sortable="true">
			<input type="button" value="<spring:message code="slot.dailyplan" />"
				onclick="javascript: window.location.assign('dailyPlan/navigateBySlot.do?slotId=${row.id}')" />
		</display:column>

	</display:table>



</div>

