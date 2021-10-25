package com.example.plantforu.util.validation.annotation;

import java.lang.annotation.*;

import javax.validation.*;

import com.example.plantforu.util.util.validation.validator.PasswordValidator;

@Target({ElementType.FIELD, ElementType.PARAMETER})
// 언제 동작할거니?
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
	// 검증 실패시 출력할 기본 메시지
	String message() default "비밀번호는 특수문자를 포함하는 영숫자와 특수문자 8~10자입니다";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {}; 
}
