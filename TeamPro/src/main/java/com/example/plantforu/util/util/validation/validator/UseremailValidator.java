package com.example.plantforu.util.util.validation.validator;

import javax.validation.*;

import org.springframework.web.bind.*;

import com.example.plantforu.util.validation.annotation.Useremail;


public class UseremailValidator implements ConstraintValidator<Useremail, String> {
	private final static String pattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i";
	
	@Override
	public boolean isValid(String Useremail, ConstraintValidatorContext context) {
		if(Useremail==null)
			return true;
		return Useremail.matches(pattern);
	}

}
