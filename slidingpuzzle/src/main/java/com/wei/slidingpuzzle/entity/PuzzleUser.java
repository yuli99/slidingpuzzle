package com.wei.slidingpuzzle.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.wei.slidingpuzzle.annotation.UniqueUserName;


@Entity
@Table(name = "users")
public class PuzzleUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Size(min = 2, max = 45, message = "{input.size}")
	@Column(unique = true)
	@UniqueUserName(message = "{userName.exist}")
	private String userName;
	
	@Size(min = 3, max = 60, message = "{input.size}")
	private String password;
	
	@NotNull
	@Email(message = "{email.invalid}")
	private String email;
	
	private boolean enabled;
	
	@ManyToMany
	@JoinTable
	private List<Role> roles;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<PuzzleImage> images;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<PlayRecord> records;

	// getters and setters
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<PuzzleImage> getImages() {
		return images;
	}

	public void setImages(List<PuzzleImage> images) {
		this.images = images;
	}

	public List<PlayRecord> getRecords() {
		return records;
	}

	public void setRecords(List<PlayRecord> records) {
		this.records = records;
	}

}
