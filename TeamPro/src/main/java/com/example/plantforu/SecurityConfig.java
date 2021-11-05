package com.example.plantforu;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.access.*;

import com.example.plantforu.security.*;

// 스프링 설정 파일임
@Configuration
// 스프링 시큐리티에 대한 설정파일이라고 알려줌
@EnableWebSecurity
public class SecurityConfig {
	
	// 관리자 작업은 /admin/** 경로로 구분
	// 멤버 경로는 @Secured로 구분
	// /admin/**으로 접근하는 관리자에 대한 시큐리티 설정
	@Order(1)
	@Configuration
	public static class AdminSecurityConfing extends WebSecurityConfigurerAdapter {
		@Autowired
		private PasswordEncoder passwordEncoder;
		@Autowired
		private AdminLoginSuccessHandler adminLoginSuccessHandler;
		@Autowired
		private AdminLoginFailureHandler adminLoginFailureHandler;

		// 오류가 발생하면 ControllerAdvice가 적절한 상태코드(우리의 경우 409)를 클라이언트에 전송한다
		// 그런데 권한이 잘못된 경우에는 바로 403이 발생한다(ControllerAdvice와 상관없다)
		// 이 403 오류를 처리하는 객체가 AccessDeniedHandler 구현체
		@Autowired
		private AccessDeniedHandler accessDeniedHandler;

		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			// inMemory에 관리자 아이디, 비밀번호, 권한 등록
			auth.inMemoryAuthentication().withUser("system").password(passwordEncoder.encode("1234")).roles("ADMIN");
		}	
		
		// 파라미터 http는 스프링 시큐리티를 이용한 접근 통제 정보를 저장할 객체
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// /admin/** 경로 중 접근 통제를 하지 않는 예외 경로를 먼저 설정
			http.authorizeRequests().antMatchers("/admin/login").permitAll();
			
			// /admin/**로 들어오는 요청에 대해 ADMIN 권한과 폼 로그인 설정
			http.requestMatchers().antMatchers("/admin/**").and().authorizeRequests().anyRequest().hasRole("ADMIN")
			.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler)
			.and().formLogin().loginPage("/admin/login").loginProcessingUrl("/admin/login")
			.successHandler(adminLoginSuccessHandler).failureHandler(adminLoginFailureHandler)
			.and().logout().logoutUrl("/admin/logout").logoutSuccessUrl("/").invalidateHttpSession(true);
		}
	}
	
	// /admin/**을 제외한 나머지 경로에 대한 시큐리티 설정
	@Order(2)
	@Configuration
	// 여기서는 @PreAuthorize, @PostAuthorize, @Secured 어노테이션 기반으로 접근 통제
	@EnableGlobalMethodSecurity(prePostEnabled=true, securedEnabled=true)
	public static class MemberSecurityConfig extends WebSecurityConfigurerAdapter {
		@Autowired
		private PasswordEncoder passwordEncoder;
		@Autowired
		private MemberLoginSuccessHandler memberLoginSuccessHandler;
		@Autowired
		private MemberLoginFailureHandler memberLoginFailureHandler;
		@Autowired
		private AccessDeniedHandler accessDeniedHandler;
		

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication().withUser("spring").password(passwordEncoder.encode("1234")).roles("USER");
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
				.and().formLogin().loginPage("/member/login").loginProcessingUrl("/member/login")
					.successHandler(memberLoginSuccessHandler).failureHandler(memberLoginFailureHandler)
				.and().logout().logoutUrl("/member/logout").logoutSuccessUrl("/").invalidateHttpSession(true);
		}
	}
}

