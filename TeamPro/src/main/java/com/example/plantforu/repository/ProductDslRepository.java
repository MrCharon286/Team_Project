package com.example.plantforu.repository;

import java.util.*;

import javax.annotation.*;
import javax.persistence.*;

import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import com.example.plantforu.controller.dto.*;
import com.example.plantforu.controller.dto.ProductDto.*;
import com.example.plantforu.entity.product.*;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.*;

import lombok.*;

@RequiredArgsConstructor
@Repository
public class ProductDslRepository {
	// 엔티티의 관리하는 역할 수행
	private final EntityManager entityManager;
	// QueryDsl을 사용하여 JPQL 쿼리를 처리하는 역할
	private JPAQueryFactory factory;
	private QProduct product;
	
	@PostConstruct
	public void init() {
		this.factory = new JPAQueryFactory(entityManager);
		this.product = QProduct.product;
	}

	public List<ProductDto.ProductList> list(Pageable pageable, Integer ctgno, String fieldName, Boolean isAsc) {
		// 동적으로 정렬을 지정
		// new OrderSpecifier(isAsc==true? Order.ASC : Order.DESC, Expressions.path(Product.class, fieldName)))
		// 첫번째 파라미터는 오름차순/내림차순 지정 : 파라미터 타입이 Order. 그래서 Order.ASC 또는 Order.DESC로 타입을 맞춰준다
		// 두번째 파라미터는 정렬할 필드
		//		동적 표현식 생성을 위한 접근 경로는 Expression 객체
		//		Expressions는 접근 경로 생성을 도와주는 정적 팩토리 클래스
		//			파라미터로는 클래스와 그 클래스에서 사용할 필드명 
		return factory.from(product).select(Projections.constructor(ProductDto.ProductList.class, product.pno, product.pname, product.pprice, product.pavgOfRating, product.pcountOfRating))
			.where(withCategory(ctgno))
			.orderBy(new OrderSpecifier(isAsc==true? Order.ASC : Order.DESC, Expressions.path(Product.class, fieldName)))
			.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
	}
	
	public List<DisApproved> disApprovedList() {
		return factory.select(Projections.constructor(ProductDto.DisApproved.class, product.pno, product.pname, product.createTime))
			.from(product).where(product.papproval.eq(false)).orderBy(product.createTime.desc()).fetch();
	}
	
	// 복합 조건 : 복합 boolean 표현식은 BooleanExpression으로 작성한다. 보통 where절을 동적으로 생성할 때 사용
	// 아래와 같은 함수가 존재한다면 factory.selecgFrom(product).where(withAddress(address), withCategory(categoryCode)).fetch();
	// adddress, categoryCode 모두 null이 아니면 where address=? and categoryCode=?
	// address가 null이면 where categoryCode=?과 같은 방식으로 만들어진다
	
	/*
		private BooleanExpression weithAddress(String address) {
			if(address==null)
				return null;
			return product.address.eq(address);
		} 
	*/
	private BooleanExpression withCategory(Integer ctgno) {
		if(ctgno==null)
			return null;
		return product.ctgno.eq(ctgno);
	}


	public Long countByPno(Integer ctgno) {
		return factory.from(product).select(product.pno.count()).where(product.pno.gt(0), withCategory(ctgno)).fetchCount();
	}

}
