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

<display:table name="users" id="row" pagesize="5" requestURI="${requestUri}" class="displaytag">
	
	<spring:message code="user.username" var="username" />
	<display:column title="${username}">
		<jstl:out value="${row.getUserAccount().getUsername() }" />
	</display:column>
	
	<spring:message code="user.name" var="name" />
	<display:column title="${name}" property="name" />
	
	<spring:message code="user.surname" var="surname" />
	<display:column title="${surname}" property="surname" />
	
	<spring:message code="user.phone" var="phone" />
	<display:column title="${phone}" property="phone" />
	
	<spring:message code="user.emailAddress" var="emailAddress" />
	<display:column title="${emailAddress}" property="emailAddress" />
	
	<spring:message code="user.display" var="display" />
	<display:column title="${display}">
			<input type="button" value="<spring:message code="user.display" />" 
					onclick="javascript: window.location.assign('user/display.do?userId=${row.id}')" />
	</display:column>
	
</display:table>