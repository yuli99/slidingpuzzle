package com.wei.slidingpuzzle.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wei.slidingpuzzle.entity.PuzzleImage;
import com.wei.slidingpuzzle.entity.PuzzleUser;
import com.wei.slidingpuzzle.entity.Role;
import com.wei.slidingpuzzle.entity.PlayRecord;
import com.wei.slidingpuzzle.repository.ImageRepository;
import com.wei.slidingpuzzle.repository.RecordRepository;
import com.wei.slidingpuzzle.repository.RoleRepository;
import com.wei.slidingpuzzle.repository.UserRepository;


@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RecordRepository recordRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	public void createUser(PuzzleUser user) {
		user.setEnabled(true);
		user.setPassword((new BCryptPasswordEncoder()).encode(user.getPassword()));
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleRepository.findByRoleName("ROLE_USER"));
		user.setRoles(roles);
		
		userRepository.save(user);	
	}
	
	public void deleteUser(Long userId) {
		userRepository.delete(userId);
	}
	
	public List<PuzzleUser> loadAllUser() {
		return userRepository.findAll();
	}
	
	public PuzzleUser loadUserById(Long userId) {
		return userRepository.findOne(userId);
	}
	
	@Transactional
	public PuzzleUser loadUserWithRecordsAndImages(Long userId) {
		PuzzleUser user = userRepository.findOne(userId);
		
		List<PlayRecord> records = recordRepository.findByUser(user, new PageRequest(0, 20, Direction.DESC, "dateOfRecord"));
		user.setRecords(records);
		
		List<PuzzleImage> images = imageRepository.findByUser(user);
		user.setImages(images);
		
		return user;
	}
	
	public PuzzleUser loadUserByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}
	
	public PuzzleUser loadUserWithRecordsAndImages(String userName) {
		PuzzleUser user = userRepository.findByUserName(userName);
		return loadUserWithRecordsAndImages(user.getUserId());
	}
	
}
