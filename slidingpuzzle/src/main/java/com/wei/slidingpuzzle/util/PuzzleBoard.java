package com.wei.slidingpuzzle.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PuzzleBoard {

	private int[] board;
	private int size;
	private int empty_i;
	private int empty_j;

	// construct a puzzle board from play record
	public PuzzleBoard(int puzzleSize, String boardStr) {
		String[] blocks = boardStr.trim().split(",");
		
		if (puzzleSize < 1 || blocks.length != puzzleSize * puzzleSize) {
			throw new NullPointerException();
		}
		
		size = puzzleSize;
		board = new int[size * size];
		int k = 0;
		for (String block : blocks) {
			board[k] = Integer.parseInt(block);
			
			if (board[k] == 0) {
				empty_i = k / size;
				empty_j = k % size;
			}
				
			k++;
		}
	}

	// construct a puzzle board from an N-by-N array of blocks
	public PuzzleBoard(int[][] blocks) {
		if (blocks == null)
			throw new NullPointerException();

		size = blocks.length;
		board = new int[size * size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i * size + j] = blocks[i][j];
				if (blocks[i][j] == 0) {
					empty_i = i;
					empty_j = j;
				}
			}
		}
	}

	// number of blocks out of place
	public int hamming() {
		int numHamming = 0;

		for (int k = 0; k < board.length; k++) {
			if (board[k] != 0 && board[k] != k + 1)
				numHamming++;
		}

		return numHamming;
	}

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		int numDis = 0;

		for (int k = 0; k < board.length; k++) {
			if (board[k] != 0 && board[k] != k + 1) {
				int di = Math.abs((board[k] - 1) / size - k / size);
				int dj = Math.abs((board[k] - 1) % size - k % size);
				numDis += di + dj;
			}
		}

		return numDis;
	}

	// is this board the goal board?
	public boolean isGoal() {
		return this.hamming() == 0;
	}

	// a board that is obtained by exchanging two adjacent blocks in the same row
	public PuzzleBoard twin() {
		int[][] twin = this.copyBoard();
		
		if (board[0] != 0 && board[1] != 0) {
			twin[0][0] = board[1];
			twin[0][1] = board[0];
		} else {
			twin[1][0] = board[size + 1];
			twin[1][1] = board[size];
		}

		return new PuzzleBoard(twin);
	}

	private int[][] copyBoard() {
		int[][] copy = new int[size][size];

		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				copy[i][j] = board[i * size + j];

		return copy;
	}

	// does this board equal
	public boolean equals(Object y) {
		if (y == null)
			return false;
		if (y == board)
			return true;
		if (y.getClass() != this.getClass())
			return false;

		PuzzleBoard that = (PuzzleBoard) y;
		if (that.getSize() != size)
			return false;

		return Arrays.equals(that.getBoard(), board);
	}

	// all neighboring boards
	public List<PuzzleBoard> neighbors() {
		List<PuzzleBoard> boards = new ArrayList<>();

		if (empty_i > 0) {
			PuzzleBoard up = new PuzzleBoard(swap(-1, 0));
			boards.add(up);
		}

		if (empty_i < size - 1) {
			PuzzleBoard down = new PuzzleBoard(swap(+1, 0));
			boards.add(down);
		}

		if (empty_j > 0) {
			PuzzleBoard left = new PuzzleBoard(swap(0, -1));
			boards.add(left);
		}

		if (empty_j < size - 1) {
			PuzzleBoard right = new PuzzleBoard(swap(0, +1));
			boards.add(right);
		}

		return boards;
	}

	private int[][] swap(int di, int dj) {
		int[][] neighbor = copyBoard();

		neighbor[empty_i][empty_j] = neighbor[empty_i + di][empty_j + dj];
		neighbor[empty_i + di][empty_j + dj] = 0;

		return neighbor;
	}

	// string representation of this board
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int k = 0; k < board.length; k++) {
			s.append(board[k]);
			s.append(",");
		}
		
		s.deleteCharAt(s.length() - 1);

		// make it convenient for UI Query use
		return "'" + s.toString() + "'";
	}

	// getters and setters
	public int[] getBoard() {
		return board;
	}

	public void setBoard(int[] board) {
		this.board = board;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getEmpty_i() {
		return empty_i;
	}

	public void setEmpty_i(int empty_i) {
		this.empty_i = empty_i;
	}

	public int getEmpty_j() {
		return empty_j;
	}

	public void setEmpty_j(int empty_j) {
		this.empty_j = empty_j;
	}

}
