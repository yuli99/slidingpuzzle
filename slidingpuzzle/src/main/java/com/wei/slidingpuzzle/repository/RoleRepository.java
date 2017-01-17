package com.wei.slidingpuzzle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wei.slidingpuzzle.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByRoleName(String roleName);
}
