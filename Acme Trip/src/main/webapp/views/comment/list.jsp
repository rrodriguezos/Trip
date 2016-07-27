<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Listing comments -->
<div class=center-text>
<display:table name="comments"  id="row" requestURI="${requestURI}" pagesize="5" class="displaytag" keepStatus="true" >

<!-- Attributes -->

  <spring:message code="comment.moment" var="momentHeader" />
  <display:column property="moment" title="${momentHeader}" sortable="true" format="{0, date, dd/MM/yyyy HH:mm}" />
  
  <spring:message code="comment.text" var="textHeader" />
  <display:column property="text" title="${textHeader}" sortable="true" />
  
   <spring:message code="comment.title" var="titleHeader" />
  <display:column property="title" title="${titleHeader}" sortable="true" />
  
  
  <spring:message code="comment.actor" var="actorHeader" />
  <display:column property="actor.userAccount.username" title="${actorHeader}" sortable="true" />
  
  <security:authorize access="hasRole('ADMIN')">
		<display:column>
				<a href='comment/administrator/mark.do?commentId=<jstl:out value="${row.id}"/>&id=<jstl:out value="${id}"/>'>
					<spring:message	code="comment.inappropriate" />
				</a>
		</display:column>
	</security:authorize>

</display:table>
</div>
 <security:authorize access="isAuthenticated()">
	<div>
  		<a href="comment/create.do?commentableId=<jstl:out value="${id}"/>"><spring:message code="comment.create" /></a>
	</div>
</security:authorize>