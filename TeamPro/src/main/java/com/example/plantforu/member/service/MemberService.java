package com.example.plantforu.member.service;


import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

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
	
	@Scheduled(cron = "0 0 4 ? * THU")
	public void deleteMemberWithInvalidCheckcode() {
		List<Member> members = dao.findByCheckcodeIsNotNull();
		dao.deleteAll(members);
	}
	
	public void useremailAvailabelCheck(String useremail) {
		if(dao.existsByUseremail(useremail)==true)
			throw new MemberFail.UseremailExistException();
	}
	
	public void telAvailabelCheck(String usertel) {
		if(dao.existsByUsertel(usertel)==true)
			throw new MemberFail.UsertelExistException();
	}

	public void join(MemberDto.Join dto) {
		Member member = dto.toEntity();
		String checkcode = RandomStringUtils.randomAlphanumeric(20);
		String encodedPassword = passwordEncoder.encode(member.getPassword());
		
		// member에 추가해야 할 필드 : checkcode, 비밀번호, Set<Authority>
		member.addJoinInfo(checkcode, encodedPassword, Arrays.asList("ROLE_USER"));
		dao.save(member);
		mailUtil.sendJoinCheckMail("admin@zmall.com", member.getUseremail(), checkcode);
	}
	
	@Transactional
	public void joinCheck(String checkcode) {
		Member member = dao.findByCheckcode(checkcode).orElseThrow(MemberFail.JoinCheckFailException::new);
		member.setCheckcode(null).setEnabled(true);
	}

	@Transactional(readOnly=true)
	public String findUseremail(String usertel) {
		return dao.findUseremailByUsertel(usertel).orElseThrow(MemberFail.MemberNotFoundException::new);
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
	public void changePassword(ChangePwd dto, String useremail) {
		Member member = dao.findById(useremail).orElseThrow(MemberFail.MemberNotFoundException::new);
		if(dto.getPassword()!=null && dto.getNewPassword()!=null) {
			if(passwordEncoder.matches(dto.getPassword(), member.getPassword())==false)
				throw new MemberFail.PasswordCheckException();
			member.setPassword(passwordEncoder.encode(dto.getNewPassword()));
		}
	}
	
	@Transactional(readOnly=true)
	public void checkPassword(String password, String useremail) {
		Member member = dao.findById(useremail).orElseThrow(MemberFail.MemberNotFoundException::new);
		if(passwordEncoder.matches(password, member.getPassword())==false)
			throw new MemberFail.PasswordCheckException();
	}

	// Member 처리 : 수정필요
	public Member read(String useremail) {
		Member member = dao.findById(useremail).orElseThrow(MemberFail.MemberNotFoundException::new);	
		return member;
	}
	

	@Transactional
	public void update(MemberDto.Update dto, String useremail) {
		Member member = dao.findById(useremail).orElseThrow(MemberFail.MemberNotFoundException::new);
		
		if(dto.getUseremail()!=null)
			member.setUseremail(dto.getUseremail());
		
		if(dto.getPassword()!=null && dto.getNewPassword()!=null) {
			if(passwordEncoder.matches(dto.getPassword(), member.getPassword())==false)
				throw new MemberFail.PasswordCheckException();
			member.setPassword(passwordEncoder.encode(dto.getPassword()));
		}
		
	}

	public void resign(String useremail) {
		dao.deleteById(useremail);
	}

}
