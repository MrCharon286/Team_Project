package com.example.plantforu.util;

import java.io.*;

import javax.servlet.http.*;

import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.multipart.*;

public class PlantforuUtil {
	// 상품 대표 사진의 확장자를 잘라내는 함수
	public static String getImageMultipartExtension(MultipartFile pimagefile) {
		int lastPositionOfDot = pimagefile.getOriginalFilename().lastIndexOf("."); 
		return pimagefile.getOriginalFilename().substring(lastPositionOfDot);
	}
	
	// 상품 정보 사진의 확장자를 잘라내는 함수
	public static String getDetailMultipartExtension(MultipartFile pdetailfile) {
		int lastPositionOfDot = pdetailfile.getOriginalFilename().lastIndexOf("."); 
		return pdetailfile.getOriginalFilename().substring(lastPositionOfDot);
	}
	
	// 상품 대표 사진을 저장하는 함수
	public static String savePimage(MultipartFile pimagefile, String pname) {
		if(pimagefile!=null && pimagefile.isEmpty()==false) {
			File file = new File(PlantforuConstant.PRODUCT_FOLDER, pname + PlantforuUtil.getImageMultipartExtension(pimagefile));
			try {
				// MultipartFile을 파일로 이동하는 함수. 이동하고 나면 MultipartFile은 사용이 불가능해진다
				pimagefile.transferTo(file);
				return file.getName();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return PlantforuConstant.DEFAULT_PIMAGE_NAME;
	}
	
	// 상품 정보 사진을 저장하는 함수
	public static String savePdetail(MultipartFile pdetailfile, String pname) {
		if(pdetailfile!=null && pdetailfile.isEmpty()==false) {
			File file = new File(PlantforuConstant.PRODUCT_FOLDER, pname + "상세" + PlantforuUtil.getDetailMultipartExtension(pdetailfile));
			try {
				// MultipartFile을 파일로 이동하는 함수. 이동하고 나면 MultipartFile은 사용이 불가능해진다
				pdetailfile.transferTo(file);
				return file.getName();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return PlantforuConstant.DEFAULT_PDETAIL_NAME;
	}
	
	// 이미지를 받아서 이미지의 종류를 리턴
	public static MediaType getMediaType(String fileName) {
		String extension = fileName.substring(fileName.lastIndexOf(".")).toUpperCase();
		MediaType type = MediaType.IMAGE_JPEG;
		if(extension.equals("PNG"))
			type = MediaType.IMAGE_PNG;
		else if(extension.equals("GIF"))
			type=MediaType.IMAGE_GIF;
		return type;
	}
	
	// @Valid 예외 처리가 자주 필요해서 함수로 분리 
	public static void bindingResultCheck(BindingResult bindingResult) throws BindException {
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
	}

	public static HttpSession getSession() {
		ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		return sra.getRequest().getSession();
	}
}
