package com.example.plantforu.repository;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;

import com.example.plantforu.entity.order.*;

public interface AddressRepository extends CrudRepository<Address, AddressId> {
	@Query("select a from Address a where a.username=:useremail and a.isDefault=true")
	public Address findDefault(String useremail);

	public List<Address> findByUseremail(String useremail);

	public boolean existsByUseremail(String useremail);

	@Modifying
	@Query("update Address a set a.isDefault=false where a.useremail=?1")
	public void setDefaultToFalse(String useremail);

	public Address findByAno(Integer ano);

}
