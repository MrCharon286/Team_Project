package com.example.plantforu.member.service;

import java.time.*;
import java.time.temporal.*;
import java.util.*;

import org.apache.commons.lang3.*;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.*;

import com.example.plantforu.entity.member.Member;
import com.example.plantforu.entity.member.MemberFail;
import com.example.plantforu.entity.member.dto.MemberDto;
import com.example.plantforu.entity.member.dto.MemberDto.ChangePwd;
import com.example.plantforu.entity.member.dto.MemberDto.ResetPwd;
import com.example.plantforu.repository.MemberRepository;
import com.example.plantforu.util.MailUtil;

import lombok.*;

@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepository dao;
	private final MailUtil mailUtil;
	private final PasswordEncoder passwordEncoder;
	
	public void emailAvailabelCheck(String useremail) {
		if(dao.existsByUseremail(useremail)==true)
			throw new MemberFail.UseremailExistException();
	}
	@Transactional(readOnly=true)
	public void telAvailabelCheck(String usertel) {
		if(dao.existsByUsertel(usertel)==true)
			throw new MemberFail.UsertelExistException();
	}

	public void join(MemberDto.Join dto) {
		Member member = dto.toEntity();
		String encodedPassword = passwordEncoder.encode(member.getPassword());
	}

	@Transactional(readOnly=true)
	public String findEmail(String usertel) {
		return dao.findEmailbByUsertel(usertel).orElseThrow(MemberFail.MemberNotFoundException::new);
	}

	@Transactional
	public void resetPassword(ResetPwd dto) {
		Member member = dao.findById(dto.getUseremail()).orElseThrow(MemberFail.MemberNotFoundException::new);
		if(member.getUseremail().equals(dto.getUseremail())==false)
			throw new MemberFail.MemberNotFoundException();
		String newPassword = RandomStringUtils.randomAlphanumeric(20);
		member.setPassword(passwordEncoder.encode(newPassword));
		mailUtil.sendResetPasswordMail("admin@zmall.com", member.getUseremail(), newPassword);
	}

	// 기존 비밀번호가 확인되면 새 비밀번호를 암호화해서 저장
	@Transactional
	public void changePassword(ChangePwd dto, String loginEmail) {
		Member member = dao.findById(loginEmail).orElseThrow(MemberFail.MemberNotFoundException::new);
		if(dto.getPassword()!=null && dto.getNewPassword()!=null) {
			if(passwordEncoder.matches(dto.getPassword(), member.getPassword())==false)
				throw new MemberFail.PasswordCheckException();
			member.setPassword(passwordEncoder.encode(dto.getNewPassword()));
		}
	}
	
	@Transactional(readOnly=true)
	public void checkPassword(String password, String loginId) {
		Member member = dao.findById(loginId).orElseThrow(MemberFail.MemberNotFoundException::new);
		if(passwordEncoder.matches(password, member.getPassword())==false)
			throw new MemberFail.PasswordCheckException();
	}

	// Member 처리 : 프사에 주소를 추가, transient 필드인 days에 값을 추가해서 내보내자
	public Member read(String loginEmail) {
		Member member = dao.findById(loginEmail).orElseThrow(MemberFail.MemberNotFoundException::new);	
		return member;
	}
	

	@Transactional
	public void update(MemberDto.Update dto, String loginId) {
		Member member = dao.findById(loginId).orElseThrow(MemberFail.MemberNotFoundException::new);
		
		if(dto.getEmail()!=null)
			member.setUseremail(dto.getEmail());
		
		if(dto.getPassword()!=null && dto.getNewPassword()!=null) {
			if(passwordEncoder.matches(dto.getPassword(), member.getPassword())==false)
				throw new MemberFail.PasswordCheckException();
			member.setPassword(passwordEncoder.encode(dto.getPassword()));
		}
		
	}

	public void resign(String loginId) {
		dao.deleteById(loginId);
	}
}
