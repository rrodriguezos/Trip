<%--
 * header.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme Trip Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->

		<li><a class="fNiv" href="${pageContext.request.contextPath}">
				<spring:message code="master.page.home" />
		</a></li>

		<li><a href="trip/list.do"><spring:message
					code="master.page.public.catalogueTrip" /></a>
		<li><a class="fNiv" href="search/buscar.do"><spring:message
					code="master.page.search.trip" /></a></li>
		<li><a href="user/list.do"><spring:message
					code="master.page.public.usersList" /></a>
		<li><a href="activitytype/activitytypes.do"><spring:message
					code="master.page.public.activitytypeList" /></a> <security:authorize
				access="isAnonymous()">
				<li><a class="fNiv" href="user/register.do"> <spring:message
							code="master.page.register" /></a></li>
				<li><a class="fNiv" href="security/login.do"> <spring:message
							code="master.page.login" /></a></li>
			</security:authorize> <security:authorize access="hasRole('ADMINISTRATOR')">
				<li><a class="fNiv" href="dashboard/administrator/list.do">
						<spring:message code="master.page.administrator.dashboard" />
				</a></li>

			</security:authorize> <security:authorize access="isAuthenticated()">
				<li><a class="fNiv"> <spring:message
							code="master.page.profile" /> (<security:authentication
							property="principal.username" />)
				</a>
					<ul>
						<li class="arrow"></li>

						<security:authorize access="hasRole('USER')">
							<li><a href="user/edit.do"><spring:message
										code="master.page.user.profile" /></a></li>
							<li><a href="trip/user/mylist.do"><spring:message
										code="master.page.user.trips" /></a></li>
							<li><a href="trip/user/create.do"><spring:message
										code="master.page.user.createTrips" /></a></li>
							<li><a href="activity/user/create.do"><spring:message
										code="master.page.user.createActivities" /></a></li>
							<li><a href="trip/user/subscribed.do"><spring:message
										code="master.page.user.tripsSubscribed" /></a></li>
						</security:authorize>

						<security:authorize access="hasRole('MANAGER')">
							<li><a href="activitytype/manager/list.do"><spring:message
										code="master.page.manager.activitytpes" /></a></li>
							<li><a href="campaign/manager/list.do"><spring:message
										code="master.page.manager.listCamp" /></a></li>
							<li><a href="campaign/manager/create.do"><spring:message
										code="master.page.manager.createCamp" /></a></li>
							<li><a href="creditcard/manager/list.do"><spring:message
										code="master.page.manager.listCCard" /></a></li>
							<li><a href="creditcard/manager/create.do"><spring:message
										code="master.page.manager.createCCard" /></a></li>
							<li><a href="chargerecord/manager/list.do"><spring:message
										code="master.page.manager.charge" /></a></li>
						</security:authorize>

						<security:authorize access="hasRole('ADMINISTRATOR')">
							<li><a href="managger/administrator/list.do"><spring:message
										code="master.page.administrator.register.manager" /></a></li>
							<li><a href="administrator/administrator/list.do"><spring:message
										code="master.page.administrator.register.administrator" /></a></li>
							<li><a href="tax/administrator/list.do"><spring:message
										code="master.page.manager.tax" /></a></li>
							<li><a href="tax/administrator/create.do"><spring:message
										code="master.page.manager.createTax" /></a></li>
							<li><a href="banner/administrator/list.do"><spring:message
										code="master.page.manager.listBanner" /></a></li>
							<li><a href="chargerecord/administrator/generate.do"><spring:message
										code="master.page.manager.generate" /></a></li>			
						</security:authorize>
						<li><a href="folder/actor/list.do"> <spring:message
									code="master.page.user.mail" /></a></li>

					</ul></li>
				<li><a href="j_spring_security_logout"> <spring:message
							code="master.page.logout" /></a></li>
			</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

