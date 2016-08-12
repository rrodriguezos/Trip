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

	<form:form action="campaign/manager/newCreditCard.do" modelAttribute="campaign">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="manager" />
		<form:hidden path="endMoment" />
		<form:hidden path="startMoment" />
		<form:hidden path="banners" />
		<form:hidden path="creditCard.chargeRecords" />
		<form:hidden path="creditCard.manager" />
		<fieldset>
			<legend>
				<spring:message code="campaign.creditCard" />
			</legend>
			<acme:textbox code="campaign.holdername" path="creditCard.holderName" />
			<acme:textbox code="campaign.brandname" path="creditCard.brandName" />
			<acme:textbox code="campaign.number" path="creditCard.creditCardNumber" />
			<acme:textbox code="campaign.expirationMonth"
				path="creditCard.expirationMonth" />
			<acme:textbox code="campaign.expirationYear"
				path="creditCard.expirationYear" />
			<acme:textbox code="campaign.cvvCode" path="creditCard.cvvCode" />
		</fieldset>

		<input type="submit" name="save"
			value="<spring:message code="campaign.save" />" />

		<acme:cancel url="campaign/manager/list.do" code="campaign.cancel" />

	</form:form>

</security:authorize>