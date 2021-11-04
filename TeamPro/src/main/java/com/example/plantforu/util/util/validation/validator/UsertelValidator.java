package com.example.plantforu.util.util.validation.validator;

import javax.validation.*;


import com.example.plantforu.util.validation.annotation.Usertel;

public class UsertelValidator implements ConstraintValidator<Usertel, String> {
	private final static String pattern = "^[0-9]{3}-[0-9]{4}-[0-9]{4}$";
	
	@Override
	public boolean isValid(String usertel, ConstraintValidatorContext context) {
		if(usertel==null)
			return true;
		return usertel.matches(pattern);
	}

}
