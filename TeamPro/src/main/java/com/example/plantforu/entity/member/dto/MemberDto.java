package com.example.plantforu.entity.member.dto;

import javax.validation.constraints.*;
import com.example.plantforu.entity.member.Member;
import com.example.plantforu.util.validation.annotation.Password;
import com.example.plantforu.util.validation.annotation.Useremail;
import com.example.plantforu.util.validation.annotation.Userirum;
import com.example.plantforu.util.validation.annotation.Usertel;

import lombok.*;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class MemberDto {
	@Data
	@AllArgsConstructor
	public static class Join {
		@NotNull
		@Useremail
		private String useremail;
		
		@NotNull
		@Userirum
		private String userirum;
		
		@NotNull
		@Password
		private String password;
		
		@NotNull
		@Usertel
		private Integer usertel;

		public Member toEntity() {
			return Member.builder().useremail(useremail).userirum(userirum).password(password).usertel(usertel).build();
		}
	}

	@Data
	public class Update {
		@NotNull
		@Userirum
		private String useremail;
		
		@Password
		private String password;
		
		@Password
		private String newPassword;
		
		public boolean passwordCheck() {
			if(this.password!=null && this.newPassword!=null) 
				return this.password.equals(this.newPassword);
			return false;
		}
	}
	
	@Data
	public class ResetPwd {
		@NotNull
		@Userirum
		private String useremail;
		
		@NotNull
		@Useremail
		private Integer useretel;
	}
	
	@Data
	public class ChangePwd {
		@NotNull
		@Password
		private String password;
		
		@NotNull
		@Password
		private String newPassword;
	}
}
