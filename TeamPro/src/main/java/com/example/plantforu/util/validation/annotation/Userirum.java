package com.example.plantforu.util.validation.annotation;

import java.lang.annotation.*;

import javax.validation.*;

import com.example.plantforu.util.util.validation.validator.UserirumValidator;

// 적용 위치 : 클래스, 필드, 메소드, 파라미터
@Target({ElementType.FIELD, ElementType.PARAMETER})
// 언제 동작할거니?
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserirumValidator.class)
public @interface Userirum {
	// 검증 실패시 출력할 기본 메시지
	String message() default "이름은 2~5자입니다";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {}; 
}
