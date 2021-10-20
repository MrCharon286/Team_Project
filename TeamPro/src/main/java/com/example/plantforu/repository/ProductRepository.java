package com.example.plantforu.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;

import com.example.plantforu.entity.product.*;

//페이징은 queryDsl로 처리할 것임. 따라서 JpaRepository가 아닌 CrudRepository를 상속
public interface ProductRepository extends CrudRepository<Product, Integer> {
	@Query("select p.pstock from Product p where pno=?1")
	public Integer readStockByPno(Integer pno);

	@Query("select p.pstock from Product p where pno=?1")
	public Integer readStock(Integer pno);
}
