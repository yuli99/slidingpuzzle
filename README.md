# Sliding Puzzle Game

Description:

This project is an online sliding n-puzzle game that has two modes (number and image) and three levels (easy 8-puzzle, medium 15-puzzle, 
and hard 24-puzzle). Beside the default puzzle images, registered users can upload and save their favourite ones to play with.
The uploaded image will be squared by seam-carving technique, and then resized to 600 x 600 to fit the puzzle board. A puzzle solver is 
implemented using the A* search algorithm so that users can check the solution of the puzzle anytime after starting a game. The score of 
a win is determined by comparing the total moves the winner made with the minimum moves required by the game.    
  
Tools: 

Eclipse, Maven 3.3, Tomcat 8.0, Spring MVC 4.3.2, Spring Security 4.1.2, Spring Data JPA 1.10.2,
JPA2.1 (Hibernate 5.1.0), MySQL 5.6.17, JQuery 3.1.0, Apache Tiles 3.0.3, Bootstrap 3.3.7
