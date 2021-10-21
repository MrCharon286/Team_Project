package com.example.plantforu.repository;

import java.util.*;

import org.springframework.data.jpa.repository.*;

import com.example.plantforu.entity.product.*;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	// 대분류 읽어오기
	@Query("select c.ctgno, c.ctgName from Category c where c.ctgno2 is null")	
	public List<Object[]> readAllMajorCategory();
	
	// 카테고리를 받아서 자식 카테고리 읽어오기
	@Query("select c.ctgno, c.ctgName from Category c where c.ctgno2=?1")
	public List<Object[]> readAllChildCategory(Category category);
}
