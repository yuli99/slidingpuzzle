package com.wei.slidingpuzzle.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wei.slidingpuzzle.entity.PuzzleUser;
import com.wei.slidingpuzzle.entity.PlayRecord;


public interface RecordRepository extends JpaRepository<PlayRecord, Long> {
	
	List<PlayRecord> findByUser(PuzzleUser user, Pageable pageable);

}
