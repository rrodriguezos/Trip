
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
   
<form:form action="message/edit.do" modelAttribute="messageForm" >  

	<table class="formTable">
		<tr><td><acme:select code="recipient.name" path="recipient" items="${actors}" itemLabel="name" id="actors" /></td></tr>
		<tr><td><acme:textbox code="message.subject" path="subject"/></td></tr>
		<tr><td><acme:textarea code="message.body" path="body"/></td></tr>
		
			<tr>
			<td>
				<acme:submit name="save" code="common.save"/>
			</td>
			
			<td>
				<acme:cancel url="folder/list.do" code="common.cancel"/>
			</td>
		</tr>
	</table>
</form:form>