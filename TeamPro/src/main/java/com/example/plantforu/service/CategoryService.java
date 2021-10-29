package com.example.plantforu.service;

import java.util.*;
import java.util.stream.*;

import javax.annotation.*;
import javax.persistence.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import com.example.plantforu.controller.dto.*;
import com.example.plantforu.entity.product.*;
import com.example.plantforu.repository.*;
import com.querydsl.jpa.impl.*;

import lombok.*;

@RequiredArgsConstructor
@Service
public class CategoryService {
	private final EntityManager em;
	private final CategoryRepository dao;
	private JPAQueryFactory factory;
	
	@PostConstruct
	public void init() {
		factory = new JPAQueryFactory(em);
	}
	
	@Transactional
	public void add(CategoryDto.Add dto) {
		Category category = dto.toEntity();
			dao.save(category);
	}
	
	// update할 때 @Transactional이 없으면 TransactionRequiredException
	@Transactional			
	public void updateCategory(CategoryDto.Update dto) {
		QCategory category = QCategory.category;
		factory.update(category).where(category.ctgno.eq(dto.getCtgno())).set(category.ctgName, dto.getCtgName()).execute();
	}
	
	public void deleteCategory(Integer ctgno) {
		dao.deleteById(ctgno);
	}

	public List<Category> readCategoryAll() {
		// CategoryRepository의 findAll()을 이용하면 결과가 14개가 나간다
		// Eager left outer join -> 중분류 select 모두 -> 소분류 select 모두
		
		List<Category> categories = new ArrayList<>();
		
		return categories;
	}
}
