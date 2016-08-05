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

	<form:form action="administrator/administrator/create.do" modelAttribute="administratorForm">


		<acme:textbox code="administrator.username" path="username" />

		<acme:password code="administrator.password" path="password" />
		<acme:password code="administrator.confirmPassword" path="confirmPassword" />

		<acme:textbox code="administrator.name" path="name" />

		<acme:textbox code="administrator.surname" path="surname" />

		<acme:textbox code="administrator.emailAddress" path="emailAddress" />

		<acme:textbox code="administrator.phone" path="phone" />

		<acme:submit name="save" code="administrator.save" />
		<acme:cancel url="administrator/administrator/list.do" code="administrator.cancel" />

	</form:form>

</security:authorize>