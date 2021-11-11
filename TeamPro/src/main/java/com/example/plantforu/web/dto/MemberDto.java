package com.example.plantforu.web.dto;

import java.time.*;

import javax.validation.constraints.*;

import org.springframework.format.annotation.*;
import org.springframework.web.multipart.*;

import com.example.plantforu.domain.member.entity.*;
import com.example.plantforu.util.validation.annotation.*;

import lombok.*;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class MemberDto {
	@Data
	@AllArgsConstructor
	public static class Join {
		@NotNull
		@Username
		private String username;
		
		@NotNull
		@Irum
		private String irum;
		
		@NotNull
		@Email
		private String email;
		
		@NotNull
		@Password
		private String password;
		
		public Member toEntity() {
			return Member.builder().username(username).irum(irum).email(email).password(password).build();
		}
	}

	@Data
	public class Update {
		@NotNull
		@Email
		private String email;
		
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
		@Username
		private String username;
		
		@NotNull
		@Email
		private String email;
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
