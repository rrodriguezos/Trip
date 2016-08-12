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

	<display:table name="taxs" id="row" pagesize="5"
		requestURI="tax/administrator/list.do" class="displaytag">

		<spring:message code="tax.type" var="taxType" />
		<display:column property="taxType" />

		<spring:message code="administrator.edit" var="edit" />
		<display:column>
			<input type="button"
				value="<spring:message code="administrator.edit" />"
				onclick="javascript: window.location.assign('tax/administrator/edit.do?taxId=${row.id}')" />
		</display:column>

	</display:table>


	<input type="button" name="create"
		value="<spring:message code="tax.create" />"
		onclick="javascript: window.location.assign('tax/administrator/create.do')" />
</security:authorize>