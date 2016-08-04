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

<display:table name="types" id="row" pagesize="5" requestURI="${requestUri}" class="displaytag">
	
	<spring:message code="type.name" var="name" />
	<display:column title="${name}" property="name" />
	
	<spring:message code="type.edit" var="edit" />
	<display:column title="${edit}">
		<security:authorize access="hasRole('MANAGER')">
			<input type="button" value="<spring:message code="type.edit" />" 
					onclick="javascript: window.location.assign('type/manager/edit.do?typeId=${row.id}')" />			
		</security:authorize>
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<input type="button" value="<spring:message code="type.edit" />" 
					onclick="javascript: window.location.assign('type/administrator/edit.do?typeId=${row.id}')" />			
		</security:authorize>
	</display:column>	
	
	
</display:table>

<security:authorize access="hasRole('MANAGER')">
<input type="button" name="create" value="<spring:message code="type.create" />"
	 onclick="javascript: window.location.assign('type/manager/create.do')" />
</security:authorize>
<security:authorize access="hasRole('ADMINISTRATOR')">
<input type="button" name="create" value="<spring:message code="type.create" />"
	 onclick="javascript: window.location.assign('type/administrator/create.do')" />
</security:authorize>