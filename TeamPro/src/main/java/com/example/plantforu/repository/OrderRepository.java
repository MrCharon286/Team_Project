package com.example.plantforu.repository;

import java.util.*;

import org.springframework.data.jpa.repository.*;

import com.example.plantforu.entity.cart.*;

public interface OrderRepository extends JpaRepository<Order, Integer>{
	
}
