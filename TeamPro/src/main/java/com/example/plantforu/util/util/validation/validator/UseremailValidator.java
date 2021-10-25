package com.example.plantforu.util.util.validation.validator;

import javax.validation.*;

import org.springframework.web.bind.*;

import com.example.plantforu.util.validation.annotation.Useremail;


public class UseremailValidator implements ConstraintValidator<Useremail, String> {
	private final static String pattern = "^[A-Za-z0-9]{13,30}$";
	
	@Override
	public boolean isValid(String Useremail, ConstraintValidatorContext context) {
		if(Useremail==null)
			return true;
		return Useremail.matches(pattern);
	}

}
