package com.example.plantforu.service;

import java.io.*;
import java.util.*;

import org.springframework.data.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.*;

import com.example.plantforu.controller.dto.*;
import com.example.plantforu.entity.product.*;
import com.example.plantforu.repository.*;
import com.example.plantforu.util.*;

import lombok.*;

@RequiredArgsConstructor
@Service
public class ProductService {
	private final ProductRepository dao;
	private final ProductDslRepository dslDao;
	
	// readOnly를 지정하면 읽기 전용이므로 EntityManager가 변경을 대비한 스냅샷을 저장하지 않아 메모리 사용량을 최적화 -> 수동으로 flush하지 않으면 업데이트되지 않는다
	@Transactional(readOnly=true)
	public ProductDto.Page list(ProductDto.ForList dto) {
		Pageable pageable = PageRequest.of(dto.getPageno()-1, PlantforuConstant.PRODUCT_PAGE_SIZE);
		List<ProductDto.ProductList> products = dslDao.list(pageable, dto.getCategory(), dto.getFieldName(), dto.getIsAsc());
		return new ProductDto.Page(products, dto.getPageno(), dslDao.countByPno(dto.getCategory()), dto.getCategory());
	}

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
		
		// 연습용 예제이므로 상품 이미지는 모두 default.jpg로 
		product.setPimage("imageDefault.jpg");
		product.setPdetail("detailDefault.jpg");
		System.out.println(product);
		return dao.save(product);
	}
	
	public Product read(Integer pno) {
		Product product = dao.findById(pno).orElseThrow(PlantforuException.ProductNotFoundException::new);
		product.setPimage(PlantforuConstant.PRODUCT_URL + product.getPimage());
		product.setPdetail(PlantforuConstant.PRODUCT_URL + product.getPdetail());
		return product;
	}
}
