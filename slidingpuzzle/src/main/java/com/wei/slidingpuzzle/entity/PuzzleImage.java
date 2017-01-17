package com.wei.slidingpuzzle.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;


@Entity
@Table(name = "puzzle_images")
public class PuzzleImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imageId;
	
	@Size(min = 5, max = 60, message = "{input.size}")
	private String imageUri;
	
	private Timestamp uploadDate;
	
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private PuzzleUser user;
	
	public PuzzleImage() {}
	
	public PuzzleImage(String imageUri) {
		this.imageUri = imageUri;
	}
	
	// getters and setters
	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public Timestamp getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Timestamp uploadDate) {
		this.uploadDate = uploadDate;
	}

	public PuzzleUser getUser() {
		return user;
	}

	public void setUser(PuzzleUser user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return imageUri;
	}

}
