package com.wei.slidingpuzzle.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "roles")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;
	
	private String roleName;
	
	@ManyToMany(mappedBy = "roles")
	private List<PuzzleUser> users;
	
	public Role() {}
	
	public Role(String roleName) {
		this.roleName = roleName;
	}

	// getters and setters
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<PuzzleUser> getUsers() {
		return users;
	}

	public void setUsers(List<PuzzleUser> users) {
		this.users = users;
	}

}
