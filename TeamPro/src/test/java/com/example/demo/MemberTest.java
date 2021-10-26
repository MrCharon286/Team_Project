package com.example.demo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.security.authorization.*;
import org.springframework.test.context.*;

import com.example.plantforu.entity.member.*;
import com.example.plantforu.repository.*;

@SpringBootTest
@ContextConfiguration(classes = Member.class)
public class MemberTest {
	@Autowired
	private Member member;
	@Autowired
	private MemberRepository memberdao;
	
	@Test
	public void insertTest() {
		Member member = Member.builder().useremail("spring1234@naver.com").password("1234").username("spring").usertel("010-1234-5685").build();

	}
}
