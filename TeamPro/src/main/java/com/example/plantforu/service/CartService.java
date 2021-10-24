package com.example.plantforu.service;

import java.util.*;
import java.util.stream.*;

import javax.servlet.http.*;

import org.springframework.stereotype.*;

import com.example.plantforu.entity.order.*;
import com.example.plantforu.repository.*;
import com.example.plantforu.util.*;

import lombok.*;

@RequiredArgsConstructor
@Service
public class CartService {
	private final ProductRepository dao;
	
	@SuppressWarnings("unchecked")
	private List<Cart> getCarts() {
		HttpSession session = PlantforuUtil.getSession();
		if(session.getAttribute("carts")!=null) 
			return (List<Cart>)session.getAttribute("carts");
		return new ArrayList<>();
	}
	
	private void saveCarts(List<Cart> carts) {
		HttpSession session = PlantforuUtil.getSession();
		session.setAttribute("carts", carts);
	}

	public void add(Cart cart) {
		List<Cart> carts = getCarts();
		boolean isExist = false;
		for(Cart c:carts) {
			if(c.getPno()==cart.getPno()) {
				c.increase();
				isExist = true;
			}
		}
		if(isExist==false)
			carts.add(cart);
		
		saveCarts(carts);
	}

	public List<Cart> read() {
		return getCarts();
	}

	public List<Cart> increase(Integer pno) {
		List<Cart> carts = getCarts();
		for(Cart cart:carts) {
			if(cart.getPno()==pno) {
				Integer pcount = cart.getPcount()+1;
				Integer pstock = dao.readPstock(pno);
				if(pcount>=pstock)
					throw new PlantforuException.OutOfStockException();
				cart.increase();
			}
		}
		saveCarts(carts);
		return carts;
	}

	public List<Cart> decrease(Integer pno) {
		List<Cart> carts = getCarts();
		for(Cart cart:carts) {
			if(cart.getPno()==pno)
				cart.decrease();
		}
		saveCarts(carts);
		return carts;
	}

	public List<Cart> delete(List<Integer> pnos) {
		List<Cart> carts = getCarts();
		List<Cart> newCarts = carts.stream().filter(cart->pnos.contains(cart.getPno())==false).collect(Collectors.toList());
		saveCarts(newCarts);
		return newCarts;
	}
}

