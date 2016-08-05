<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h1> <jstl:out value="${folder.name}"/> </h1>

<display:table name="messages" id="row" pagesize="5" requestURI="${requestUri}" class="displaytag">
	
	<spring:message code="message.subject" var="subject" />
	<display:column title="${subject}" property="subject" />
	
	<spring:message code="message.moment" var="moment" />
	<display:column title="${moment}" >
		<fmt:formatDate value="${row.getMoment()}" pattern="dd/MM/yyyy HH:mm" />
	</display:column>	
	
	<spring:message code="message.sender" var="sender" />
	<display:column title="${sender}" property="sender.name" />
	
	<spring:message code="message.messagePriority" var="messagePriority" />
	<display:column title="${messagePriority}">
		<spring:message code="message.${ row.messagePriority }"/>
	</display:column>
	
	<spring:message code="message.display" var="display" />
	<display:column title="${display}">
			<input type="button" value="<spring:message code="message.display" />" 
					onclick="javascript: window.location.assign('message/actor/display.do?messageId=${row.id}')" />
	</display:column>
	
	<jstl:if test="${ !folder.name.equals('Spamfolder') && !folder.name.equals('Trashfolder') }">
	
	<jstl:if test="${ folder.name.equals('Starredfolder') && folder.systemFolder }">
	
	<spring:message code="message.star" var="star" />
	<display:column title="${star}">
			<input type="button" value="<spring:message code="message.starTrue" />" 
					onclick="javascript: window.location.assign('message/actor/favorite.do?messageId=${row.id}')" />
	</display:column>
	</jstl:if>
	
	</jstl:if>
	
	<jstl:if test="${ !folder.name.equals('Starredfolder') }">
	<jstl:if test="${ !folder.name.equals('Spamfolder') && !folder.name.equals('Trashfolder')}">
	<spring:message code="message.spam" var="spam" />
	<display:column title="${spam}">
			<input type="button" value="<spring:message code="message.addSpam" />" 
					onclick="javascript: window.location.assign('message/actor/tospam.do?messageId=${row.id}')" />
	</display:column>
	
	<spring:message code="message.star" var="star" />
	<display:column title="${star}">
			<input type="button" value="<spring:message code="message.starFalse" />" 
					onclick="javascript: window.location.assign('message/actor/favorite.do?messageId=${row.id}')" />
	</display:column>
	</jstl:if>
	
	<spring:message code="message.move" var="move" />
	<display:column title="${move}">
			<input type="button" value="<spring:message code="message.move" />" 
					onclick="javascript: window.location.assign('message/actor/move.do?messageId=${row.id}')" />
	</display:column>
	
	</jstl:if>
	
	<spring:message code="message.delete" var="delete" />
	<display:column title="${delete}">
		<a href="message/actor/delete.do?messageId=${row.id}">
		<jstl:choose>
			<jstl:when test="${row.folder.name == \"Trashfolder\"}">
				<input type="button" value="<spring:message code="message.delete" />" 
				onclick="return confirm('<spring:message code="message.confirm.delete.final" />')" />
			</jstl:when>
			<jstl:otherwise>
				<input type="button" value="<spring:message code="message.delete" />" 
				onclick="return confirm('<spring:message code="message.confirm.delete" />')" />
			</jstl:otherwise>	
		</jstl:choose>
		
		</a>	
	</display:column>
	
</display:table>
<br/>

<input type="button" value="<spring:message code="message.back" />" 
onclick="javascript: window.location.assign('folder/actor/list.do')" />
