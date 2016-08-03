
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<jstl:choose>
	<jstl:when test="${activity.id==0}">
		<p>
			<spring:message code="activity.creating" />
	</jstl:when>
	<jstl:otherwise>
		<p>
			<spring:message code="activity.updating" />
		</p>
	</jstl:otherwise>
</jstl:choose>



<security:authorize access="hasRole('USER')">
	<form:form modelAttribute="activity" action="activity/user/edit.do">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="isAppropiate" />
		<form:hidden path="user" />
		<form:hidden path="slots" />
		<form:hidden path="comments" />
		<form:hidden path="manager" />

		<tr>
			<td><acme:textbox code="activity.title" path="title" />
		<tr>
			<td><br>
		<tr>
			<td><acme:textbox code="activity.description" path="description" />
		<tr>
			<td><br>
		<tr>
			<td><acme:textarea code="activity.photos" path="photos" />
		<tr>
			<td><br> <spring:message code="activity.activitytype" />
		<tr>
			<td><form:select path="activityType">
					<form:options items="${activitytypes}" itemLabel="name"
						itemValue="id" />
				</form:select> <form:errors cssClass="error" path="activityType" /> <br> <input
				type="submit" name="save"
				value="<spring:message code="activity.save" />" /> <acme:cancel
					url="activity/list.do" code="activity.cancel" />
	</form:form>
</security:authorize>