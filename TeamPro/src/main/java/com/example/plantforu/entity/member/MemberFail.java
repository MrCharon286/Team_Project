package com.example.plantforu.entity.member;

import lombok.*;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class MemberFail {
	public static class UsernameExistException extends RuntimeException {
	}

	public static class EmailExistException extends RuntimeException {
	}

	public static class JoinFailException extends RuntimeException {
	}

	public static class CheckcodeNotFoundException extends RuntimeException {
	}

	public static class MemberNotFoundException extends RuntimeException {
	}

	public static class PasswordCheckException extends RuntimeException {
	}

	public static class JoinCheckFailException extends RuntimeException {
	}
}