package com.example.plantforu.util.validation.validator;

import javax.validation.*;

import org.springframework.web.bind.*;

import com.example.plantforu.util.validation.annotation.*;

public class IrumValidator implements ConstraintValidator<Irum, String> {
	private final static String pattern = "^[가-힣]{2,10}$";
	
	@Override
	public boolean isValid(String irum, ConstraintValidatorContext context) {
		if(irum==null)
			return true;
		return irum.matches(pattern);
	}

}
