package com.wei.slidingpuzzle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wei.slidingpuzzle.entity.PuzzleImage;
import com.wei.slidingpuzzle.entity.PuzzleUser;


public interface ImageRepository extends JpaRepository<PuzzleImage, Long> {
	
	PuzzleImage findByImageUri(String imageUri);
	
	List<PuzzleImage> findByUser(PuzzleUser user);
	
}
