
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


	<!-- Debo controlar que cuando sea inapropiado, no mostrar a los demas usuarios si no es el que creo la actividad -->

	<display:table name="activities" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag" keepStatus="true">
		<!-- Attributes -->
		<spring:message code="activity.title" var="title" />
		<display:column property="title" title="${title}" sortable="true" />

		<spring:message code="activity.description" var="description" />
		<display:column property="description" title="${title}"
			sortable="true" />


		<spring:message code="activity.photos" var="photos" />
		<display:column title="${photos}" sortable="true">
			<jstl:forEach var="photo" items="${row.photos}">
				<img height="150px" src="<jstl:out value="${photo}" />">
			</jstl:forEach>
		</display:column>

		<spring:message code="activity.slots" var="slots" />
		<display:column title="${slots}" sortable="true">
			<a
				href="slot/navigateByActivity.do?activityId=<jstl:out value="${row.id}"/> "><spring:message
					code="activity.slots" /></a>
		</display:column>

		<spring:message code="activity.comment" var="comments" />
		<display:column>
			<a href="comment/list.do?id=<jstl:out value="${row.id}"/>"> <spring:message
					code="activity.comment" />
			</a>
		</display:column>
	</display:table>



</div>

