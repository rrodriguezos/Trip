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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table>
	<tr>
		<th colspan=2><spring:message code="message.subject" />: <jstl:out value="${message.getSubject()}" /></th>
	</tr>
	<tr>
		<td>
			<ul>
				<li><b><spring:message code="message.sender" />: </b>
					<p>
						<jstl:out value="${message.getSender().getName()}" />
					</p></li>
				<li><b><spring:message code="message.recipient" />: </b>
					<p>
						<jstl:out value="${message.getRecipient().getName()}" />
					</p></li>
				<li><b><spring:message code="message.moment" />: </b>
					<p>
						<fmt:formatDate value="${message.getMoment()}" pattern="dd/MM/yyyy HH:mm" />
					</p></li>
			</ul>
		</td>
		<td>
			<ul>
				<li><b><spring:message code="message.body" />: </b>
					<p>
						<jstl:out value="${message.getBody()}" />
					</p></li>
			</ul>
		</td>
	</tr>
</table>