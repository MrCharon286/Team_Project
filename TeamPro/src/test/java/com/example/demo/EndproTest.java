package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.plantforu.entity.member.Member;
import com.example.plantforu.member.service.MemberService;
import com.example.plantforu.repository.MemberRepository;

public class EndproTest {
	@Autowired
	MemberRepository dao;
	@Autowired
	MemberService service;
	
	@Test
	public void test() {
		service.useremailAvailabelCheck(null);
	}
	
	
	
}
