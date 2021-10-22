package com.example.plantforu.repository;

import java.util.*;

import org.springframework.data.jpa.repository.*;

import com.example.plantforu.entity.product.*;

public interface QnaRepository extends JpaRepository<Qna, QnaId> {

	List<Qna> findByUsernameOrderByQnoDesc(String loginId);

	List<Qna> findByPnoOrderByQnoDesc(Integer pno);
}
