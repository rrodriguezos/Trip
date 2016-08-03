<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<!-- Listing messages -->
<h1> <jstl:out value="${folder.name}"/> </h1>

<display:table name="messages" id="row" pagesize="5" requestURI="${requestUri}" class="displaytag">

	<spring:message code="message.subject" var="subject" />
	<display:column property="subject" title="${subject}" sortable="true" />

	<spring:message code="message.body" var="body" />
	<display:column property="body" title="${body}" sortable="true" />

	<spring:message code="message.moment" var="moment" />
	<display:column property="moment" title="${moment}" sortable="true" />

	<spring:message code="message.sender" var="sender" />
	<display:column property="sender.name" title="${sender}"
		sortable="true" />

	<spring:message code="message.recipient" var="recipient" />
	<display:column property="recipient.name" title="${recipient}"
		sortable="true" />

	<spring:message code="message.messagePriority" var="messagePriority" />
	<display:column property="messagePriority" title="${messagePriority}"
		sortable="true" />

	<spring:message code="message.display" var="display" />
	<display:column title="${display}">
			<input type="button" value="<spring:message code="message.display" />" 
					onclick="javascript: window.location.assign('message/display.do?messageId=${row.id}')" />
	</display:column>
	
	<jstl:if test="${ !folder.name.equals('Spam folder') && !folder.name.equals('Trash folder') }">
	
	<jstl:if test="${ folder.name.equals('Starred folder') && folder.systemFolder }">
	
	<spring:message code="message.star" var="star" />
	<display:column title="${star}">
			<input type="button" value="<spring:message code="message.disstarredHeader" />" 
					onclick="javascript: window.location.assign('message/starred.do?messageId=${row.id}')" />
	</display:column>
	</jstl:if>
	
	</jstl:if>
	
	<jstl:if test="${ !folder.name.equals('Starred folder') }">
	<jstl:if test="${ !folder.name.equals('Spam folder') && !folder.name.equals('Trash folder')}">
	<spring:message code="message.flag" var="flag" />
	<display:column title="${spam}">
			<input type="button" value="<spring:message code="message.flagHeader" />" 
					onclick="javascript: window.location.assign('message/flag.do?messageId=${row.id}')" />
	</display:column>
	
	<spring:message code="message.star" var="star" />
	<display:column title="${star}">
			<input type="button" value="<spring:message code="message.starredHeader" />" 
					onclick="javascript: window.location.assign('message/starred.do?messageId=${row.id}')" />
	</display:column>
	</jstl:if>
	
	<spring:message code="message.move" var="move" />
	<display:column title="${move}">
			<input type="button" value="<spring:message code="message.move" />" 
					onclick="javascript: window.location.assign('message/move.do?messageId=${row.id}')" />
	</display:column>
	
	</jstl:if>
	
	<spring:message code="message.delete" var="delete" />
	<display:column title="${delete}">
		<a href="message/delete.do?messageId=${row.id}">
		<jstl:choose>
			<jstl:when test="${row.folder.name == \"Trash folder\"}">
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