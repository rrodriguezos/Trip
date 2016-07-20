<%--
 * register.jsp
 *
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<display:table name="${actor}" id="row" class="displaytag" requestURI="${requestURI}" pagesize="5" keepStatus="true" >
	
	<spring:message	code="actor.name"  var="name"/>
	<display:column property="name" title="${name}" sortable="true" />
	
	<spring:message	code="actor.surname"  var="surname"/>
	<display:column property="surname" title="${surname}" sortable="true" />
	
	<spring:message	code="actor.phone"  var="phone"/>
	<display:column property="phone" title="${phone}" sortable="true" />
	
	<spring:message	code="actor.emailAdress"  var="emailAdress"/>
	<display:column property="emailAdress" title="${emailAdress}" sortable="true" />	

	
		<spring:message	code="actor.manage" var="management"/>
		<display:column title="${management}">
				<a href="${editURI}">
					<spring:message	code="actor.edit" />
				</a>
		</display:column>
	
	
	</display:table>
