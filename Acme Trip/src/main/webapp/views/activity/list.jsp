
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!-- Listing activity -->
<div>		


		
		<display:table name="activities" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag" keepStatus="true">
				<!-- Attributes -->
				<spring:message code="activity.title" var="title" />
				<display:column property="title" title="${title}" sortable="true" />

				<spring:message code="activity.description" var="description" />
				<display:column property="description" title="${title}"
					sortable="true" />


				<spring:message code="activity.photos" var="photos" />
				<display:column title="${photos}" sortable="true">
					<jstl:forEach var="photo" items="${row.photos}">
						<img height="150px" src="<jstl:out value="${photo}" />">
					</jstl:forEach>
				</display:column>

				<spring:message code="activity.activityType" var="activityType" />
				<display:column title="${activityType}" sortable="true">
					<a
						href="activitytype/listByActivity.do?activityId=<jstl:out value="${row.id}"/> "><spring:message
							code="activity.activityType" /></a>
				</display:column>
				
				<display:column>
	  <spring:message code="activity.comment" var="comments" />
      <a href="comment/list.do?id=<jstl:out value="${row.id}"/>">
        <spring:message code="activity.comment" />
      </a>
    </display:column>
    
    <security:authorize access="hasRole('USER')">
      <display:column>
      <a href="activity/manager/edit.do?activityId=<jstl:out value="${row.id}"/> "><spring:message code="activity.edit"/></a>
  		</display:column>
  		
  		</security:authorize>
    
    <security:authorize access="hasRole('MANAGER')">

  <display:column>
    <jstl:if test="${row.isAppropiate == true}">
      <a href="activity/manager/mark.do?activityId=<jstl:out value="${row.id}"/> "><spring:message code="activity.markInnapropiate"/></a>
     </jstl:if>
  </display:column>
  </security:authorize>
				</display:table>

</div>

