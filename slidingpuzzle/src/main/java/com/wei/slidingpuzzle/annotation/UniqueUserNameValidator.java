package com.wei.slidingpuzzle.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.wei.slidingpuzzle.repository.UserRepository;


public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String> {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void initialize(UniqueUserName constraintAnnotation) {}

	@Override
	public boolean isValid(String userName, ConstraintValidatorContext context) {
		
		if(userRepository == null || userRepository.findByUserName(userName) == null) {
			return true;
		}
		else {
			return false;
		}
	}

}
