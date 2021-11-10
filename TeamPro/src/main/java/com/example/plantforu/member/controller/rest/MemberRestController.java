package com.example.plantforu.member.controller.rest;

import java.net.*;
import java.security.*;

import javax.servlet.http.*;
import javax.validation.*;
import javax.validation.constraints.*;

import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.logout.*;
import org.springframework.stereotype.*;
import org.springframework.validation.*;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.*;

import com.example.plantforu.entity.member.dto.MemberDto;
import com.example.plantforu.member.service.MemberService;
import com.example.plantforu.util.PlantforuResponseConstant;
import com.example.plantforu.util.validation.annotation.Password;
import com.example.plantforu.util.validation.annotation.Useremail;
import com.example.plantforu.util.validation.annotation.Usertel;

import lombok.*;

@RequiredArgsConstructor
@RestController
// @RequestParam에서 검증 활성화
@Validated
public class MemberRestController {
	private final MemberService service;
	
	// 이메일 사용 여부
	@GetMapping(path="/member/useremail/check", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> useremailAvailableCheck(@Useremail String useremail) {
		service.useremailAvailabelCheck(useremail);
		return ResponseEntity.ok(PlantforuResponseConstant.EMAIL_AVAILABLE_MSG);
	}
	
	// 전화번호 사용 여부
	@GetMapping(path="/member/usertel/check", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> telAvailableCheck(@Usertel String usertel) {
		service.telAvailabelCheck(usertel);
		return ResponseEntity.ok(PlantforuResponseConstant.TEL_AVAILABLE_MSG);
		}
		
	// 회원 가입
	@PostMapping(path="/member/new_join", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> join(@Valid MemberDto.Join dto, BindingResult bindingResult) throws BindException {
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
		service.join(dto);
		return ResponseEntity.ok(PlantforuResponseConstant.JOIN_MSG);
	}
	
	// 회원 가입 확인 : 체크 코드를 확인한 다음 REST로 화면 이동을 시키자
	// 300대 상태 코드는 redirect를 의미한다
	@GetMapping("/member/join/check")
	public ResponseEntity<?> joinCheck(String checkcode) {
		service.joinCheck(checkcode);
		URI uri = UriComponentsBuilder.newInstance().path("/member/login").build().toUri();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(uri);
		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	}
	
	// 아이디 찾기
	@GetMapping(path="/member/find/useremail", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> findtelRead(@Usertel String usertel) {
		URI uri = UriComponentsBuilder.newInstance().path("/member/login").build().toUri();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(uri);
		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	}
	
 	// 비밀번호 리셋->임시비번 발급
	@PatchMapping(path="/member/findpw", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> findPwdRead(@Valid MemberDto.ResetPwd dto, BindingResult bindingResult) throws BindException {
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
		service.resetPassword(dto);
		return ResponseEntity.ok(PlantforuResponseConstant.RESET_PASSWORD_MSG);
	}
	
	// 임시 비밀번호로 로그인했을 때 비밀번호 변경
	@PreAuthorize("isAuthenticated()")
	@PatchMapping(path="/member/new_findpw", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> findPwd(@Valid MemberDto.ChangePwd dto, BindingResult bindingResult, Principal principal) throws BindException {
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
		service.changePassword(dto, principal.getName());
		return ResponseEntity.ok(PlantforuResponseConstant.CHANGE_PASSWORD_MSG);
	}
	
	//내정보보기 눌렸을때 비밀번호 확인창
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path="/member/password/check", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<?> checkPassword(@Password String password, Principal principal, HttpSession session) {
		service.checkPassword(password, principal.getName());
		session.setAttribute("passwordCheck", true);
		return ResponseEntity.ok(null);
	}
	
	// 내 정보 보기
	@PreAuthorize("isAuthenticated()")
	@GetMapping(path="/member/view/myinfo", produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<?> memberInfoRead(Principal principal, HttpSession session) {
		return ResponseEntity.ok(service.read(principal.getName()));
	}
	
	// 내 정보 변경
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/member/view/myinfo")
	public ResponseEntity<?> memberInfoUpdate(@Valid MemberDto.Update dto, BindingResult bindingResult, Principal principal) throws BindException {
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
		service.update(dto, principal.getName());
		return ResponseEntity.ok(null);
	}
	
	// 회원 탈퇴 후 루트 페이지로 이동(회원 탈퇴 버튼이 내정보에 있다. 따라서 루트로 강제이동 시키자)
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/member/view/myinfo")
	public ResponseEntity<?> memberDrop(SecurityContextLogoutHandler handler, HttpServletRequest req, HttpServletResponse res, Authentication authentication )  {
		service.resign(authentication.getName());
		handler.logout(req, res, authentication);
		return ResponseEntity.ok(null);
	}
	
	
}
