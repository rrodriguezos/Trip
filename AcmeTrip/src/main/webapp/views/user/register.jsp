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

<security:authorize access="isAnonymous()">

	<form:form action="user/register.do" modelAttribute="userForm">


		<acme:textbox code="user.username" path="username" />

		<acme:password code="user.password" path="password" />
		<acme:password code="user.passwordRepeat" path="passwordRepeat" />

		<acme:textbox code="user.name" path="name" />

		<acme:textbox code="user.surname" path="surname" />

		<acme:textbox code="user.email" path="email" />

		<acme:textbox code="user.phone" path="phone" />

		<acme:checkbox code="user.accept" path="accept"
			url="welcome/conditions.do" />

		<acme:submit name="save" code="user.save" />
		<acme:cancel url="welcome/index.do" code="user.cancel" />

	</form:form>

</security:authorize>