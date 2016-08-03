
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


<display:table name="activities" id="row" class="displaytag"
	requestURI="${requestURI}" pagesize="5" keepStatus="true">

	<spring:message code="activity.title" var="title" />
	<display:column property="title" title="${title}" />

	<spring:message code="activity.description" var="description" />
	<display:column property="description" title="${description}" />

	<spring:message code="activity.display" var="display" />
	<display:column title="${display}">
		<input type="button"
			value="<spring:message code="activity.display" />"
			onclick="javascript: window.location.assign('activity/display.do?activityId=${row.id}')" />
	</display:column>

</display:table>

<security:authorize access="hasRole('USER')">
	<input type="button" name="create"
		value="<spring:message code="activity.create" />"
		onclick="javascript: window.location.assign('activity/user/create.do')" />
</security:authorize>





