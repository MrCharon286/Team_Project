package com.example.plantforu.service;


import java.util.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.example.plantforu.controller.dto.*;
import com.example.plantforu.entity.order.*;
import com.example.plantforu.repository.*;

import lombok.*;

@RequiredArgsConstructor
@Service
public class AddressService {
	private final AddressRepository dao;

	@Transactional(readOnly=true)
	public List<Address> readAddresses(String useremail) {
		return dao.findByUseremail(useremail);
	}

	public Integer add(AddressDto.Add dto, String useremail) {
		Address address = dto.toEntity().setUseremail(useremail);
		if(dao.existsByUseremail(useremail)==false) 
			address.setDefault(true);
		return dao.save(address).getAno();
	}


	
}
