package com.example.plantforu.controller.dto;

import com.example.plantforu.entity.product.*;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryDto {
	@Data
	@AllArgsConstructor
	public static class Add {
		private Integer ctgno;
		private String ctgName;
		private Integer superCtgno;
		public Category toEntity() {
			return Category.builder().ctgno(ctgno).ctgName(ctgName).build();
		}
	}
	
	@Data
	@AllArgsConstructor
	public static class Read {
		private Integer ctgno;
		private String ctgName;
		public Category toEntity() {
			return Category.builder().ctgno(ctgno).ctgName(ctgName).build();
		}
	}
	
	@Data
	@AllArgsConstructor
	public static class Update {
		private Integer ctgno;
		private String ctgName;
	}
}
