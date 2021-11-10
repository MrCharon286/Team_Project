package com.example.plantforu;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.password.*;

import com.example.plantforu.security.CustomUserDetailsService;
import com.example.plantforu.security.LoginSuccessHandler;
import com.example.plantforu.security.PlantforuAccessDeniedHandler;

import lombok.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final PasswordEncoder passwordEncoder;
	private final LoginSuccessHandler loginSuccessHandler;
	private final PlantforuAccessDeniedHandler PlantforuAccessDeniedHandler;
	private final CustomUserDetailsService service;
	
	// userDetailsService를 이용한 인증 프로바이더 빈(bean) 생성
	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(service);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		return daoAuthenticationProvider;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// CSRF 비활성화
		http.csrf().disable().exceptionHandling().accessDeniedHandler(PlantforuAccessDeniedHandler);
		
		http.formLogin().loginPage("/member/login").loginProcessingUrl("/member/login").usernameParameter("useremail")
			.passwordParameter("password").successHandler(loginSuccessHandler);
		
		http.logout().logoutUrl("/member/logout").logoutSuccessUrl("/");
	}
}

