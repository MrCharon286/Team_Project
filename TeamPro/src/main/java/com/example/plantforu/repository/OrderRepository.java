package com.example.plantforu.repository;

import java.util.*;

import org.springframework.data.jpa.repository.*;

import com.example.plantforu.entity.order.*;

public interface OrderRepository extends JpaRepository<Order, Integer>{

	@Query("select o from Order o where o.address.useremail=?1 order by o.orderNo asc")
	public List<Order> readAll(String useremail);

}
