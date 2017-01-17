<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="taglib.jsp"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
  		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico" />" >
		
		<link rel="stylesheet" href="<c:url value="/resources/style.css" />" >
        
		<title><tiles:getAsString name="title" /></title>

		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">

		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>

		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>	
	</head>

	<body>
		<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx"%>
		<tilesx:useAttribute name="current" />
		
		<nav class="navbar navbar-inverse navbar-fixed-top">
      		<div class="container">
        		<div class="navbar-header">
          			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" 
          				aria-expanded="false" aria-controls="navbar">
            			<span class="sr-only">Toggle navigation</span>
            			<span class="icon-bar"></span>
            			<span class="icon-bar"></span>
            			<span class="icon-bar"></span>
          			</button>
          			<a class="navbar-brand" href="<spring:url value="/" />">Sliding Puzzle</a>
       		 	</div>
        		<div id="navbar" class="navbar-collapse collapse">
          			<ul class="nav navbar-nav">
            			<li class="${current == 'index' ? 'active' : ''}"><a href="<spring:url value="/" />">Home</a></li>
            			<li class="${current == 'about' ? 'active' : ''}"><a href="<spring:url value="/about.html" />">About</a></li>
            			<li class="${current == 'contact' ? 'active' : ''}"><a href="<spring:url value="/contact.html" />">Contact</a></li>
            			<li class="dropdown">
              				<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" 
              					aria-expanded="false">Play <span class="caret"></span></a>
		              		<ul class="dropdown-menu">
		                		<li><a href="<spring:url value="/account/play.html?size=3" />">3x3 Easy</a></li>
		                		<li><a href="<spring:url value="/account/play.html?size=4" />">4x4 Medium</a></li>
		                		<li><a href="<spring:url value="/account/play.html?size=5" />">5x5 Hard</a></li>
		              		</ul>
            			</li>
          			</ul>
          			<ul class="nav navbar-nav navbar-right">
          				<security:authorize access="hasRole('ROLE_ADMIN')">
            				<li class="${current == 'users' ? 'active' : ''}"><a href="<spring:url value="/users.html" />">Users</a></li>
            			</security:authorize>
            			<security:authorize access="! isAuthenticated()">
          					<li class="${current == 'register' ? 'active' : ''}"><a href="<spring:url value="/register.html" />">Register</a></li>
	             			<li class="${current == 'login' ? 'active' : ''}"><a href="<spring:url value="/login.html" />">Login</a></li>
              			</security:authorize>
              			<security:authorize access="isAuthenticated()">
              				<li class="${current == 'account' ? 'active' : ''}"><a href="<spring:url value="/account.html" />">My account</a></li>
              				<li>
              					<c:url var="logoutUrl" value="/logout" />
              					<form action="${logoutUrl}" id="logout" method="post" ></form>
								<a href="#" onclick="document.getElementById('logout').submit();">Logout</a>
              				</li>
              			</security:authorize>
          			</ul>
        		</div>
      		</div>
    	</nav>
    	
    	<div class="container">		
			<tiles:insertAttribute name="body" />
			<hr>
			<tiles:insertAttribute name="footer" />
		</div>
	</body>
</html>