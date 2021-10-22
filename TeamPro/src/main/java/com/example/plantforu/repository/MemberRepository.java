package com.example.plantforu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.plantforu.entity.member.Member;

public interface MemberRepository extends CrudRepository<Member, String> {
	boolean existsByUseremail(String useremail);

	boolean existsByUsername(String username);

	@Query("select m.useremail from Member m where m.username=:username")
	Optional<String> findUsernameByEmail(String email);


}