<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${actionURI}" modelAttribute="${actor}" >  
	

	<jstl:if test="${administrator!=null}">
		<acme:textbox code="actor.name" 		 path="name"		  value="${administrator.name}" />
		<acme:textbox code="actor.surname" 		 path="surname"		  value="${administrator.surname}"/>
		<acme:textbox code="actor.phone" 		 path="phone"		  value="${administrator.phone}"/>
		<acme:textbox code="actor.emailAdress" 	 path="emailAdress"	  value="${administrator.emailAdress}"/>
	</jstl:if>
	
	<jstl:if test="${user!=null||userRegisterForm!=null}">
		<acme:textbox code="actor.name" 		 path="name"		  value="${customer.name}"/>
		<acme:textbox code="actor.surname" 		 path="surname"		  value="${customer.surname}"/>
		<acme:textbox code="actor.phone" 		 path="phone"		  value="${customer.phone}"/>
		<acme:textbox code="actor.emailAdress" 	 path="emailAdress"	  value="${administrator.emailAdress}"/>	

	</jstl:if>
	
	<acme:textbox  code="actor.username"	 path="username"	test="${userRegisterForm!=null}"/>
	<acme:password code="actor.password" 	 path="password"/>	
	
	<jstl:if test="${userRegisterForm!=null}">
	<acme:password code="actor.password" 	 path="repeatedPassword"/>	
	</jstl:if>
 
	
  	<acme:submit name="save" code="actor.save"/>
	<acme:cancel url="" code="actor.cancel"/>
	
</form:form>