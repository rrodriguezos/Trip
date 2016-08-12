<%--

 * register.jsp

 *

 * Copyright (C) 2014 Universidad de Sevilla

 * 

 * The use of this project is hereby constrained to the conditions of the 

 * TDG Licence, a copy of which you may download from 

 * http://www.tdg-seville.info/License.html

 --%>



<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ADMINISTRATOR')">

	<form:form action="banner/administrator/selectTax.do"
		modelAttribute="banner">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="keyWords" />
		<form:hidden path="campaign" />
		<form:hidden path="chargeRecords" />
		<form:hidden path="photo" />
		<form:hidden path="display" />
		<form:hidden path="price" />
		<form:hidden path="maxTimesDisplayed" />

		<acme:select items="${taxs}" itemLabel="taxType" code="tax.type"
			path="tax" />
		<br>
		<input type="submit" name="save"
			value="<spring:message code="campaign.save" />" />

		<acme:cancel url="banner/administrator/list.do"
			code="administrator.cancel" />

	</form:form>

</security:authorize>