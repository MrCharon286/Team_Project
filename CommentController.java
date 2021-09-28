package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.*;
import com.example.demo.service.*;

@RestController
public class CommentController {
	//Rest 방식은 화면없이 Model만 출력-> Model은 자바객체, 자바객체를 보내면 클라리언트가 자바가 아니라면 이해 불가능
	//							  -> 보통 JSON으로 변환해서 출력
	
	//스프링은 간단한 일을 하는 작은 모듈들을 조립해서 만든 거대 프레임워크임
	//	사용자		<--->		컨트롤러
	//				<--->		PropertyEditor		ex)	2020-11-27	-> 날짜객체로 받을 수 없다
	//				<--->		MessageConverter	[Jackson databind], Gson을 pom.xml에 추가하면 자동 등록
	
	//REST 방식은 응답객체에 직접 내용을 적어서 출력 :  @ResponseBody, @RequestBody
	
	//스프링에서 REST 응답용으로 제공하는 ResponseEntity 클래스 : @ResponseBody + 상태코드
	
	//REST는 postmap 깔아서 돌릴수 있음 화면이 없기 때문에
	@Autowired
	private CommentService service;
	
	@PostMapping("/comment/insert") 
	public ResponseEntity<?> insert(@ModelAttribute Comment comment) {
		List<Comment> list = service.insert(comment);
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("/comment/delete") 
	public ResponseEntity<?> delete(@RequestParam int cno, @RequestParam int bno, @RequestParam String password){
		List<Comment> list = service.delete(cno, password, bno);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
}
