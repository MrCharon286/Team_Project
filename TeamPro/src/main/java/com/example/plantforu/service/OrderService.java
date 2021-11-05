package com.example.plantforu.service;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.example.plantforu.controller.dto.*;
import com.example.plantforu.controller.dto.OrderDto.OrderProduct;
import com.example.plantforu.entity.cart.*;
import com.example.plantforu.entity.product.*;
import com.example.plantforu.repository.*;
import com.example.plantforu.util.*;

import lombok.*;

@RequiredArgsConstructor
@Service
public class OrderService {
	private final ProductRepository productDao;
	private final OrderRepository orderDao;
	private final OrderDslRepository orderDslDao;
	
	// 장바구니 주문 처리
	@SuppressWarnings("unchecked")
	public void orderCart(List<Integer> pnos) {
		HttpSession session = PlantforuUtil.getSession();
		List<Cart> carts = (List<Cart>)session.getAttribute("carts");
		List<OrderItem> orders = new ArrayList<>();
		for(Cart cart:carts) {
			if(pnos.contains(cart.getPno())) {
				OrderItem orderItem = cart.toCartOrderItem();
				System.out.println(orderItem);
				orders.add(orderItem);
			}
		}
		session.setAttribute("cart", orders);
	}
	
	// 상품 선택하고 주문 처리
	public void orderProduct(OrderDto.OrderProduct dto) {
		HttpSession session = PlantforuUtil.getSession();
		Product product = productDao.findById(dto.getPno()).orElseThrow(PlantforuException.ProductNotFoundException::new);
		OrderItem orderItem = OrderItem.builder().pno(dto.getPno()).pname(product.getPname()).pprice(product.getPprice())
			.pcount(dto.getPcount()).orderItemPrice(dto.getPcount()*product.getPprice()).pimage(PlantforuConstant.PRODUCT_URL+product.getPimage()).build();
		session.setAttribute("product", Arrays.asList(orderItem));
	}

	// select 파라미터가 product면 상품을, cart면  주문 목록을 리턴
	@SuppressWarnings("unchecked")
	public List<OrderItem> getOrders(String select) {
		HttpSession session = PlantforuUtil.getSession();
		if(select.equals("product")) {
			// 리턴 타입이 List<OrderItem>이므로 리스트로 변환해 리턴
			return (List<OrderItem>)session.getAttribute("product");
		}
		return (List<OrderItem>)session.getAttribute("cart");
	}
	/*
	@Transactional(readOnly=true)
	public List<Order> readAll(String loginId) {
		return orderDao.readAll(loginId);
	}
	*/
	
}


