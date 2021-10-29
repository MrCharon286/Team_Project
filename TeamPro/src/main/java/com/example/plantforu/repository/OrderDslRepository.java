package com.example.plantforu.repository;

import java.time.format.*;
import java.util.List;

import javax.annotation.*;
import javax.persistence.*;

import org.springframework.stereotype.*;

import com.example.plantforu.controller.dto.*;
import com.example.plantforu.controller.dto.OrderDto.*;
import com.example.plantforu.entity.cart.*;
import com.example.plantforu.entity.order.QOrder;
import com.example.plantforu.entity.order.QOrderItem;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.*;
import com.querydsl.jpa.impl.*;

import lombok.*;

@RequiredArgsConstructor
@Repository
public class OrderDslRepository {
	private final EntityManager entityManager;
	private JPAQueryFactory factory;
	private QOrder order;
	private QOrderItem orderItem;
	
	@PostConstruct
	public void init() {
		this.factory = new JPAQueryFactory(entityManager);
		this.order = QOrder.order;
		this.orderItem = QOrderItem.orderItem;
	}
	
	public List<isReviewAvailable> reviewAvailableList(String loginId) {
		return factory.select(Projections.constructor(OrderDto.isReviewAvailable.class, order.ono, order.createTime, orderItem.pimage, orderItem.pname, orderItem.orderItemNo))
			.from(order).innerJoin(order.orderItems, orderItem).where(order.username.eq(loginId).and(orderItem.isReviewAvailable.eq(true))).orderBy(order.createTime.desc()).fetch();
	}

	public OrderDto.ReviewInfo getOrderItem(Integer orderNo, Integer orderItemNo) {
		Tuple tuple = factory.select(order.ono, order.createTime, orderItem.pimage, orderItem.pname, orderItem.orderItemNo)
			.from(order).innerJoin(order.orderItems, orderItem).where(orderItem.orderItemNo.eq(orderItemNo)).fetchOne();
		if(tuple==null)
			return null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
		return new OrderDto.ReviewInfo(dtf.format(tuple.get(order.createTime)), tuple.get(orderItem.pname), tuple.get(order.ono), tuple.get(order.ono), tuple.get(orderItem.orderItemNo));
	}
}
