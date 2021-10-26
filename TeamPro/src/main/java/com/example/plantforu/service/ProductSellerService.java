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
public class ProductSellerService {
	private final ProductRepository dao;
	private final ProductImageRepository imageDao;
	
	public CKResponse ckImageUpload(MultipartFile upload) {
		if(upload!=null && upload.isEmpty()==false && upload.getContentType().toLowerCase().startsWith("image/")) {
			String imageName = UUID.randomUUID().toString() + PlantforuUtil.getMultipartExtension(upload);
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
		String pdetail = dto.getPdetail();
		if(Objects.isNull(pdetail)==false) {
			Jsoup.parseBodyFragment(pdetail).getElementsByTag("img").forEach(img->{
				String imageUrl = img.attr("src");
				String imageName = imageUrl.substring(imageUrl.lastIndexOf("=")+1);
				File tempImage = new File(PlantforuConstant.TEMP_FOLDER, imageName);
				File image = new File(PlantforuConstant.PRODUCT_FOLDER, imageName);
				try {
					if(tempImage.exists()) {
						FileCopyUtils.copy(Files.readAllBytes(tempImage.toPath()), image);
						tempImage.delete();
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
			});
		}
		
		/* 
		 * product에 대표이미지로 쓸 image 컬럼을 생성해야 하는가?
		product.setImage("default.jpg");
		*/
		System.out.println(product);
		return dao.save(product);
		
	}

	// 이 메서드는 ProductService.read와 같다
	@Transactional(readOnly=true)
	public ProductImage read(Integer pimageno) {
		ProductImage productImage = imageDao.findById(pimageno).orElseThrow(PlantforuException.ProductNotFoundException::new);
		productImage.setPprofile(PlantforuConstant.PRODUCT_URL + productImage.getPimageno());
		return productImage;
	}
}
