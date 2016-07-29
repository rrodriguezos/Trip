<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<spring:message code="daily.creating" />

<form:form modelAttribute="dailyplan" action="dailyplan/user/edit.do">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="slots" />
	<table class="formTable">
		<tr>
			<td><acme:textbox code="dailyplan.title" path="title" /></td>
		</tr>
		<tr>
			<td><acme:textbox code="dailyplan.trip" path="trip" /></td>
		</tr>
		<tr>
			<td><acme:textarea code="dailyplan.description"
					path="description" /></td>
		</tr>
		<tr>
			<td><select NAME="weekDay">
					<OPTION VALUE="MONDAY">
						<spring:message code="common.lunes" />
					</OPTION>
					<OPTION VALUE="TUESDAY">
						<spring:message code="common.martes" />
					</OPTION>
					<OPTION VALUE="WEDNESDAY">
						<spring:message code="common.miercoles" />
					</OPTION>
					<OPTION VALUE="THURSDAY">
						<spring:message code="common.jueves" />
					</OPTION>
					<OPTION VALUE="FRIDAY">
						<spring:message code="common.viernes" />
					</OPTION>
					<OPTION VALUE="SATURDAY">
						<spring:message code="common.sabado" />
					</OPTION>
					<OPTION VALUE="SUNDAY">
						<spring:message code="common.domingo" />
					</OPTION>
			</select></td>
		</tr>
		<tr>
			<td><acme:textbox code="dailyplan.photos" path="photos" />
		<tr>
		<tr>
			<td><acme:submit name="save" code="common.save" /></td>
			<td><input type="submit" name="cancel"
				value="<spring:message code="common.cancel" />"
				onclick="return confirm('<spring:message code="trip.confirm.cancel" />')" />
			</td>
		</tr>
	</table>
</form:form>