/*
프사			선택 입력
아이디		필수 입력
이름			필수 입력
비밀번호		필수 
비밀번호 확인	필수
이메일		필수
생일			필수

※ 오류 처리 단계
1. 필수인 경우 에러 메시지 : "필수 입력입니다" 에러 메시지
2. 패턴 테스트
	아이디 : 대문자, 숫자 8~10자
	이름 : 한글 2~10자
	비밀번호 : 영숫자/특수문자 8~10자. 특수문자 하나이상
	비밀번호 확인 : 비밀번호와 일치
	이메일 : 이메일 형식 확인
	생일 : 숫자 4 - 숫자 2 - 숫자 2
3. 사용가능 확인
	아이디, 이메일은 ajax로 서버에 사용가능 여부 확인
*/

// 오류메시지 출력 함수 : (value, pattern, message, element) -> 입력값, 패턴, 오류 메시지, 오류메시지를 출력할 element
const check = (value, pattern, message, element)=>{
	if(value=="") {
		element.text("필수 입력입니다").attr("class", "fail");
		return false;
	}	
	if(pattern.test(value)==false) {
		element.text(message).attr("class", "fail");
		return false;
	}
	return true;
}

const useremailCheck = ()=>{
	const $useremail = $("#useremail").val();
	// /i는 case insensitive, g는 global(한번 찾고 마무리하지말고 다 찾아라)
	// 정규식을 사용하는 대표적인 경우 두 가지는 test(), replace()
	const pattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
	return check($useremail, pattern, "정확한 이메일을 입력하세요", $("#useremail_msg"))
}
const usertelCheck = ()=>{
	$("#usertel_msg").text("");
	const $usertel = $("#usertel").val();
	const pattern = /^[0-9]{3}-[0-9]{4}-[0-9]{4}$/;
	return check($usertel, pattern, "정확한 전화번호를 입력하세요", $("#usertel_msg"))
}

const userirumCheck = ()=>{
	// 에러메시지를 지운다
	$("#userirum_msg").text("");
	const $value = $("#userirum").val();
	// 정규식으로 문자열 패턴을 지정. 자바스크립트는 정규식을 //로 감싼다
	// ^는 시작, $는 끝. 만약 [가-힣]{2,10} 패턴일 경우 "ab우리cdefg"로 통과한다
	const pattern = /^[가-힣]{3,5}$/; 
	return check($value, pattern, "이름은 한글 3~5자입니다", $("#userirum_msg"));
}

const passwordCheck = () => {
	$("#password_msg").text("");
	const $password = $("#password").val();
	
	// 정규식에서 ()는 독립된 조건
	// ?=는 앞에서 부터 찾아라(전방 탐색)
	// .은 임의의 한글자
	// *는 앞글자가 0개이상, +는 앞글자가 1개이상 -> 특수문자가 1글자 이상
	const pattern = /^(?=.*[!@#$%^&*])^[A-Za-z0-9!@#$%^&*]{8,10}$/;
	return check($password, pattern, "비밀번호는 영숫자와 특수문자 8~10자입니다", $("#password_msg"));	
}
const password2Check = () => {
	$("#password2_msg").text("");
	const $password2 = $("#password2").val();
	if($password2=="") {
		$("#password2_msg").text("필수입력입니다").attr("class","fail");
		return false;
	} 
	if($password2!==$("#password").val()) {
		$("#password2_msg").text("비밀번호가 일치하지 않습니다").attr("class","fail");
		return false;
	}
	return true;
}


// 실제로 회원 가입을 수행하는 함수 : 사용자가 입력한 데이터를 가지고 FormData 객체를 만들어 ajax 요청을 보낸다
const join = () =>{
	// multipart/form-data 형식을 지정해야한다
	// 폼인 경우 : <form enctype="multipart/form-data">
	// 자바스크립트의 경우 FormData 내장 객체를 사용한다
	//		1. new FormData($("폼아이디")) - 폼 전제를 FormData로
	//		2. const formData = new FormData(); -> formData.append()로 하나씩 추가
	
	const formData = new FormData($("#join_form")[0]);
	
	// FormData에 담긴 값을 출력해보려면 of 반복문을 써야만 한다
	for(const key of formData.keys())
		console.log(key);
	for(const value of formData.values()) 
		console.log(value);
	
	// processData : $.ajax는 자바스크립트 객체를 urlencoded로 자동 변환 -> false를 줘서 금지
	// contentType : false면 multipart/form-data
	$.ajax({
		url: "/member/new_join",
		method: "post",
		data: formData,
		processData: false,
		contentType: false
	}).done(()=>Swal.fire("가입신청 완료","이메일을 확인하세요", "success"))
	.fail((msg)=>Swal.fire('가입신청 실패', msg,'error'));
}

$(document).ready(()=>{
	
	// 아이디를 입력하면 usernameCheck 후 서버에 사용가능여부를 확인
	$("#useremail").on("blur", ()=>{
		// 아이디에 대해 필수 입력 체크, 패턴 체크
		if(useremailCheck()==false)
			return false;
		$.ajax("/member/useremail/check?useremail=" + $("#useremail").val())
			.done(()=>$("#useremail_msg").text("사용가능한 이메일입니다").attr("class", "success"))
			.fail(()=>$("#useremail_msg").text("사용중인 이메일입니다").attr("class", "fail"));
	});
	$("#usertel").on("blur", ()=>{
		if(usertelCheck()==false)
			return false;
		$.ajax("/member/usertel/check?usertel=" + $("#usertel").val())
			.done(()=>$("#usertel_msg").text("사용할 수 있는 전화번호입니다").attr("class", "success"))
			.fail(()=>$("#usertel_msg").text("사용중인 전화번호입니다").attr("class", "fail"));
	});
		
	$("#useremail").on("blur", useremailCheck);
	$("#usertel").on("blur", usertelCheck);
	$("#userirum").on("blur", userirumCheck);
	$("#password").on("blur", passwordCheck);
	$("#password2").on("blur", password2Check);
	
	// 입력 오류 메시지가 떠도 가입 버튼을 누르면 가입 신청 처리가 된다
	// 프로그래밍한 순서대로 실행되는 백엔드와 달리 프론트는 사용자 마음대로 실행할 수 있다
	// -> 프로그래머가 두번, 세번 확인하면서 처리해야 한다
	$("#join").on("click", ()=>{
		// join을 호출하기 전에 6개를 입력했고 패턴을 통과하는 지 테스트
		const r1 = useremailCheck();
		const r2 = passwordCheck();
		const r3 = password2Check();
		const r4 = userirumCheck();
		if((r1 && r2 && r3 && r4) == false)
			return false;

		
		// jQuery의 $.when을 이용해 여러개의 ajax를 한꺼번에 처리하자
			$.when($.ajax("/member/useremail/check?useremail="+$("#useremail").val()), 
			$.ajax("/member/usertel/check?usertel="+$("#usertel").val()))
			.done(()=>join())
			.fail(()=>Swal.fire("실패", "이메일이나 전화번호가 사용중입니다", "error"));		
	});
});