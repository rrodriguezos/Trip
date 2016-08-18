<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<security:authorize access="hasRole('ADMINISTRATOR')">

<display:table name="managers" id="row" pagesize="5" requestURI="manager/administrator/list.do" class="displaytag">
	
	<spring:message code="manager.username" var="username" />
	<display:column title="${username}">
		<jstl:out value="${row.getUserAccount().getUsername() }" />
	</display:column>
	
	<spring:message code="manager.name" var="name" />
	<display:column title="${name}" property="name" />
	
	<spring:message code="manager.surname" var="surname" />
	<display:column title="${surname}" property="surname" />
	
	<spring:message code="manager.display" var="display" />
	<display:column title="${display}">
			<input type="button" value="<spring:message code="manager.display" />" 
					onclick="javascript: window.location.assign('manager/administrator/display.do?managerId=${row.id}')" />
	</display:column>
	
</display:table>


<input type="button" name="create" value="<spring:message code="manager.create" />"
	 onclick="javascript: window.location.assign('manager/administrator/create.do')" />
</security:authorize>