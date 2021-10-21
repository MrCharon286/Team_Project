package com.example.plantforu.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.plantforu.entity.member.Authority;
import com.example.plantforu.entity.member.AuthorityId;

public interface AuthorityRepository extends CrudRepository<Authority, AuthorityId> {

}
