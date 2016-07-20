
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jstl:choose>
	<jstl:when test="${trip.id==0}"><p><spring:message code="trip.creating" /></p></jstl:when>
	<jstl:otherwise><p><spring:message code="trip.updating" /></p></jstl:otherwise>
</jstl:choose>

<form:form modelAttribute="trip" action="trip/user/edit.do">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="comments" />
	
	<table class="formTable">
		<tr><td><acme:textbox code="trip.title" path="title"/></td></tr>
		<tr><td><acme:textarea code="trip.description" path="description"/></td></tr>
		<tr><td><acme:textbox code="trip.startDate" path="startDate" /></td></tr>
		<tr><td><acme:textbox code="trip.endDate" path="endDate" /></td></tr>
		<tr><td><acme:textbox code="trip.photos" path="photos"/><tr><td>

		<tr><td><acme:select code="trip.dailyplans" path="dailyplans" items="${dailyplans}" itemLabel="title" id="dailyplans" /></td></tr>
		
		<tr>
			
			<td>
				<acme:submit name="save" code="common.save"/>
			</td>
		
			<td>
				<jstl:if test="${trip.id!=0}">
				  <input type="submit" name="delete" value="<spring:message code="common.delete" />" 
					onclick="return confirm('<spring:message code="trip.confirm.delete" />')" />
				</jstl:if>
			</td>
			
			<td>
				<acme:cancel url="trip/list.do" code="common.cancel"/>
			</td>
		</tr>
	</table>
</form:form>