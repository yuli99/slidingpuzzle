<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<h1>About</h1>

<br />

<h3>Introduction</h3>

<p>The n-puzzle game is invented and popularized by Noyes Palmer Chapman in the 1870s. It is a sliding puzzle that consists of a frame 
	of numbered square tiles in random order with one tile missing. The object of the puzzle is to place the tiles in order 
	by making sliding moves that use the empty space. The puzzle exists in different sizes. For 3×3 tiles, the puzzle is called the 
	8-puzzle; for 4×4 tiles, the puzzle is called the 15-puzzle; and for 5×5 tiles, the puzzle is called the 24-puzzle. </p>

<br />

<h3>Puzzle solution</h3>

<p>A* search algorithm is used to find the optimal solution of the n-puzzle game. We define a search node of the game to be the board,
	the number of moves made to reach the board, and the previous search node. First, insert the initial search node into a minimum
	priority queue. Then, remove the search node with minimum priority in the queue and insert all its neighboring search nodes instead. 
	Repeat the procedure until the dequeued search node corresponds to the goal board. When the goal board is dequeued, we have discovered
	not only a sequence of moves from the initial board to the goal board, but one that makes the fewest number of moves (the optimal one).</p> 
	
<p>The choice of the priority function for the search node is crucial for the success of this approach. Manhattan priority function is 
	defined and user here. It is the sum of Manhattan distances for the tiles to their goal positions, plus the number of moves made so 
	far to get to the search node. The Manhattan distance for a tile is the sum of the vertical and horizontal distance to its goal 
	position.</p>
	
<p>Search nodes corresponding to the same board are enqueued on the priority queue multiple times in this type of search. To optimize the search,
	when considering the neighbor of a search node, we don't enqueue a neighbor if the board is the same as the board of the previous search node.</p>
	
<p>Not all the initial boards are solvable. To detect the unsolvable ones, we use the fact that boards are divided into two classes with respect to 
	solvability: boards that are solvable and boards that are solvable only if we modify the initial board by swapping any pair of adjacent tiles
	in the same row. Run A* search algorithm simultaneously on a puzzle and its twin obtained by swapping a pair of adjacent tiles instances, only 
	one of the two will lead to the goal board. </p>     

<br />
	    
<h3>Image resizing</h3>

<p>Seam-carving technique is used to resize user uploaded image to a square shape. It is a content-aware image resizing technique where image is 
	reduced in size by one pixel of height or width at a time. A vertical (horizontal) seam in an image is a path of pixels connected from the top 
	to the bottom (the left to the right) with one pixel in each row. Unlike standard content-agnostic resizing techniques such as cropping and 
	scaling, the most interesting features(aspect ration, set of objects present, etc.) of the image are preserved.</p>

<p>To identify and remove a seam from the image, we first calculate the energy of each pixel, which is a measure of the importance of each pixel.
	The higher the energy, the less likely that the pixel will be included as part of a seam. The dual gradient energy function is implemented to
	do the calculation and it gives high values for pixels with a rapid color gradient. The seam-carving technique avoids removing those high-energy
	pixels in the image by finding a seam of minimum total energy. The way of doing it is similar to the classic shortest path problem in an edge-weight
	digraph.</p>
