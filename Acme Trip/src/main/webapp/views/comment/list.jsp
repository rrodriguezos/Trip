<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Listing grid -->
<div class=center-text>
<display:table name="comments"  id="row" requestURI="${requestURI}" pagesize="5" class="displaytag" keepStatus="true" >

<!-- Attributes -->

  <spring:message code="comment.moment" var="momentHeader" />
  <display:column property="moment" title="${momentHeader}" sortable="true" format="{0, date, dd/MM/yyyy HH:mm}" />
  
  <spring:message code="comment.title" var="titleHeader" />
  <display:column property="text" title="${textHeader}" sortable="true" />
  
    <spring:message code="comment.text" var="textHeader" />
  <display:column property="text" title="${textHeader}" sortable="true" />
  
  <spring:message code="comment.actor" var="actorHeader" />
  <display:column property="actor.userAccount.username" title="${actorHeader}" sortable="true" />
  <security:authorize access="hasRole('ADMIN')">
  <spring:message code="comment.markStatus" var="markStatus" />
	<display:column title="${markStatus}">
			<input type="button" value="<spring:message code="comment.mark" />" 
					onclick="javascript: window.location.assign('comment/administrator/mark.do?commentId=<jstl:out value="${row.id}"/>&id=<jstl:out value="${id}"/>')" />
	</display:column>
	</security:authorize>  


</display:table>
</div>
 <security:authorize access="isAuthenticated()">
	<div>
  		<a href="comment/create.do?commentableId=<jstl:out value="${id}"/>"><spring:message code="comment.create" /></a>
	</div>
</security:authorize>