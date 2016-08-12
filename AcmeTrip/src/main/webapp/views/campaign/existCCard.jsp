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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('MANAGER')">

	<form:form action="campaign/manager/existingCreditCard.do" modelAttribute="campaign">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="manager" />
		<form:hidden path="endMoment" />
		<form:hidden path="startMoment" />
		<form:hidden path="banners" />
		
		<acme:select items="${tarjetasPaUsar}" itemLabel="creditCardNumber"
					code="campaign.tarjetas" path="creditCard" /> 
		
		<input type="submit" name="save"
			value="<spring:message code="campaign.save" />" />
		
		
		<acme:cancel url="campaign/manager/list.do" code="campaign.cancel" />

	</form:form>

</security:authorize>