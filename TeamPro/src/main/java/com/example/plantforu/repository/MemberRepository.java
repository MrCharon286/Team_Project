package com.example.plantforu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.plantforu.entity.member.Member;

public interface MemberRepository extends CrudRepository<Member, String> {

	Optional<Member> findByCheckcode(String checkcode);

	@Query("select m.useremail from Member m where m.usertel=:usertel")
	Optional<String> findUseremailByUsertel(String usertel);

	List<Member> findByCheckcodeIsNotNull();

	boolean existsByUseremail(String useremail);

	boolean existsByUsertel(Integer usertel);
}