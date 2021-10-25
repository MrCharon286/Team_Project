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
public class ReviewService {
	private final ReviewRepository reviewDao;
	private final ProductRepository productDao; 
	
	public List<Review> getReviewList(String loginId) {
		return reviewDao.findByUsernameOrderByRnoDesc(loginId);
	}


	@Transactional
	public void writeReview(ReviewDto.Write dto, String loginId) {
		Product product = productDao.findById(dto.getPno()).get();
		Review review = Review.builder().product(product).rcontent(dto.getRcontent()).rating(dto.getRating()).build();
		product.addReview(review);
	}
}
