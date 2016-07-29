<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<spring:message code="slot.creating" />

<form:form modelAttribute="slot" action="slot/user/edit.do">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="dailyplan" />
	<table class="formTable">
		<tr>
			<td><acme:textbox code="slot.title" path="title" /></td>
		</tr>
		<tr>
			<td><acme:textarea code="slot.description" path="description" /></td>
		</tr>
		<tr>
			<td><acme:textarea code="slot.startDate" path="startDate" /></td>
		</tr>
		<tr>
			<td><acme:textarea code="slot.endDate" path="endDate" /></td>
		</tr>
		<tr>
			<td><acme:textbox code="slot.photos" path="photos" />
		<tr>
		<tr><td><acme:select code="slot.activities" path="activities" items="${activities}" itemLabel="title" id="activities" /></td></tr>		
		<tr>
			<td><acme:submit name="save" code="common.save" /></td>
			<td><input type="submit" name="cancel" value="<spring:message code="common.delete" />" 
					onclick="return confirm('<spring:message code="slot.confirm.cancel" />')" />
			</td>
		</tr>
	</table>
</form:form>