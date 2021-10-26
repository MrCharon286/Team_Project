package com.example.plantforu.repository;

import java.util.*;

import org.springframework.data.jpa.repository.*;

import com.example.plantforu.entity.product.*;

public interface ProductImageRepository extends JpaRepository<ProductImage, ProductImageId> {

	List<ProductImage> findByPnoOrderByPimagenoDesc(Integer pno);

	Optional<ProductImage> findById(Integer pimageno);

}
