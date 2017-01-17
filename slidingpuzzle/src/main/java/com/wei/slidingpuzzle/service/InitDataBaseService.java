package com.wei.slidingpuzzle.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wei.slidingpuzzle.entity.PuzzleUser;
import com.wei.slidingpuzzle.entity.Role;
import com.wei.slidingpuzzle.repository.RoleRepository;
import com.wei.slidingpuzzle.repository.UserRepository;


@Service
@Transactional
public class InitDataBaseService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@PostConstruct
	public void init() {
		if (roleRepository.findByRoleName("ROLE_ADMIN") == null) {
			Role roleAdmin = new Role("ROLE_ADMIN");
			roleRepository.save(roleAdmin);
			
			Role roleUser = new Role("ROLE_USER");
			roleRepository.save(roleUser);
			
			PuzzleUser admin = new PuzzleUser();
			admin.setUserName("admin");
			admin.setPassword((new BCryptPasswordEncoder()).encode("admin"));
			admin.setEmail("admin@email.com");
			admin.setEnabled(true);
			
			List<Role> roles = new ArrayList<>();
			roles.add(roleAdmin);
			roles.add(roleUser);
			admin.setRoles(roles);
			
			userRepository.save(admin);	
		}		
	}		
}
