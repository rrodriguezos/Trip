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

	<form:form action="${ requestUri }" modelAttribute="type">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="activities" />

		<acme:textbox code="type.name" path="name" />

		<acme:submit name="save" code="type.save" />
		<security:authorize access="hasRole('MANAGER')">
			<acme:cancel url="type/manager/list.do" code="type.cancel" />
		</security:authorize>
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<acme:cancel url="type/administrator/list.do" code="type.cancel" />
		</security:authorize>

	</form:form>