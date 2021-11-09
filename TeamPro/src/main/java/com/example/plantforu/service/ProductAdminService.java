package com.example.plantforu.service;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.jsoup.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.util.*;
import org.springframework.web.multipart.*;

import com.example.plantforu.controller.dto.*;
import com.example.plantforu.entity.product.*;
import com.example.plantforu.repository.*;
import com.example.plantforu.util.*;

import lombok.*;

@RequiredArgsConstructor
@Service
public class ProductAdminService {
	private final ProductRepository dao;
	
	public CKResponse ckImageUpload(MultipartFile upload) {
		if(upload!=null && upload.isEmpty()==false && upload.getContentType().toLowerCase().startsWith("image/")) {
			String imageName = UUID.randomUUID().toString() + PlantforuUtil.getImageMultipartExtension(upload);
			File file = new File(PlantforuConstant.TEMP_FOLDER, imageName);
			try {
				upload.transferTo(file);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			return new CKResponse(1, imageName, PlantforuConstant.PRODUCT_URL + imageName);
		}
		return null;
	}

	public Product add(ProductDto.Create dto) {
		Product product = dto.toEntity();
		MultipartFile pimagefile = dto.getPimagefile();
		MultipartFile pdetailfile = dto.getPdetailfile();
		
		String pimage = PlantforuUtil.savePimage(pimagefile, product.getPname());
		String pdetail = PlantforuUtil.savePdetail(pdetailfile, product.getPname());
		
		product.addProductInfo(pimage, pdetail);
		System.out.println(product);
		return dao.save(product);
	}
		
	// 이 메서드는 ProductService.read와 같다
	@Transactional(readOnly=true)
	public Product read(Integer pno) {
		Product product = dao.findById(pno).orElseThrow(PlantforuException.ProductNotFoundException::new);
		product.setPimage(PlantforuConstant.PRODUCT_URL + product.getPimage());
		product.setPdetail(PlantforuConstant.PRODUCT_URL + product.getPdetail());
		return product;
	}
}
