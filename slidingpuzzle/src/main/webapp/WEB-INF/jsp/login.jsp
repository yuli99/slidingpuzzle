<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp" %>

<div class="container">
	<c:url var="loginUrl" value="/login" />
	<form class="form-signin" role="form" action="${loginUrl}" method="POST">
	  	<c:if test="${not empty msg}">
	  		<div class="alert alert-info" role="alert">
	  			<span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>
  				<span class="sr-only">Success:</span>
	  			${msg}
	  		</div>
	    </c:if>
	    <c:if test="${not empty error}">
	    	<div class="alert alert-danger" role="alert">
	    		<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  				<span class="sr-only">Error:</span>
	    		${error}
	    	</div>
	    </c:if>        
	            	   	
		<h2 class="form-signin-heading">Please sign in</h2>
		<br />
		<label class="sr-only" for="username">Username</label>
		<input type="text" class="form-control" id="username" name="userName" placeholder="Enter Username" required>
		<br />
		<label class="sr-only" for="password">Password</label> 
		<input type="password" class="form-control" id="password" name="password" placeholder="Enter Password" required>
		<br />
		<button type="submit" class="btn btn-block btn-primary">Sign in</button>
	</form>
</div>

