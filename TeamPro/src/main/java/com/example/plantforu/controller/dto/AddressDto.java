package com.example.plantforu.controller.dto;

import com.example.plantforu.entity.order.*;

import lombok.*;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class AddressDto {
	@Data
	public static class Add {
		private String useremail;
		private String anum;
		private String address1;
		private String address2;
		private Boolean isDefault;
		public Address toEntity() {
			return Address.builder().useremail(useremail).anum(anum).address1(address1).address2(address2).isDefault(isDefault).build();
		}
	}
}
