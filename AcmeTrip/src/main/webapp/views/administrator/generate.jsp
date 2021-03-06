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

	<jstl:if test="${fallo==true}">
		<spring:message code="administrator.falloGenerate" />
		<input type="button"
				value="<spring:message code="master.page.manager.generate" />"
				onclick="javascript: window.location.assign('chargerecord/administrator/generate.do')" />
	</jstl:if>
	<jstl:if test="${fallo==false}">
		<spring:message code="administrator.aciertoGenerate" />
	</jstl:if>

</security:authorize>