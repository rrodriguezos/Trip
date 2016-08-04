<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<display:table name="activities" id="row" pagesize="5" requestURI="${requestUri}" class="displaytag">
	
	<spring:message code="activity.title" var="title" />
	<display:column property="title" title="${title}" />
		
	<spring:message code="activity.description" var="description" />
	<display:column property="description" title="${description}" />
	
	<spring:message code="activity.display" var="display" />
	<display:column title="${display}">
			<input type="button" value="<spring:message code="activity.display" />" 
					onclick="javascript: window.location.assign('activity/display.do?activityId=${row.id}')" />
	</display:column>
	
</display:table>

<security:authorize access="hasRole('USER')">
<input type="button" name="create" value="<spring:message code="activity.create" />"
	 onclick="javascript: window.location.assign('activity/user/create.do')" />
</security:authorize>