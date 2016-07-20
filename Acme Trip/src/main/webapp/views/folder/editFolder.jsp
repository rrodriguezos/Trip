
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<jstl:choose>
	<jstl:when test="${folder.id==0}"><p><spring:message code="folder.creating" /></p></jstl:when>
	<jstl:otherwise><p><spring:message code="folder.updating" /></p></jstl:otherwise>
</jstl:choose>

<form:form modelAttribute="folder">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="systemFolder" value="false" />
	<form:hidden path="actor" />
	<form:hidden path="messages" />
	
	<table class="formTable">
		<tr><td><acme:textbox code="folder.name" path="name"/></td></tr>
		
		<tr>
			
			<td>
				<input type="submit" name="save" value="<spring:message code="common.save" />" />
			</td>
		
			<td>
				<jstl:if test="${folder.id!=0}">
					<input type="submit" name="delete" value="<spring:message code="common.delete" />" 
					onclick="return confirm('<spring:message code="folder.confirm.delete" />')" />
				</jstl:if>
			</td>
			
			<td>
				<acme:cancel url="folder/list.do" code="common.cancel"/>
			</td>
		</tr>
	</table>
</form:form>