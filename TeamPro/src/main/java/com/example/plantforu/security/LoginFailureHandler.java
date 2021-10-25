package com.example.plantforu.security;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import com.example.plantforu.entity.member.Member;
import com.example.plantforu.repository.MemberRepository;

import lombok.*;

@RequiredArgsConstructor
@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	private final MemberRepository dao;
	
	@Transactional
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		HttpSession session = request.getSession();
		
		// 아이디가 없는 경우
		if(exception instanceof InternalAuthenticationServiceException) 
			session.setAttribute("msg", "아이디를 확인하세요");
		else if(exception instanceof BadCredentialsException) {
			// 로그인에 실패했으므로 인증 객체는 존재하지 않는다. 아이디는 request.getParameter()로 꺼내오자 
			String useremail = request.getParameter("useremail");
			Member member = dao.findById(useremail).get();
			
			// 로그인 실패 횟수와 아이디 활성화 [업데이트]는 member에서 처리
			member.loginFail();
			
			// 세션에 오류 메시지를 저장
			if(member.getLoginFailCnt()<4) 
				session.setAttribute("msg", "로그인에 " + member.getLoginFailCnt() + "회 실패했습니다");
			else
				session.setAttribute("msg", "로그인에 5회이상 실패해 자동입력방지문자를 입력하세요");	
		} 
	}
}





