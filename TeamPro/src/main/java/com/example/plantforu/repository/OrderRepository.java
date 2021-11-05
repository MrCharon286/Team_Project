package com.example.plantforu.repository;

import java.util.*;

import org.springframework.data.jpa.repository.*;

import com.example.plantforu.entity.cart.*;

public interface OrderRepository extends JpaRepository<Order, Integer>{
	/*
	@Query("select o from Order o where o.address.username=?1 order by o.ono asc")
	public List<Order> readAll(String username);
	*/
}
