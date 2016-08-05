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

<security:authorize access="hasRole('USER')">

	<form:form action="user/edit.do" modelAttribute="userRegisterForm"
		cssClass="form-horizontal">

		<form:hidden path="id" />
		<form:hidden path="accept" />

		<table class="table-responsive">
			<tr>
				<td><acme:textbox code="user.name" path="name" /></td>
			</tr>
			<tr>
				<td><acme:textbox code="user.surname" path="surname" /></td>
			</tr>
			<tr>
				<td><acme:textbox code="user.phone" path="phone" /></td>
			</tr>

			<tr>
				<td><acme:textbox code="user.emailAddress" path="emailAddress" /></td>
			</tr>
			<tr>
				<td><acme:textbox code="user.username" path="username"
						readonly="true" /></td>
			</tr>
			<tr>
				<td><acme:password code="user.passwordPast"
						path="passwordPast" /></td>
			</tr>
			<tr>
				<td><acme:password code="user.password" path="password" /></td>
			</tr>
			<tr>
				<td><acme:password code="user.confirmPassword"
						path="confirmPassword" /></td>
			</tr>
			
			<tr>
				<td><acme:submit name="save" code="user.save" /> <acme:cancel
						url="welcome/index.do" code="user.cancel" /></td>
			</tr>
		</table>

	</form:form>

</security:authorize>