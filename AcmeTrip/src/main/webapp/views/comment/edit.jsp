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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${actionUri }" modelAttribute="comment">
	<form:hidden path="id"/>
	<form:hidden path="version" />
	<form:hidden path="actor"/>
	<form:hidden path="commentable"/>
	<form:hidden path="moment"/>
	<form:hidden path="appropriated"/>
	
	<acme:textbox code="comment.title" path="title"/>
	
	<acme:textarea code="comment.text" path="text"/>
	
	<input type="submit" name="save" value="<spring:message code="comment.save" />" />
	
	<input type="button" name="cancel" value="<spring:message code="comment.cancel" />" onclick="javascript: window.history.back()" />
	
</form:form>
