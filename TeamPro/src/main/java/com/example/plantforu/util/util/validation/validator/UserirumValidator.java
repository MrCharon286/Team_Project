package com.example.plantforu.util.util.validation.validator;

import javax.validation.*;

import org.springframework.web.bind.*;

import com.example.plantforu.util.validation.annotation.Userirum;

public class UserirumValidator implements ConstraintValidator<Userirum, String> {
	private final static String pattern = "^[가-힣]{3,5}$";
	
	@Override
	public boolean isValid(String userirum, ConstraintValidatorContext context) {
		if(userirum==null)
			return true;
		return userirum.matches(pattern);
	}

}
