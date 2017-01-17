package com.wei.slidingpuzzle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wei.slidingpuzzle.entity.PuzzleUser;


public interface UserRepository extends JpaRepository<PuzzleUser, Long> {
	
	PuzzleUser findByUserName(String userName);

}
