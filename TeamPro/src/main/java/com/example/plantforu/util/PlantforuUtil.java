package com.example.plantforu.util;

import javax.servlet.http.*;

import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.multipart.*;

public class PlantforuUtil {
	public static String getMultipartExtension(MultipartFile sajin) {
		int lastPositionOfDot = sajin.getOriginalFilename().lastIndexOf("."); 
		return sajin.getOriginalFilename().substring(lastPositionOfDot);
	}
	
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
