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

  <acme:textbox code="actor.name" path="name" value="${user.name}"/>
  <acme:textbox code="actor.surname" path="surname" value="${user.surname}" />
  <acme:textbox code="actor.phone" path="phone" value="${user.phone}"/>
  <acme:textbox code="actor.emailAdress" path="emailAdress" value="${user.emailAdress}"/>
  
  <jstl:if test="${registerForm!=null}">
  <acme:textbox code="actor.username" path="username" />
  <acme:password code="actor.password" path="password"  />
  </jstl:if>
  
  <acme:password code="actor.confirmPassword" path="confirmPassword" />
  
  <acme:submit name="save" code="actor.save" />
  <acme:cancel url="${cancelURI}" code="actor.cancel" />

</form:form>