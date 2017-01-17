<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp" %>

<h3>List of Users</h3>
<table class="table table-bordered table-hover table-striped">
	<thead>
		<tr>
			<th>user name</th>
			<th>Email Address</th>
			<th>operations</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${users}" var="user">
			<tr>
				<td>
					<a href="<spring:url value="/users/${user.userId}.html" />">
						<c:out value="${user.userName}" />
					</a>
				</td>
				<td>
					<c:out value="${user.email}" />
				</td>
				<td>
					<a href="<spring:url value="/users/remove/${user.userId}.html" />" class="btn btn-danger triggerRemove">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
						Delete User
					</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<br />

<div class="modal fade" id="modalRemove" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
    	<div class="modal-content">
    		<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        		<h4 class="modal-title" id="myModalLabel">Remove Select User</h4>
      		</div>
      		<div class="modal-body">
        		Are you sure you want to delete the selected user?
      		</div>
      		<div class="modal-footer">
        		<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        		<a href="" class="btn btn-danger removeBtn">Delete</a>
    		</div>
    	</div>
	</div>
</div>

<script>
$(document).ready(function() {
	$(".triggerRemove").click(function(e) {
		e.preventDefault();
		$("#modalRemove .removeBtn").attr("href", $(this).attr("href"));
		$("#modalRemove").modal();
	});
});
</script>
