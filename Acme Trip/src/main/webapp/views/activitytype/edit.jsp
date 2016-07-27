
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('MANAGER')">
<form:form modelAttribute="activitytype" action="activitytype/manager/edit.do">
	<form:hidden path="id" />
	<form:hidden path="version" />	
	<form:hidden path="activities" />
	
	<table class="formTable">
		<tr><td><acme:textbox code="activitytype.name" path="name"/></td></tr>		
		<tr>
			
			<td>
				<acme:submit name="save" code="activitytype.save"/>
			</td>
		
			<td>
				<jstl:if test="${activitytype.id!=0}">
				  <input type="submit" name="delete" value="<spring:message code="activitytype.delete" />" 
					onclick="return confirm('<spring:message code="activitytype.confirm.delete" />')" />
				</jstl:if>
			</td>
			
			<td>
				<acme:cancel url="activitytype/manager/list.do" code="activitytype.cancel"/>
			</td>
		</tr>
	</table>
</form:form>
</security:authorize>