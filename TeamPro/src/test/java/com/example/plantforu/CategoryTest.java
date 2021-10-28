package com.example.plantforu;

import static org.junit.Assert.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.*;

import com.example.plantforu.entity.product.*;
import com.example.plantforu.repository.*;

@SpringBootTest
public class CategoryTest {
	@Autowired
	private CategoryRepository dao;
	
	// @Test
	public void initTest() {
		assertNotNull(dao);
	}
	
	@Test
	public void test1() {
		for(int i=1; i<=5; i++) {
			dao.save(Category.builder().ctgno(i).ctgName(i+"호기").build());
		}
	}
}
