package com.example.plantforu.security;

import java.util.*;
import java.util.stream.*;

import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.example.plantforu.entity.member.Member;
import com.example.plantforu.repository.MemberRepository;

import lombok.*;


// 스프링 시큐리티는 인증(authentication)과 인가(authorization) 기능을 제공한다
// 1. UserDetailsService가 DB에서 회원 정보를 읽어와  UserDetails 객체(Account)를 생성해 인증 프로바이더에게 넘겨준다
// 2. 스프링시큐리티의 인증 프로바이더가 Account와 사용자 입력 정보를 비교해 인증에 성공하면 Authentication 객체를 생성한다

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final MemberRepository dao;
	
	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
		
		// 1. 사용자가 없으면 InternalAuthenticationServiceException을 발생시켜라
		Member member = dao.findById(useremail).orElseThrow(()->new InternalAuthenticationServiceException("USER NOT FOUND"));
		
		// 2-1. useremail, password, enabled로 UserDetails 객체를 생성
		Account account = Account.builder().useremail(member.getUseremail()).password(member.getPassword()).isEnabled(member.getEnabled()).build();
		
		return account;
	}
}
