
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!-- Listing activitytype -->

<div class=center-text>
<display:table name="activitytypes"  id="row" requestURI="${requestURI}" pagesize="5" class="displaytag" keepStatus="true" >

<!-- Attributes -->

  <spring:message code="activitytype.name" var="nameHeader" />
  <display:column property="name" title="${nameHeader}" sortable="true" />

  
  <security:authorize access="hasRole('MANAGER')">
    <display:column>
      <a href='activitytype/manager/edit.do?activitytypeId=<jstl:out value="${row.id}"/>'>
        <spring:message code="activitytype.edit" />
      </a>
    </display:column>
  </security:authorize>

</display:table>
</div>

<security:authorize access="hasRole('MANAGER')">
  <div>
     <a href='activitytype/manager/create.do'>
        <spring:message code="activitytype.create" />
      </a>
  </div>
</security:authorize>