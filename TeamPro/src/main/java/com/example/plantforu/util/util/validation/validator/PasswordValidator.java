package com.example.plantforu.util.util.validation.validator;

import javax.validation.*;

import com.example.plantforu.util.validation.annotation.Password;

public class PasswordValidator implements ConstraintValidator<Password, String> {
	private final static String pattern = "^(?=.*[!@#$%^&*])^[A-Za-z0-9!@#$%^&*]{8,10}$";
	
	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		if(password==null)
			return true;
		return password.matches(pattern);
	}

}
