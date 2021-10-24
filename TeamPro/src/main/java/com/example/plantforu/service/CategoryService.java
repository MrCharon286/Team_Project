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
		// 추가할 category의 부모 카테고리를 찾아 자식으로 추가
		if(dto.getSuperCtgno()!=null && dto.getSuperCtgno().equals("")==false) {
			Category superCategory = dao.findById(dto.getSuperCtgno()).get();
			superCategory.addCategory(category);
		}
		// 부모 카테고리가 없다면(대분류라면) 그냥 추가
		else 
			dao.save(category);
	}
	
	// 카테고리를 파라미터로 자식 카테고리를 출력 - 상품 추가 화면에서 대분류 -> 중분류 -> 소분류를 선택할 때 사용
	@Transactional(readOnly=true)
	public List<CategoryDto.Read> readCategory(Integer ctgno) {
		List<Object[]> list = ctgno==null? dao.readAllMajorCategory() : dao.readAllChildCategory(Category.builder().ctgno(ctgno).build());
		
		// 47라인에서 읽어온 카테고리 코드와 카테고리 이름들을 CategoryDto.Read의 리스트로 만들어 리턴
		return list.stream().map(ar->new CategoryDto.Read((Integer)ar[0], (String)ar[1])).collect(Collectors.toList());
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
		
		// 대분류를 읽어온다
		List<Object[]> list = dao.readAllMajorCategory();
		list.forEach(ar-> categories.add(Category.builder().ctgno((Integer)ar[0]).ctgName((String)ar[1]).build()));
		
		for(int i=0; i<categories.size(); i++) {
			// 각 대분류의 자식 중분류들을 읽어와 대분류에 추가한다
			List<Category> subCategories = new ArrayList<>();
			list = dao.readAllChildCategory(Category.builder().ctgno(categories.get(i).getCtgno()).build());
			list.forEach(ar-> subCategories.add(Category.builder().ctgno((Integer)ar[0]).ctgName((String)ar[1]).build()));
			categories.get(i).setCategories(subCategories);
			
			for(int j=0; j<categories.get(i).getCategories().size(); j++) {
				// 각 중분류의 자식 소분류들을 읽어와 중분류에 추가한다
				List<Category> desendescendantCategories = new ArrayList<>();
				list = dao.readAllChildCategory(Category.builder().ctgno(subCategories.get(j).getCtgno()).build());
				list.forEach(ar-> desendescendantCategories.add(Category.builder().ctgno((Integer)ar[0]).ctgName((String)ar[1]).build()));
				categories.get(i).getCategories().get(j).setCategories(desendescendantCategories);
			}
		}
		return categories;
	}
}
