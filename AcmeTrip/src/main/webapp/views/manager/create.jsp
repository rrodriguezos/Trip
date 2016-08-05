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

	<form:form action="manager/administrator/create.do" modelAttribute="managerForm">


		<acme:textbox code="manager.username" path="username" />

		<acme:password code="manager.password" path="password" />
		<acme:password code="manager.confirmPassword" path="confirmPassword" />

		<acme:textbox code="manager.name" path="name" />

		<acme:textbox code="manager.surname" path="surname" />

		<acme:textbox code="manager.emailAddress" path="emailAddress" />

		<acme:textbox code="manager.phone" path="phone" />

		<acme:submit name="save" code="manager.save" />
		<acme:cancel url="manager/administrator/list.do" code="manager.cancel" />

	</form:form>

</security:authorize>