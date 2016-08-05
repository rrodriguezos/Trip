<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<display:table name="activitytypes" id="row" pagesize="5" requestURI="${requestUri}" class="displaytag">
	
	<spring:message code="activitytype.name" var="name" />
	<display:column title="${name}" property="name" />
	
	<spring:message code="activitytype.edit" var="edit" />
	<display:column title="${edit}">
		<security:authorize access="hasRole('MANAGER')">
			<input type="button" value="<spring:message code="activitytype.edit" />" 
					onclick="javascript: window.location.assign('activitytype/manager/edit.do?activitytypeId=${row.id}')" />			
		</security:authorize>
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<input type="button" value="<spring:message code="activitytype.edit" />" 
					onclick="javascript: window.location.assign('activitytype/administrator/edit.do?activitytypeId=${row.id}')" />			
		</security:authorize>
	</display:column>	
	
	
</display:table>

<security:authorize access="hasRole('MANAGER')">
<input type="button" name="create" value="<spring:message code="activitytype.create" />"
	 onclick="javascript: window.location.assign('activitytype/manager/create.do')" />
</security:authorize>