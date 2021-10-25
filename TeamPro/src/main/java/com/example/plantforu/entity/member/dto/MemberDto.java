package com.example.plantforu.entity.member.dto;

import javax.validation.constraints.*;
import org.springframework.format.annotation.*;
import com.example.plantforu.entity.member.Member;
import com.example.plantforu.util.validation.annotation.Password;
import com.example.plantforu.util.validation.annotation.Useremail;
import com.example.plantforu.util.validation.annotation.Username;
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
		@Username
		private String username;
		
		@NotNull
		@Password
		private String password;
		
		@NotNull
		@Usertel
		private String usertel;

		public Member toEntity() {
			return Member.builder().useremail(useremail).username(username).password(password).usertel(usertel).build();
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
		private String useremail;
		
		@NotNull
		@Email
		private String usertel;
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
