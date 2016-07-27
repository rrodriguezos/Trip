
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form modelAttribute="${modelAttribute}" action="${actionURI}">
	
	<security:authorize access="hasRole('MANAGER')">
	
	  <form:hidden path="id" />
	  <form:hidden path="version" />
	  <form:hidden path="photos" />
	  <form:hidden path="description" />
	  <form:hidden path="title" />

	  
	  <acme:select items="${activities}" itemLabel="title" code="activitiy.activities" path="activities"/>
	  
	  <acme:submit name="save" code="activity.save"/>
	  <acme:cancel url="activity/manager/list.do" code="activity.markInnapropiate"/>
		
	</security:authorize>
	
	<security:authorize access="hasRole('USER')">
	
	  <tr><td><acme:textbox code="activity.title" path="title"/><tr><td>
	  <tr><td><acme:textbox code="activity.description" path="description"/><tr><td>
	  <tr><td><acme:textbox code="activity.photos" path="photos"/><tr><td>
	  

			
	  <acme:submit name="save" code="activity.save"/>
	  <acme:cancel url="activity/list.do" code="activity.cancel"/>

	</security:authorize>
</form:form>