package com.wei.slidingpuzzle.util;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class PuzzleSolver {
	
	private SearchNode goalNode;
	private PriorityQueue<SearchNode> pqNode = new PriorityQueue<>();
	private PriorityQueue<SearchNode> pqTwin = new PriorityQueue<>();
	
	private class SearchNode implements Comparable<SearchNode>  {
		
		private PuzzleBoard board;
		private int moves;
		private SearchNode previous;
		
		public SearchNode(PuzzleBoard board, int moves, SearchNode previous)  {
			this.board = board;
			this.moves = moves;
			this.previous = previous;
		}
		
		public int compareTo(SearchNode that)  {
			if (this.priority() > that.priority()) {
				return 1;
			}
			else if (this.priority() == that.priority()) {
				return 0;
			}
			else {
				return -1;
			}
		}
		
		public int priority() {
			return  board.manhattan() + moves;
		}		
	}
		
	// find a solution to the initial board (A* algorithm)
	public PuzzleSolver(PuzzleBoard initBoard)  {
		if (initBoard == null) 
			throw new NullPointerException();
		
		SearchNode currNode = new SearchNode(initBoard, 0, null);
		SearchNode currTwin = new SearchNode(initBoard.twin(), 0, null);
		pqNode.add(currNode);
		pqTwin.add(currTwin);
		
		while (!currNode.board.isGoal() && !currTwin.board.isGoal()) {
			currNode = pqNode.poll();
			currTwin =pqTwin.poll();
			
			for (PuzzleBoard b : currNode.board.neighbors()) {
				SearchNode nextNode = new SearchNode(b, currNode.moves + 1, currNode);
				if (currNode.previous == null) {
					pqNode.add(nextNode);
				}
				else if (!b.equals(currNode.previous.board)) {
					pqNode.add(nextNode);	
				}
			}
			
			for (PuzzleBoard b : currTwin.board.neighbors()) {
				SearchNode nextTwin = new SearchNode(b, currTwin.moves + 1, currTwin);
				if (currTwin.previous == null) {
					pqTwin.add(nextTwin);
				}
				else if (!b.equals(currTwin.previous.board)) {
					pqTwin.add(nextTwin);
				}
			}
		}
		
		if (currNode.board.isGoal()) {
			goalNode = currNode;	
		}		
	}
	
	// is the initial board solvable
	public boolean isSolvable()  {
		return goalNode != null;
	}
		
	// minimum number of moves to solve initial board; 
	// return -1 if unsolvable
	public int moves()  {
		if (goalNode == null)  
			return -1;
		else 
			return goalNode.moves;
	}
		
	// sequence of boards in a shortest solution
	// return null if unsolvable
	public List<PuzzleBoard> solution()  {
		if (!isSolvable()) return null;
		
		Stack<PuzzleBoard> solBoards = new Stack<>();
		SearchNode trace = goalNode;
		solBoards.push(trace.board);
		
		while (trace.previous != null) {
			trace = trace.previous;
			solBoards.push(trace.board);
		}
		
		List<PuzzleBoard> solution = new ArrayList<>();
		while (!solBoards.isEmpty()) {
			solution.add(solBoards.pop());
		}
		
		return solution;
	}

}
