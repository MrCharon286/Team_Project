package com.example.plantforu.entity.seller;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SellerRepository extends CrudRepository<Seller, String> {
	boolean existsByUseremail(String sellerid);

	boolean existsByUsername(String sname);

	Optional<Seller> findByCheckcode(String checkcode);

	@Query("select s.seller from Member s where s.sname=:sname")
	Optional<String> findUsernameByEmail(String email);

}