package com.example.plantforu.repository;

import java.util.*;

import org.springframework.data.jpa.repository.*;

import com.example.plantforu.entity.product.*;

public interface ReviewRepository extends JpaRepository<Review, ReviewId> {

	List<Review> findByUsernameOrderByRnoDesc(String loginId);

	List<Review> findByPnoOrderByRnoDesc(Integer pno);
}
