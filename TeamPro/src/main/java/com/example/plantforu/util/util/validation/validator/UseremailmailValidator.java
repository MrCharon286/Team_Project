package com.example.plantforu.util.util.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.plantforu.util.validation.annotation.Useremail;

public class UseremailmailValidator implements ConstraintValidator<Useremail, String> {
	private final static String pattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
	
	@Override 	 	
	public boolean isValid(String useremail, ConstraintValidatorContext context) {
		System.out.println(useremail);
		System.out.println(useremail.matches(pattern));
		if(useremail==null)
			return true;
		return useremail.matches(pattern);
	}

}
