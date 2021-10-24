package com.example.plantforu.service;

import java.util.*;

import javax.transaction.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import com.example.plantforu.controller.dto.*;
import com.example.plantforu.entity.product.*;
import com.example.plantforu.repository.*;

import lombok.*;

@RequiredArgsConstructor
@Service
public class QnaService {
	private final QnaRepository qnaDao;
	private final ProductRepository productDao; 
	
	public List<Qna> getQnaList(String loginId) {
		return qnaDao.findByUsernameOrderByQnoDesc(loginId);
	}


	@Transactional
	public void writeQna(QnaDto.Write dto, String loginId) {
		Product product = productDao.findById(dto.getPno()).get();
		Qna qna = Qna.builder().product(product).qtitle(dto.getQtitle()).qcontent(dto.getQcontent()).build();
		product.addQna(qna);
	}
}
