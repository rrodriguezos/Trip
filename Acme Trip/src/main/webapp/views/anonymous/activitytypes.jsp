
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


<!-- Listing activitytype -->


<display:table name="activitytypes" id="row" class="displaytag"
	requestURI="${requestURI}" pagesize="5" keepStatus="true">


	<spring:message code="activitytype.name" var="title" />
	<display:column property="name" title="${title}" sortable="true" />

	<spring:message code="activitytype.activities" var="activities" />
	<display:column title="${activities}" sortable="true">
		<a
			href="activity/navigateByActivitytype.do?activitytypeId=<jstl:out value="${row.id}"/> "><spring:message
				code="activitytype.activities" /></a>
	</display:column>




</display:table>
