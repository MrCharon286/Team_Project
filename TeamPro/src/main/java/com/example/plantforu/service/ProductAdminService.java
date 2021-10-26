package com.example.plantforu.service;

import java.util.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.example.plantforu.controller.dto.*;
import com.example.plantforu.repository.*;

import lombok.*;

@RequiredArgsConstructor
@Service
public class ProductAdminService {
	private final ProductRepository productDao;
	private final ProductDslRepository productDslDao;
	
	@Transactional(readOnly=true)
	public List<ProductDto.DisApproved> disApprovedList() {
		return productDslDao.disApprovedList();
	}
	
	@Transactional
	public void setDisApprovedToApproved(Integer pno) {
		productDao.findById(pno).orElseThrow(PlantforuException.ProductNotFoundException::new).setPapproval(true);
	}
}
