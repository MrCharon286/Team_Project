package com.example.plantforu.util.validation.annotation;

import java.lang.annotation.*;

import javax.validation.*;

import com.example.plantforu.util.util.validation.validator.UseremailmailValidator;
import com.example.plantforu.util.util.validation.validator.UsertelValidator;

// 적용 위치 : 클래스, 필드, 메소드, 파라미터
@Target({ElementType.FIELD, ElementType.PARAMETER})
// 언제 동작할거니?
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsertelValidator.class)
public @interface Usertel {
	// 검증 실패시 출력할 기본 메시지
	String message() default "전화번호는은 13자 입니다, -까지 입력해주세요";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {}; 
}
