<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp" %>


<form:form modelAttribute="user" cssClass="form-horizontal registrationForm">
	
	<h2 class="form-signin-heading">Registration Form</h2>
	<br />

	<div class="form-group">
		<label for="userName" class="col-sm-2 control-label">User Name:</label>
		<div class="col-sm-10">
			<form:input path="userName" cssClass="form-control" />
			<form:errors path="userName" style="color:red" />
		</div>
	</div>
	<div class="form-group">
		<label for="email" class="col-sm-2 control-label">Email Address:</label>
		<div class="col-sm-10">
			<form:input path="email" cssClass="form-control" />
			<form:errors path="email" style="color:red"/>
		</div>
	</div>
	<div class="form-group">
		<label for="password" class="col-sm-2 control-label">Password:</label>
		<div class="col-sm-10">
			<form:password path="password" cssClass="form-control" />
			<form:errors path="password" style="color:red"/>
		</div>
	</div>
	<div class="form-group">
		<label for="password" class="col-sm-2 control-label">Repeat Password:</label>
		<div class="col-sm-10">
			<input type="password" name="password_again" id="password_again" class="form-control" />
		</div>
	</div>
	<div class="form-group">
		<label for="submit" class="col-sm-2 control-label"></label>
		<div class="col-sm-10">
			<input id="submit" type="submit" value="Sign Up" class="btn btn-primary btn-default" />
		</div>
	</div>
</form:form>
<br />

<script>
$(document).ready(function() {	
	$(".registrationForm").validate({
			rules: {
				name: {
					required: true,
					minlength: 2,
					maxlength: 45,
					remote : {
						url: "<spring:url value='/register/available.html' />",
						type: "get",
						data: {
							username: function() {
								return $("#name").val();
							}
						}
					}
				},
				email: {
					required: true,
					email: true
				},
				password: {
					required: true,
					minlength: 3,
					maxlength: 60
				},
				password_again: {
					required: true,
					minlength: 3,
					maxlength: 60,
					equalTo: "#password"
				}
			},
			highlight: function(element) {
				$(element).closest('.form-group').removeClass('has-success').addClass('has-error');
			},
			unhighlight: function(element) {
				$(element).closest('.form-group').removeClass('has-error').addClass('has-success');
			},
			messages: {
				name: {
					remote: "Such username already exists"
				}
			}
		}
	);
});
</script>