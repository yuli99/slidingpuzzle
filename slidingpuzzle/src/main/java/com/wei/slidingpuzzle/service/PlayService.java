package com.wei.slidingpuzzle.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.wei.slidingpuzzle.entity.PlayRecord;
import com.wei.slidingpuzzle.repository.RecordRepository;
import com.wei.slidingpuzzle.util.PuzzleBoard;
import com.wei.slidingpuzzle.util.PuzzleSolver;


@Service
public class PlayService {
	
	@Autowired
	private RecordRepository recordRepository;
	
	/* 
	 * find an optimal solution of the puzzle using A* algorithm
	 * return null if the puzzle is unsolvable 
	 */
	public List<String> getSolution(int size, String boardStr) {
		PuzzleBoard intiBoard = new PuzzleBoard(size, boardStr);
		PuzzleSolver solver = new PuzzleSolver(intiBoard);
		
		List<PuzzleBoard> boards = solver.solution();
		if (boards == null) return null;
		
		List<String> solution = new ArrayList<String>(boards.size());
		for (PuzzleBoard board : boards) {
			solution.add(board.toString());
		}
		
		return solution;
	}
	
	public void savePlayRecord(PlayRecord record) {
		record.setDateOfRecord(new Timestamp(Calendar.getInstance()
				.getTime().getTime()));
		
		recordRepository.save(record);
	}
	
	@PreAuthorize("#record.user.userName == authentication.name or hasRole('ROLE_ADMIN')")
	public void deleteRecord(@P("record") PlayRecord record) {
		recordRepository.delete(record);
	}
	
	public PlayRecord loadRecordById(Long recordId) {
		return recordRepository.findOne(recordId);
	}	

}
