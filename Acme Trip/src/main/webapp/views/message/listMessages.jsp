<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<!-- Listing messages -->

<security:authorize access="hasRole('CUSTOMER') || hasRole('ADMIN')|| hasRole('MANAGER')">

<display:table name="messages" id="row" class="displaytag" requestURI="message/list.do" pagesize="5" keepStatus="true" >
	
	<spring:message	code="message.subject"  var="subject"/>
	<display:column property="subject" title="${subject}" sortable="true" />
	
	<spring:message	code="message.body"  var="body"/>
	<display:column property="body" title="${body}" sortable="true" />
	
	<spring:message	code="message.moment"  var="moment"/>
	<display:column property="moment" title="${moment}" sortable="true" />
	
	<spring:message	code="message.messagePriority"  var="messagePriority"/>
	<display:column property="messagePriority" title="${messagePriority}" sortable="true" />
	
	<spring:message	code="common.manage" var="management"/>
	<display:column title="${management}">
			<a href='message/delete.do?messageId=<jstl:out value="${row.id}"/>'>
				<spring:message	code="common.delete" />
			</a>
	</display:column>
	
	
	</display:table>

	</security:authorize>