<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<!-- Listing folders -->

<security:authorize access="hasRole('USER') || hasRole('ADMIN')|| hasRole('MANAGER')">

<display:table name="folders" id="row" class="displaytag" requestURI="folder/list.do" pagesize="5" keepStatus="true" >
	
	<spring:message	code="folder.name"  var="name"/>
	<display:column title="${name}">
		<a href='message/list.do?folderId=<jstl:out value="${row.id}"/>'>
			<jstl:out value="${row.name}"/></a>
	</display:column>
	
	
		<spring:message	code="common.edit" var="edit"/>
		<display:column title="${edit}">
			<jstl:if test="${row.systemFolder==false}">
				<a href='folder/edit.do?folderId=<jstl:out value="${row.id}"/>'>
					<spring:message	code="common.edit" />
				</a>
			</jstl:if>
		</display:column>
	
</display:table>
		


<br/>


<a href="message/send.do"><spring:message code="message.send"/></a>
&nbsp;
&nbsp;
<a href="folder/edit.do"><spring:message code="folder.create"/></a>



</security:authorize>