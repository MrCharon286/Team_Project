package com.example.plantforu.repository;

import org.springframework.data.repository.*;

import com.example.plantforu.entity.product.*;

//페이징은 queryDsl로 처리할 것임. 따라서 JpaRepository가 아닌 CrudRepository를 상속
public interface ProductRepository extends CrudRepository<Product, Integer> {

}
