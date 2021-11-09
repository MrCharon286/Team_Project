package com.example.plantforu;

import static org.junit.Assert.*;

import java.util.*;

import javax.transaction.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.*;

import com.example.plantforu.entity.product.*;
import com.example.plantforu.repository.*;

@SpringBootTest
public class ProductTest {
	@Autowired
	private ProductRepository dao;
	
	// @Test
	public void initTest() {
		assertNotNull(dao);
	}
	
	@Test
	public void insertTest() {
		for(int i=43; i<=60; i++) {
			dao.save(Product.builder().pno(i).pdetail("aaa"+i).pimage("bbb"+i).pprice(2000).pname(i+"번 상품").category(Category.음료).build());
		}
	}
	
	// @Test
	public void selectTest() {
		//System.out.println(dao.findById(1));
		System.out.println(dao.findAll());
	}
	
	//@Transactional
	@Test
	public void deleteTest() {
		dao.deleteById(48);
	}
}
