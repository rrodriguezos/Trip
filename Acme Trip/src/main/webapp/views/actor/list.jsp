<%--
 * list.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<display:table name="${actor}" id="row" class="displaytag" requestURI="${requestURI}" pagesize="5" keepStatus="true" >

	
	<spring:message	code="actor.name"  var="name"/>
	<display:column property="name" title="${name}" sortable="true" />
	
	<spring:message	code="actor.surname"  var="surname"/>
	<display:column property="surname" title="${surname}" sortable="true" />
	
	<spring:message	code="actor.phone"  var="phone"/>
	<display:column property="phone" title="${phone}" sortable="true" />
	
	<spring:message	code="actor.emailAddress"  var="emailAddress"/>
	<display:column property="emailAddress" title="${emailAddress}" sortable="true" />	
	
	<spring:message	code="actor.trips" var="trips"/>
	<display:column title="${trips}">
		<a href="trip/list.do?userId=<jstl:out value='${row.id}'/>">
			<spring:message	code="actor.see" />
		</a>
	</display:column>
	
	<spring:message code="actor.display" var="display" />
	<display:column title="${display}">
			<input type="button" value="<spring:message code="actor.displayIn" />" 
					onclick="javascript: window.location.assign('user/display.do?userId=${row.id}')" />
	</display:column>
	
	<jstl:if test="${requestURI=='user/user/list.do'}">
	<spring:message	code="actor.edit" var="edit"/>
	<display:column title="${edit}">
		<a href="user/user/edit.do">
			<spring:message	code="actor.edit" />
		</a>
	</display:column>
	</jstl:if>	

	
</display:table>

	
	
