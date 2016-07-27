<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>


<form:form action="folder/edit.do" modelAttribute="folderForm" >

	<acme:textbox code="folder.create" path="name" value="${folderName}"/>	
	<acme:submit name="save" code="folder.save" />
	<acme:cancel url="" code="folder.cancel"/>
	
</form:form>