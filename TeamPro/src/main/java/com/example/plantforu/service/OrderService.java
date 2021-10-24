package com.example.plantforu.service;

import java.util.*;

import javax.servlet.http.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.example.plantforu.controller.dto.*;
import com.example.plantforu.entity.order.*;
import com.example.plantforu.entity.product.*;
import com.example.plantforu.repository.*;
import com.example.plantforu.util.*;

import lombok.*;

@RequiredArgsConstructor
@Service
public class OrderService {
	private final AddressRepository addressDao;
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
				OrderItem orderItem = cart.toOrderItem();
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
		OrderItem orderItem = OrderItem.builder().pno(dto.getPno()).name(product.getPname()).price(product.getPprice())
			.count(dto.getPcount()).orderItemPrice(dto.getPcount()*product.getPprice()).image(PlantforuConstant.PRODUCT_URL+product.getPimage()).build();
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
	
	@Transactional
	@SuppressWarnings("unchecked")
	public void payment(String select, Integer ano, String loginId) {
		select = select.equals("product") ? "product" : "cart";
		HttpSession session = PlantforuUtil.getSession();
		
		List<OrderItem> orders = (List<OrderItem>)session.getAttribute(select);
		
		Address address = addressDao.findById(new Aname(loginId, ano)).orElseThrow(PlantforuException.AddressNotFoundException::new);
		Order order = Order.builder().address(address).deliveryStatus(DeliveryStatus.PAY).build();
		
		// Address.builder().addressNo(1).username("spring").build()와 같이 파라미터를 만들어 넘기면
		// JPA는 그 Address가 존재한다는 사실을 모름으로 새로운 주소라고 판단한다 -> 그런데 CascadeType이 없어서 아래 예외 발생
		// object references an unsaved transient instance - save the transient instance before flushing
		// Order order = Order.builder().address(Address.builder().addressNo(addressNo).username(loginId).build()).build();
		orders.forEach(orderItem->order.addOrderItem(orderItem));
		orderDao.save(order);
	}

	@Transactional(readOnly=true)
	public List<Order> readAll(String loginId) {
		return orderDao.readAll(loginId);
	}

	@Transactional(readOnly=true)
	public List<OrderDto.ReviewAvailable> reviewAvailableList(String loginId) {
		return orderDslDao.reviewAvailableList(loginId);
	}

	@Transactional(readOnly=true)
	public OrderDto.ReviewInfo getOrderItem(Integer orderNo, Integer orderItemNo) {
		return orderDslDao.getOrderItem(orderNo, orderItemNo);
	}

	@Transactional
	public void setIsReviewAvailableToFalse(Integer orderNo, Integer orderItemNo) {
		orderDao.findById(orderNo).orElseThrow(PlantforuException.OrderNotExistException::new).getOrderItems().stream().filter(item->item.getOrderItemNo()==orderItemNo).findFirst().orElseThrow(PlantforuException.OrderItemNotExistException::new).setIsReviewAvailable(false);
	}
}


