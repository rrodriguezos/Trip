
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<!-- The total number of users who have registered to the system -->
  <spring:message code="administrator.dashboard.1"/>
  <p><jstl:out value="${totalNumberUsers}"></jstl:out></p>

<br>

<!-- The total number trips that have been registered -->
  <spring:message code="administrator.dashboard.2"/>
  <p><jstl:out value="${totalTripsRegistered}"></jstl:out></p>

<br>

<!-- The average number of trips per user. -->
  <spring:message code="administrator.dashboard.3"/>
  <p><jstl:out value="${averageTripsPerUser}"></jstl:out></p>

<br>

<!-- Standard deviation number of trips per user. -->
  <spring:message code="administrator.dashboard.4"/>
  <p><jstl:out value="${desviationTripsUser}"></jstl:out></p>

<br>

<!-- The average number of daily plans per trip. -->
  <spring:message code="administrator.dashboard.5"/>
  <p><jstl:out value="${averageDailyplansPerTrip}"></jstl:out></p>

<br>

<!-- Standard deviation number of daily plans per trip. -->
  <spring:message code="administrator.dashboard.6"/>
  <p><jstl:out value="${desviationDailyplansTrip}"></jstl:out></p>

<br>

<!-- The users who have registered at least 80% the maximum number of trips that a user has registered. -->
  <spring:message code="administrator.dashboard.7"/>
  <br>
<display:table name="users80percent" id="row1" requestURI="dashboard/administrator/list.do" pagesize="5" class="displaytag" keepStatus="true">
  <spring:message code="administrator.name" var="name" />
  <display:column property="name" title="${name}" sortable="true" />
  <spring:message code="administrator.surname" var="surname" />
  <display:column property="surname" title="${surname}" sortable="true" /> 
  <spring:message code="administrator.phone" var="phone" />
  <display:column property="phone" title="${phone}" sortable="true" />
</display:table>

<br>

<br>

<%--<!-- The users who have been inactive for more than one year. -->
  <spring:message code="administrator.dashboard.8"/>
  <br>
<display:table name="usersInactive" id="row1" requestURI="dashboard/administrator/list.do" pagesize="5" class="displaytag" keepStatus="true">
  <spring:message code="user.name" var="name" />
  <display:column property="name" title="${name}" sortable="true" />
  <spring:message code="user.surname" var="surname" />
  <display:column property="surname" title="${surname}" sortable="true" /> 
  <spring:message code="user.phone" var="phone" />
  <display:column property="phone" title="${phone}" sortable="true" />
</display:table>

<br>

<!-- The minimum, the maximum, and the average number of campaigns per manager. -->
  <spring:message code="administrator.dashboard.9"/>
  <p><jstl:out value="${minimum}"></jstl:out></p>

<br>
  
  <spring:message code="administrator.dashboard.10"/>
  <p><jstl:out value="${maximum}"></jstl:out></p>

<br>
  
  <spring:message code="administrator.dashboard.11"/>
  <p><jstl:out value="${average}"></jstl:out></p>

<br>

<!-- The average amount of money that has been charged per campaign -->
  <spring:message code="administrator.dashboard.12"/>
  <p><jstl:out value="${averageAmount}"></jstl:out></p>

<br>

<!-- The average number of days that the campaigns last -->
  <spring:message code="administrator.dashboard.13"/>
  <p><jstl:out value="${averageCampaignsLast}"></jstl:out></p>

<br>

<!-- Standard deviation number of days that the campaigns last -->
  <spring:message code="administrator.dashboard.14"/>
  <p><jstl:out value="${standardDesviationCampaignsLast}"></jstl:out></p>

<br>

<!-- The managers who have registered more campaigns -->
  <spring:message code="administrator.dashboard.15"/>
  <br>
<display:table name="managersMoreCampaigns" id="row3" requestURI="dashboard/administrator/list.do" pagesize="5" class="displaytag" keepStatus="true">
  <spring:message code="user.name" var="name" />
  <display:column property="name" title="${name}" sortable="true" />
  <spring:message code="user.surname" var="surname" />
  <display:column property="surname" title="${surname}" sortable="true" /> 
  <spring:message code="user.phone" var="phone" />
  <display:column property="phone" title="${phone}" sortable="true" />
</display:table>

<br>

<!-- The banners in the active campaigns that have been displayed at least 10% more than the average -->
  <spring:message code="administrator.dashboard.16"/>
  <br>
<display:table name="banners10MoreAverage" id="row3" requestURI="dashboard/administrator/list.do" pagesize="5" class="displaytag" keepStatus="true">
  <spring:message code="banner.photo" var="photo" />
  <display:column property="photo" title="${photo}" sortable="true" /> 
</display:table>

<br>

<!-- The banners in the active campaigns that have been displayed at least 10% less than the average -->
  <spring:message code="administrator.dashboard.17"/>
  <br>
<display:table name="banners10LessAverage" id="row3" requestURI="dashboard/administrator/list.do" pagesize="5" class="displaytag" keepStatus="true">
  <spring:message code="banner.photo" var="photo" />
  <display:column property="photo" title="${photo}" sortable="true" /> 
</display:table>

<br>---%>





