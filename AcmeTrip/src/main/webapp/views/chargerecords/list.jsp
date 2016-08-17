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

<security:authorize access="hasRole('MANAGER')">

	<display:table name="chargerecords" id="row" pagesize="5"
		requestURI="${requestUri}" class="displaytag">

		<spring:message code="chargerecord.createMoment" var="createMoment"/>
		<display:column property="createMoment" sortable="true" title="${createMoment}"/>

		<spring:message code="chargerecord.amountMoney" var="amountMoney"/>
		<display:column property="amountMoney" sortable="true" title="${amountMoney}"/>

		<spring:message code="chargerecord.number" var="number"/>
		<display:column property="creditCard.creditCardNumber" title="${number}"/>

		<spring:message code="chargerecord.banner" />
		<display:column>
			<img width="200px" height="200x" src="${row.getBanner().getPhoto()}" />
		</display:column>



	</display:table>

	<a href="creditcard/manager/create.do"><spring:message
			code="master.page.manager.createCCard" /></a>

</security:authorize>