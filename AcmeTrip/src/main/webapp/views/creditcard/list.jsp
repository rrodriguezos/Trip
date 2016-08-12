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

	<display:table name="creditcards" id="row" pagesize="5"
		requestURI="${requestUri}" class="displaytag">

		<spring:message code="creditcard.holdername" var="holderName" />
		<display:column property="holderName" sortable="true" />

		<spring:message code="creditcard.brandname" var="brandName" />
		<display:column property="brandName" sortable="true" />

		<spring:message code="creditcard.number" var="creditCardNumber" />
		<display:column property="creditCardNumber" />

		<spring:message code="creditcard.expirationMonth"
			var="expirationMonth" />
		<display:column property="expirationMonth" />

		<spring:message code="creditcard.expirationYear" var="expirationYear" />
		<display:column property="expirationYear" />

		<spring:message code="creditcard.delete" />
		<display:column>
			<jstl:if test="${row.campaign==null}">
				<input type="button"
					value="<spring:message code="creditcard.delete" />"
					onclick="javascript: window.location.assign('creditcard/manager/delete.do?creditCardId=${row.id}')" />
			</jstl:if>
			<jstl:if test="${row.campaign!=null}">
			<<spring:message code="creditcard.cant.delete" />"
				</jstl:if>
		</display:column>

	</display:table>

	<a href="creditcard/manager/create.do"><spring:message
			code="master.page.manager.createCCard" /></a>

</security:authorize>