<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/taglib.jsp" %>

<h2>Welcome, ${record.user.userName}!</h2>

<div class="container">
  	<div class="row">
  		<div class="col-md-7">
  			<div id="gameObject"></div>
		</div>
		<div class="col-md-3">
			<div class="gamePanel">	
				<label for="imageSelector">Select Mode:</label>
				<select class="form-control" id="imageSelector">
					<c:forEach items="${images}" var="image">
						<option value="${image}">${image}</option>
					</c:forEach>
				</select>
				<br />
				<button id="newGame" type="button" class="btn btn-primary">New Puzzle</button>
			</div>	
			<div class="gamePanel2">
				Number of moves:
				<span id="moves">0</span>			
			</div>
			<div class="gamePanel2">
		    	<button id="restart" type="button" class="btn btn-primary" disabled>Restart Puzzle</button>
		    </div>
		    <div>
		    	<c:url var="solutionUrl" value="/account/solution.html" />
		    	<form role="form" action="${solutionUrl}" method="POST">
         			<input type="hidden" name="size" value="${record.puzzleSize}" />
         			<input id="setBoard" type="hidden" name="board" />
         			<input id="setMode" type="hidden" name="mode" />
	        		<button id="solution" type="submit" class="btn btn-primary" disabled>View Solution &raquo;</button>
				</form>
			</div>
		</div>	
	</div>
	<div class="note">
		<h4>Notes:</h4>
		<ol>
			<li>Press "New Puzzle" to shuffle the tiles and start a game, "Restart" and "View Solution" button enabled after the game starts.</li>
			<li>Use the drop-down list of "Select Mode" to change the background image before or during the game.</li>
			<li>When reaching the goal board, a modal auto appears to show the score, and you can choose whether to save the play record or not</li>
		</ol>
	</div>
</div>

<div class="modal fade" id="modalWin" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
    	<div class="modal-content">
      		<div class="modal-header">
        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        		<h4 class="modal-title">You Win</h4>
      		</div>
	      	<div class="modal-body">
	        	Congratulations for solving the puzzle!
	        	<br />
	        	You score: 
	        	<span id="score">0</span>
	      	</div>
	      	<div class="modal-footer">
	        	<form:form modelAttribute="record" method="POST">
	        		<form:input type="hidden" path="puzzleSize" value="${record.puzzleSize}"/>
	        		<form:input id="setScore" type="hidden" path="score" />
	        		<form:input id="setBoard2" type="hidden" path="initBoard" />
	        		<button type="button" class="btn btn-default" data-dismiss="modal">Back to Game</button>
	        		<button type="submit" class="btn btn-primary">Save Play Record</button>
	        	</form:form>
	      	</div>
    	</div>
  	</div>
</div>

<script>
$(document).ready(function() {
	// size of the puzzle: n = 3, 4, or 5
	var size = ${record.puzzleSize};

	// pixels of the game board
	var boardSize = 600;

	// empty space of the board (gray)
	var emptyTile = size * size;

	// pixels of each tile
	var tileSize = boardSize / size;

	// set goal array
	var goalBoard = [];
	for (var i = 1; i < size * size; i++) {
		goalBoard.push(i);
	}
	goalBoard.push(0);
	
	// set shuffle times
	var shuffledTimes = size * size * size;
	
	var initBoard = goalBoard;	
	var image = $("#imageSelector").val();
	var started = false;
	var shuffling = false;
	var minMoves = 0;

    $.fn.extend({ n_puzzle:
    	function() {
	    	// set game board
	    	$("#gameObject").html('<div id="board"></div>');
	
	    	$("#board").css({ 
		        position:"absolute", 
		        width: boardSize + "px", 
		        height: boardSize + "px", 
		        border: "2px solid gray"
		    });
	    		
	        initPuzzle(image);
	        	
	        $("#newGame").click(function() {
	        	started = true;
	        	resetBoard(goalBoard);
	        	minMoves = 0;
	        	shuffle();
	        	resetMoves();
	        	//$("#imageSelector").prop("disabled", true);
	        	$("#restart").removeAttr("disabled");
	        	$("#solution").removeAttr("disabled");
	        	// console.log(initBoard.join());
	        	var boardStr = initBoard.join();
	        	$("#setBoard").val(boardStr);
	        	$("#setBoard2").val(boardStr);
	        });
	        	 
	        $("#restart").click(function() {
	         	resetBoard(initBoard);
	         	resetMoves();
	         	started = true; 
	         	$("#solution").removeAttr("disabled");
	        }); 
	        	
	        // attach click event for each tile
	        $("#board").children("div").click(function() {
	        	if (started) {
	            	moveTile(this);
	            	checkWin();
	        	}
	        });
	
	        // use image selector to change puzzle image
	        $("#imageSelector").on("change", function() {
	            image = $(this).val();
	            resetImage(image);
	            $("#setMode").val($(this).val());
	        });
	            
	        // close modal win
	        $(".close").click(function () {
	            $("#modalWin").modal("hide");
	        });
        }
    });
    
 	// create the tiles using the same image but different left offset and top offset
    function initPuzzle(selectedImage) {
    	// create all tiles
    	for (var j = 0; j < emptyTile - 1; j++) {
    		tile = $("<div><br /></div>");
    		var xPos = (j % size) * tileSize,
			    yPos = Math.floor(j / size) * tileSize;
    		
    		var xImagePos = -(j % size) * tileSize,
    			yImagePos = -Math.floor(j / size) * tileSize;
    	
	        tile.css({
	     		display: "block",
	     		float: "left",
	     		cursor: "pointer",
	     		// backgroundImage: "url(../resources/puzzleimages/" + selectedImage + ")",
	     		backgroundImage: "url('file:///F:/Yuli_Java/MyProjects/puzzle_images/" + selectedImage + "')",
	     		backgroundRepeat: "no-repeat",
	     		backgroundPosition: (xImagePos + "px " + yImagePos + "px"),
	     		position: "absolute",
	     		left: xPos + "px",
	     		top: yPos + "px",
	     		mozUserSelect: "none",
	     		webkitUserSelect: "none",
	     		khtmlUserSelect: "none",
	     		userSelect: "none",
	     		border: "1px solid gray"
	     	}).width(tileSize).height(tileSize);
    		
            $("#board").append(tile);
    	}
    	
		// set empty space transparent
		space = $("<div><br /></div>");
		space.css({
	 		display: "block",
	 		float: "left",
	 		cursor: "pointer",
	 		background: "transparent",
	 		position: "absolute",
	 		left: ((emptyTile - 1) % size) * tileSize + "px",
     		top: Math.floor((emptyTile - 1) / size) * tileSize + "px",
	 		mozUserSelect: "none",
	 		webkitUserSelect: "none",
	 		khtmlUserSelect: "none",
	 		userSelect: "none"
	 	}).width(tileSize).height(tileSize);
		
		$("#board").append(space);
		
        // initial moves and puzzle image
        resetMoves();
        $("#setMode").val($("#imageSelector").val());
    }
 	
 	function resetImage(newImage) {
 		for (var n = 1; n < emptyTile; n++) {
 			//$("#board").children("div:nth-child(" + n + ")").css("backgroundImage", "url(../resources/puzzleimages/" + newImage + ")");
 			$("#board").children("div:nth-child(" + n + ")").css("backgroundImage", "url('file:///F:/Yuli_Java/MyProjects/puzzle_images/" + newImage + "')");
 		}
 	}
    
    function shuffle() {
    	// shuffle tiles
    	shuffling = true;
   
    	for (var i = 0; i < shuffledTimes; i++) {
    		var random = Math.floor(Math.random() * (emptyTile - 1) + 1);
    		// console.log(random);
    		moveTile($("#board div:nth-child(" + random + ")"));
    	}
    	
    	// get current board 
    	for (var n = 1; n <= emptyTile; n++) {
    	    var xPos = $("#board").children("div:nth-child(" + n + ")").css("left");
    	    var yPos = $("#board").children("div:nth-child(" + n + ")").css("top");
    		 
    	    var i = (parseInt(yPos) / tileSize) * size + (parseInt(xPos) / tileSize);
    	    
    	    initBoard[i] = (n == emptyTile) ? 0 : n;
    	}
    	
    	shuffling = false;
    	// console.log(initBoard.join(", "));
    }
    
    function resetBoard(board) {
    	for (var j = 0; j < emptyTile; j++) {
    		
    		var xNew = (j % size) * tileSize,
	    		yNew = Math.floor(j / size) * tileSize;
    		
    		if (board[j] == 0) {
    			$("#board").children("div:nth-child(" + emptyTile + ")").css("left", xNew);
    			$("#board").children("div:nth-child(" + emptyTile + ")").css("top", yNew);
    		}
    		else {	
    			$("#board").children("div:nth-child(" + board[j] + ")").css("left", xNew);
    			$("#board").children("div:nth-child(" + board[j] + ")").css("top", yNew);
    		}
    	}
    }
    
    function resetMoves() {
		$("#moves").text(0);
	}
    
    function updateMoves() {
    	$("#moves").text(parseInt($(moves).text()) + 1);
	}

    function moveTile(clickedTile) {
        var movable = false;
        var xOld = $("#board").children("div:nth-child(" + emptyTile + ")").css("left");
        var yOld = $("#board").children("div:nth-child(" + emptyTile + ")").css("top");
        var xNew = $(clickedTile).css("left");
        var yNew = $(clickedTile).css("top");

        if (xOld == xNew && yNew == (parseInt(yOld) - tileSize) + "px")
            movable = true;
       
        if (xOld == xNew && yNew == (parseInt(yOld) + tileSize) + "px")
            movable = true;
        
        if ((parseInt(xOld) - tileSize) + "px" == xNew && yNew == yOld)
            movable = true;
        
        if ((parseInt(xOld) + tileSize) + "px" == xNew && yNew == yOld)
            movable = true;
        
        if (movable) {
        	$(clickedTile).css("left", xOld);
            $(clickedTile).css("top", yOld);
        	$("#board").children("div:nth-child(" + emptyTile + ")").css("left", xNew);
            $("#board").children("div:nth-child(" + emptyTile + ")").css("top", yNew);
            
            if (shuffling)  minMoves++;
            else updateMoves();
        }     
    }
    
    function checkWin() {
   		// check positon of tiles
    	for (var n = 1; n < emptyTile; n++) {
    	    var xPos = $("#board").children("div:nth-child(" + n + ")").css("left");
    	    var yPos = $("#board").children("div:nth-child(" + n + ")").css("top");
    	    var xGoal = ((n - 1) % size) * tileSize;
    	    var yGoal = Math.floor((n - 1) / size) * tileSize;
    		 
    	    if (parseInt(xPos) != xGoal || parseInt(yPos) != yGoal) return;
    	}
    	
    	$("#score").text(getScore());
        $("#modalWin").modal("show");
        $("#setScore").val(getScore());
		started = false;
		$("#solution").prop("disabled", true);
    }
    
    function getScore() {
    	var m = parseInt($("#moves").text());
    	return (m <= minMoves) ? 100 : Math.floor(100 * minMoves / m);
    }
    
    $("#gameObject").n_puzzle();
	
});
</script>
		
				

