<%--
 * create.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
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

<form:form action="message/create.do" modelAttribute="message">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sender" />
	<form:hidden path="folder" />
	<form:hidden path="moment" />
	<form:hidden path="star" />

	<acme:textbox code="message.subject" path="subject" />

	<acme:textarea code="message.body" path="body" />

	<spring:message code="message.recipient" />
	<form:select path="recipient">
		<form:option label="----" value="0" />
		<form:options items="${actors}" itemLabel="name" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="recipient" />
	<br>

	<tr>
		<td><select NAME="messagePriority">
				<OPTION VALUE="LOW">
					<spring:message code="common.bajo" />
				</OPTION>
				<OPTION VALUE="NEUTRAL">
					<spring:message code="common.neutral" />
				</OPTION>
				<OPTION VALUE="HIGH">
					<spring:message code="common.alto" />
				</OPTION>
		</select></td>
	</tr>

	<acme:submit name="save" code="message.save" />

	<jstl:if test="${message.id!=0 }">
		<input type="submit" name="delete"
			value="<spring:message code="message.delete"/>"
			onclick="return confirm('<spring:message code="message.confirm.delete" />')" />
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="message.cancel" />" />

</form:form>
