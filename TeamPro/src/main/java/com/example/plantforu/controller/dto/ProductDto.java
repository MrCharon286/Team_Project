package com.example.plantforu.controller.dto;

import java.time.*;
import java.util.*;

import org.springframework.web.multipart.*;

import com.example.plantforu.entity.product.*;
import com.example.plantforu.util.*;
import com.fasterxml.jackson.annotation.*;

import lombok.*;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class ProductDto {
	// 상품 목록을 요청하는 DTO
	@Data
	public static class ForList {
		private Integer pageno;
		private Category category;
		private String fieldName;
		private Boolean isAsc;
		public ForList(Integer pageno, Category category, String fieldName, Boolean isAsc) {
			if(pageno==null)
				this.pageno = 1;
			if(fieldName==null)
				this.fieldName = "pno";
			if(isAsc==null)
				this.isAsc = false;
		}
	}
	
	// 상품 목록을 응답하는 DTO 
	@Data
	@AllArgsConstructor
	public static class ProductList {
		private Category category;
		private Integer pno;
		private String pname;
		private Integer pprice;
	}
	
	@Data
	@AllArgsConstructor
	public static class Page {
		private List<ProductList> products;
		private Integer pageno;
		private Long totalcount;
		private Category category;
	}

	// 상품 추가를 요청하는 DTO
	@Data
	@AllArgsConstructor
	public static class Create {
		private Integer pno;
		private String pname;
		private MultipartFile pimage;
		private MultipartFile pdetail;
		private Integer pprice;
		private Category category;
		public Product toEntity() {
			return Product.builder().pname(pname).pprice(pprice).category(category).build();
		}
	}
	
	// JsonView 어노테이션을 이용해 DTO를 구성하기 위한 클래스
	@Data
	@AllArgsConstructor
	public class ReadForMember {
	}
	
	@Data
	@AllArgsConstructor
	public class ReadForAdmin {
	}
}
