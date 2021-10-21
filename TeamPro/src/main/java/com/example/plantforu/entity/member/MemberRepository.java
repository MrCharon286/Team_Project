package com.example.plantforu.entity.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String> {
	boolean existsByUseremail(String useremail);

	boolean existsByUsername(String username);

	Optional<Member> findByCheckcode(String checkcode);

	@Query("select m.useremail from Member m where m.username=:username")
	Optional<String> findUsernameByEmail(String email);


}