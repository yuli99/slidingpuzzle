<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp"%>

<div class="jumbotron">
    <img src="<c:url value="/resources/images/logo.png" />" class="img-responsive" alt="logo-image" height="150">
    <br /><br />
    <p>Register and play the n-puzzle game with us today!<p>
	<p>Enjoy three levels(3x3 tiles, 4x4 tiles, 5x5 tiles) and two modes(number and image) of the game!</p>
	<p>Upload and customize puzzle image, track play scores, and find optimal puzzle solution!</p>	
</div>

<div class="row featurette">
	<div class="col-md-7">
		<div class="col-lg-4">
			<h4>Level: easy</h4>
			<img src="<c:url value="/resources/images/8-puzzle.png" />" alt="8-puzzle" class="img-responsive">
	        <h4>3x3 tiles (8-puzzle)</h4>
	        <p><a class="btn btn-primary" href="<spring:url value="/account/play.html?size=3" />" role="button">Start to play &raquo;</a></p>
        </div>
		<div class="col-lg-4">
			<h4>Level: medium</h4>
			<img src="<c:url value="/resources/images/15-puzzle.png" />" alt="15-puzzle" class="img-responsive">
	        <h4>4x4 tiles (15-puzzle)</h4>
	        <p><a class="btn btn-primary" href="<spring:url value="/account/play.html?size=4" />" role="button">Start to play &raquo;</a></p>
        </div>
		<div class="col-lg-4">
			<h4>Level: hard</h4>
			<img src="<c:url value="/resources/images/24-puzzle.png" />" alt="24-puzzle" class="img-responsive">
	        <h4>5x5 tiles (24-puzzle)</h4>
	        <p><a class="btn btn-primary" href="<spring:url value="/account/play.html?size=5" />" role="button">Start to play &raquo;</a></p>
        </div>
    </div>
    <div class="col-md-5">
        <img src="<c:url value="/resources/images/gameDemo.gif" />" alt="game demo" class="img-responsive">
    </div>
</div>

<div class="note">
	<h4>Notes:</h4>
	<ol>
		<li>We ensure that every new generated puzzle is solvable. The optimal solution of the puzzle is obtained by A* search algorithm.</li>
		<li>The resolution of your uploaded images should be larger than 600 x 600. We auto-resize it to a square shape using seam caver algorithm.</li>
		<li>A score will display after you win the game. It is calculated as the ratio of the required minimum moves over your total moves.</li>
	</ol>
</div>