package com.example.plantforu.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.plantforu.entity.seller.SellerAuthority;
import com.example.plantforu.entity.seller.SellerAuthorityId;

public interface SellerAuthorityRepository extends CrudRepository<SellerAuthority, SellerAuthorityId> {

}
