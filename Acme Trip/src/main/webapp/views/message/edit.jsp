<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
   
<form:form action="message/edit.do" modelAttribute="messageForm" >  

	<acme:select code="recipient.name" path="recipient" items="${actors}" itemLabel="name" id="actors" />
	<acme:textbox code="message.subject" path="subject"/>
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
	<acme:textarea code="message.body" path="body"/>

	<acme:submit name="save" code="message.save"/>

	<acme:cancel url="folder/list.do" code="message.cancel"/>

</form:form>