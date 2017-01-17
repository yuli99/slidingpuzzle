package com.wei.slidingpuzzle.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "records")
public class PlayRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long recordId;
	
	@Min(3)
	@Max(5)
	private int puzzleSize;
	
    @NotNull
	private String initBoard;

	@Min(0)
	@Max(100)
	private int score;	
	private Timestamp dateOfRecord;
	
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private PuzzleUser user;
	
	// getters and setters
	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public int getPuzzleSize() {
		return puzzleSize;
	}

	public void setPuzzleSize(int puzzleSize) {
		this.puzzleSize = puzzleSize;
	}

	public String getInitBoard() {
		return initBoard;
	}

	public void setInitBoard(String initBoard) {
		this.initBoard = initBoard;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Timestamp getDateOfRecord() {
		return dateOfRecord;
	}

	public void setDateOfRecord(Timestamp dateOfRecord) {
		this.dateOfRecord = dateOfRecord;
	}

	public PuzzleUser getUser() {
		return user;
	}

	public void setUser(PuzzleUser user) {
		this.user = user;
	}

}
