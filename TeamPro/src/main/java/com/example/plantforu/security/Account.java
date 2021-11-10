package com.example.plantforu.security;

import java.util.*;

import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;

import com.example.plantforu.entity.member.Member;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account implements UserDetails {
	private String useremail;
	private String password;
	private boolean isEnabled;
	@Getter
	private Collection<GrantedAuthority> authorities;
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return useremail;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
}
