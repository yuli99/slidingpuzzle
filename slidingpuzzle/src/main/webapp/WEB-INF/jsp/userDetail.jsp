<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp" %>

<h2>User: <c:out value="${user.userName}" /></h2>
<h3>Play records</h3>
<table class="table table-bordered table-hover table-striped">
	<thead>
		<tr>
			<th>Date of Record</th>
			<th>Puzzle Size</th>
			<th>Score</th>
			<th>Operation</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${user.records}" var="record">
			<tr>
				<td>
					<c:out value="${record.dateOfRecord}" />
				</td>
				<td>
					<c:out value="${record.puzzleSize}" /> -by- <c:out value="${record.puzzleSize}" /> tiles
				</td>
				<td>
					<c:out value="${record.score}" /> / 100
				</td>
				<td>
					<a href="<spring:url value="/records/remove/${record.recordId}.html" />" class="btn btn-danger triggerRemove">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
						Delete Record
					</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<br />

<h3>Uploaded images</h3>
<table class="table table-bordered table-hover table-striped">
	<thead>
		<tr>
			<th>Uploaded Date</th>
			<th>Image Name</th>
			<th>operations</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${user.images}" var="image">
			<tr>
				<td>
					<c:out value="${image.uploadDate}" />
				</td>
				<td>
					<c:out value="${image.imageUri}" />
				</td>
				<td>
					<a href="<spring:url value="/images/remove/${image.imageId}.html" />" class="btn btn-danger triggerRemove">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
						Delete Image
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
        		<h4 class="modal-title" id="myModalLabel">Remove Select Item</h4>
      		</div>
      		<div class="modal-body">
        		Are you sure you want to delete the selected item?
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
