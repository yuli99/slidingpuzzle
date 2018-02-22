<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp" %>

<h2>Welcome, <c:out value="${user.userName}" />!</h2>

<h3>Uploaded images</h3>
<div>
<button class="btn btn-success" data-toggle="modal" data-target="#modalUpload">
	<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
	New image
</button>
</div>
<br />

<ul class="nav nav-tabs">
	<c:forEach items="${user.images}" var="image">
	  <li><a href="#image_${image.imageId}" data-toggle="tab"><c:out value="${image.imageUri}" /></a>
	  </li>
	</c:forEach>
</ul>

<div class="tab-content">
	<c:forEach items="${user.images}" var="image">
		<div class="tab-pane" id="image_${image.imageId}">
			<br />
			<!--  if images were uploaded to the project resources file, use: 
			<img src="<c:url value="/resources/puzzleimages/${image.imageUri}" />" alt="image" style="width:20%"/>
			-->
			<!-- images were stored in local file system -->
			<img src="file:///F:/Yuli_Java/MyProjects/puzzle_images/${image.imageUri}" alt="image" style="width:20%" />
			<ul class="list-group">
			  	<li class="list-group-item">image uploaded at <c:out value="${image.uploadDate}" /></li>
			  	<li class="list-group-item">
			  		<a href="<spring:url value="/images/remove/${image.imageId}.html" />" class="btn btn-danger triggerRemove">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
						delete image
					</a>
			  	</li>
			</ul>
		</div>	
	</c:forEach>
</div>
<br />

<h3>Play records</h3>
<table class="table table-bordered table-hover table-striped">
	<thead>
		<tr>
			<th>Date of Record</th>
			<th>Puzzle Size</th>
			<th>Score</th>
			<th>Operations</th>
		</tr>
  	</thead>
	<tbody>
		<c:forEach items="${user.records}" var="record">
			<tr>
				<td>
					<c:out value="${record.dateOfRecord}" />
				</td>
				<td>
					<c:out value="${record.puzzleSize}" />-by-<c:out value="${record.puzzleSize}" /> tiles
				</td>
				<td>
					<c:out value="${record.score}" /> / 100
				</td>
				<td>
					<button type="button" class="btn btn-info triggerPreviewBoard" data-container="body" data-toggle="popover" 
				  		data-placement="right" data-trigger="focus" data-content="${record.initBoard}">
				  		<span class="glyphicon glyphicon-picture" aria-hidden="true"></span>
						view puzzle board
					</button>
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

<div class="modal fade uploadform in" id="modalUpload" role="dialog" aria-hidden="false">
	<form id="form-uploadimage" action='<spring:url value="/account.html" />' method="POST" enctype="multipart/form-data">
		<div class="modal-dialog">
    		<div class="modal-content">
		        <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			        <h4 class="modal-title">New Image to Upload</h4>
		        </div>
		        <div class="modal-body">
		        	<label class="btn btn-primary" for="imageFile">
					    <input id="imageFile" name="imageFile" type="file" style="display:none;" size="40"  onchange='$("#imageInfo").html($(this).val());'>
					     Choose Image File...
					</label>
	        		<span class='label label-info' id="imageInfo"></span>
		        </div>
		        <br />
		        <div class="modal-footer">
		        	<button type="button" class="btn btn-close" data-dismiss="modal">Cancel</button>
		        	<button type="submit" class="btn btn-primary">Upload</button>
		        </div>
		    </div>
	    </div>
	</form>
</div>


<div class="modal fade" id="modalRemove" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel" aria-hidden="true">
	<div class="modal-dialog">
    	<div class="modal-content">
    		<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        		<h4 class="modal-title" id="myModalLabel">Remove Select Image</h4>
      		</div>
      		<div class="modal-body">
        		Are you sure you want to delete the selected image?
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
	
	$('.nav-tabs a:first').tab('show');
	
	$(".triggerRemove").click(function(e) {
		e.preventDefault();
		$("#modalRemove .removeBtn").attr("href", $(this).attr("href"));
		$("#modalRemove").modal();
	});
	
	$(".triggerPreviewBoard").popover();
});
</script>

