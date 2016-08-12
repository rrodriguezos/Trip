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

	<form:form action="creditcard/manager/create.do"
		modelAttribute="creditcard">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="manager" />
		<form:hidden path="chargeRecords" />
		<form:hidden path="campaign" />
		<fieldset>
			<legend>
				<spring:message code="creditcard.creditCard" />
			</legend>
			<acme:textbox code="creditcard.holdername" path="holderName" />
			<acme:textbox code="creditcard.brandname" path="brandName" />
			<acme:textbox code="creditcard.number" path="creditCardNumber" />
			<acme:textbox code="creditcard.expirationMonth"
				path="expirationMonth" />
			<acme:textbox code="creditcard.expirationYear" path="expirationYear" />
			<acme:textbox code="creditcard.cvvCode" path="cvvCode" />
		</fieldset>

		<input type="submit" name="save"
			value="<spring:message code="creditcard.save" />" />

		<acme:cancel url="creditcard/manager/list.do" code="creditcard.cancel" />

	</form:form>

</security:authorize>