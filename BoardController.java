package com.example.demo.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import com.example.demo.entity.*;
import com.example.demo.service.*;

//REST : data
//MVC : Model view Controller (Model 2)
@Controller
public class BoardController {
	@Autowired
	private BoardService service;
	
	// 글쓰기 화면보여주기
	@GetMapping("/board/write") 
	public ModelAndView write() {
		return new ModelAndView("board/write");
	}
	
	//글쓰기
	@PostMapping("/board/write")
	public ModelAndView write(@ModelAttribute Board board) {
		//사용자가 제목, 내용, 닉네임, 비밀번호를 입력하면 그값을 객체 생성은 스프링이 수행한다
		//사용자가 입력한 값을 담고 있는 객체를 Commend 객체라고 한다(Commend 패턴)
		Board result = service.write(board);
		return new ModelAndView("redirect:/board/read?bno=" + result.getBno());
	}
	//글읽기
	@GetMapping("/board/read")
	public ModelAndView read(@RequestParam Integer bno) {
		return new ModelAndView("board/read").addObject("map", service.read(bno));
	}
	
	//글목록
	@GetMapping("/board/list")
	public ModelAndView list() {
		return new ModelAndView("board/list").addObject("list", service.list());
	}
	
	//글변경
	@PostMapping("/board/update")
	public ModelAndView update(@ModelAttribute Board board) {
		service.update(board);
		return new ModelAndView("redirect:/board/read?bno=" + board.getBno());
	}
	
	//글삭제
	@PostMapping("/board/delete")
	public ModelAndView delete(@RequestParam Integer bno, @RequestParam String password ) {
		service.delete(bno,password);
		return new ModelAndView("redirect:/board/list");
	}
	
	/*
	public ModelAndVeiw list() {
		//request.setAttribute("list", service.list());								model
		//RequestDispatcher rd = request.getRequestDispatcher("/board/aaa.jsp");	view
		//re.forward(request, response);											forward
		
		return new ModelAndView("board/aaa").addObject("list", service.list());
		return new ModelAndView("redirect:/board/aaa");
	}
	*/
}
