<%--
 * header.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme Trip Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		
		<li><a class="fNiv" href="${pageContext.request.contextPath}">
			<spring:message code="master.page.home" /></a>
		</li>		
		
		<li><a href="trip/list.do"><spring:message code="master.page.public.tripList" /></a>
		<li><a href="users/list.do"><spring:message code="master.page.public.usersList" /></a>
		<li><a href="activitytype/list.do"><spring:message code="master.page.public.activitytypeList" /></a>		
		
		
	<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="user/register.do">
				<spring:message code="master.page.register" /></a>
			</li>
			<li><a class="fNiv" href="security/login.do">
				<spring:message code="master.page.login" /></a>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('ADMIN')">
		  <li><a class="fNiv" href="dashboard/administrator/list.do">
			<spring:message code="master.page.administrator.dashboard" /></a>
		  </li>
		</security:authorize>
		
		
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> 
				<spring:message code="master.page.profile" />
			    (<security:authentication property="principal.username" />)	</a>
				<ul><li class="arrow"></li>
				
					<security:authorize access="hasRole('USER')">
					<li>
						<a href="user/user/list.do"><spring:message code="master.page.user.profile" /></a>
					</li>
					<li>
						<a href="trip/user/list.do"><spring:message code="master.page.user.trips" /></a>
					</li>
					</security:authorize>
					
					<security:authorize access="hasRole('MANAGER')">
					<li>
						<a href="activity/manager/list.do"><spring:message code="master.page.manager.activities" /></a>
					</li>
					</security:authorize>
					
					<security:authorize access="hasRole('ADMIN')">
					<li>
						<a href="manager/administrator/register.do"><spring:message code="master.page.administrator.register.manager" /></a>
					</li>
					<li>
						<a href="administrator/administrator/register.do"><spring:message code="master.page.administrator.register.administrator" /></a>
					</li>
					<li>
						<a href="comment/administrator/list.do"><spring:message code="master.page.administrator.comments" /></a>
					</li>
					</security:authorize>
				
					<li><a href="folder/list.do">					
						<spring:message code="master.page.user.mail" /></a>
					</li>				
					<li><a href="j_spring_security_logout">
						<spring:message code="master.page.logout" /></a>
					</li>
					
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

