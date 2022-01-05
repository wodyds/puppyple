$(document).ready(function() {
	
	//유효성 검사 변수 선언 (했지만 적용이 안돼서 스프링에서 작업하기로...!!!! ★)
	var regId = /^[a-z]\w{4,11}$/;
//	var regPw = /^[]\w{4,11}$/;
	var regNickname = /^[a-z가-힣]{2,9}$/;
	var regPhone = /^(010|070|02|031|032|033|041|042|043|044|051|052|053|054|055|061|062|063|064)\d{4}\d{4}$/;
//	var regEmail = /^[a-z]\w{4,11}@[a-z]{2,10}[\.][a-z]{2,3}[\.]?[a-z]{0,2}$/;
	
	
	
	//submit 버든 클릭 했을때 
		$("#submit").click(function() {
			
			//입력란이 빈칸일 때 (작업 완료)
			// + 잘못된 입력칸 일 때 : 유효성검사  ← 스프링에서 하기
			if(!$("#member_id").val()){
				$(".fillId").show();
				$("#member_id").focus();
			}
			
			if(!$("#member_pw").val()){
				$(".fillPw").show();
				$("#member_pw").focus();
			}/*else if(!regPw.test($("#pw").val)){
				$(".pw_check").show();
				$("#pw").focus();
			} ← 스프링에서 하기 */
			
			if(!$("#pw_ck").val()){
				$(".fillPw2").show();
				$("#pw_ck").focus();
			}
			
			if(!$("#member_nickname").val()){
				$(".fillNickname").show();
				$("#member_nickname").focus();
			}
			
			if(!$("#member_phone").val()){
				$(".fillPhone").show();
				$("#member_phone").focus();
			}
			
			if(!$("#member_email").val()){
				$(".fillEmail").show();
				$("#member_email").focus();
			}
			
			return true;
		
		}); // submit click	
		
		
		// 눈 아이콘 클릭 시 패스워드 보이기
		$(".eye").click(function() {
		  $("#member_pw").toggleClass("active");
		  if($("#member_pw").hasClass("active") == true) {
		    $('.fa.fa-eye').attr('class', 'fa fa-eye-slash').css('margin-left','-1px');
		    $("#member_pw").attr("type", "text");
		  } else {
		    $('.fa.fa-eye-slash').attr('class', 'fa fa-eye').css('margin-left','0px');;
		    $("#member_pw").attr("type", "password");
		  }
		});
		
		
	
});	//ready



		
// 아래부터는 예전에 정호쌤이랑 수업시간에 했던 json, ajax 써서 했던 코드들 참고하려고 그냥 복붙한 것
		
/*		signupCheck = false;
		$.ajax({
			url : "loginCheck.json",
			type : "post",
			dataType : "json",
			async : false, 
			success : function(result) {
				$(result).each(function(key, value) {
						signupCheck = true;
				}); // each
			} // success
		}); // ajax
		if (signupCheck) {
			alert(id.value + "님 회원가입을 축하합니다. 로그인을 해주세요.");
			location.href = 'index.html';
			return false;
		} else {
			alert("회원가입 양식이 틀렸습니다. 제대로 작성해주십시오.");
			$("#name").focus();
			return false;
		}
	}); // submit click
	
*/
		
	
		
		
/*	$("#reset").click(function() {
		var loginCancel = confirm("회원가입을 취소하시겠습니까?");
		if (loginCancel) {
			alert("회원가입이 취소되었습니다. \n메인페이지로 이동합니다.");
			location.href = 'index.html';
			return false;
		} else {
			alert("회원가입이 계속됩니다.");
			return false;
		}
	}); // reset click
*/
/*	// 비밀번호 중복 확인
	$("#pw").click(function() {
		$("#pwCheckSpan").html("");
	});// click
	$("#pw_check").click(function() {
		$("#pwCheckSpan").html("");
	});// click
	// 밑에 일치함,일치하지않음 뜨는것들 클릭하면 지워지게 하려고 넣음
	$("#pw").keyup(function() { // keyup 타자 칠때마다 비교
		if ($("#pw").val() != $("#pw_check").val()) {
			$("#pwCheckSpan").html("비밀번호 불일치").css("color", "#A63628");
		} else {
			$("#pwCheckSpan").html("비밀번호 일치").css("color", "#1c1e59");
		}
	});// keyup
	$("#pw_check").keyup(function() {
		if ($("#pw").val() != $("#pw_check").val()) {
			$("#pwCheckSpan").html("비밀번호 불일치").css("color", "#A63628");
		} else {
			$("#pwCheckSpan").html("비밀번호 일치").css("color", "#1c1e59");
		}
	});// pwCheck keyup
	// 비밀번호 중복 확인 끝

	//아이디, 이메일 중복 체크 버튼 기능
	$("#id_check").click(function() {
		idCheck = false;
		$.ajax({
			url : "loginCheck.json",
			type : "post",
			dataType : "json",
			async : false,
			success : function(result) {
				$(result).each(function(key, value) {
					if ($("#id").val() == value.id) {
						idCheck = true;
					}
				}); // each
			} // success
		}); // ajax
		if (!$("#id").val()) {
			alert("아이디를 입력하세요.");
		} else if (idCheck) {
			alert("이미 존재하는 아이디입니다.");
		} else {
			alert("사용 가능한 아이디입니다.");
		}
	});
	
	$("#email_check").click(function() {
		emailCheck = false;
		$.ajax({
			url : "loginCheck.json",
			type : "post",
			dataType : "json",
			async : false,
			success : function(result) {
				$(result).each(function(key, value) {
					if ($("#email").val() == value.email) {
						emailCheck = true;
					}
				}); // each
			} // success
		}); // ajax
		if (!$("#email").val()) {
			alert("이메일을 입력하세요.");
		} else if (emailCheck) {
			alert("이미 존재하는 이메일입니다.");
		} else {
			alert("사용 가능한 이메일입니다.");
		}
	});
	//아이디, 이메일 중복 체크 버튼 기능 끝
*/	
		
		
		
		
		
// 밑에 걍 로그인페이지꺼 복붙임 회원가입 XXXX
	
	// 로그인페이지에서 로그인 버튼 기능
//	$("#submit").click(function() {

//	  $("#submit").click(function() {
//		var id = $('#id').val();
//		var pw = $('#pw').val();
//		if (!id && !pw) {
//			$('#id').focus();
//			alert("아이디와 패스워드를 정확히입력하세요");
//			return false;
//		}
//		var check = 0;
//		$.ajax({
//			url : "loginCheck.json",
//			dataType : "json",
//			async : false,
//			success : function(data) {
//
//				$(data).each(function(key, value) {
//					if (id == (value.id) && pw == (value.pw)) {
//						check = 1;
//					}
//				});
//			}
//		});
//		if (check == 1) {
//			alert(id + "님이 로그인  되셨습니다.");
//			location.replace("index.html");
//			return false;
//		} else {
//			alert("아이디가 존재하지 않거나 비밀번호가 틀립니다.");
//			$('#id').val("");
//			$('#pw').val("");
//			$('#id').focus();
//			return false;
//		}
//		// 선생님이 알려주기 전에 했던 작업임. 경묵씨가 알려준 방법.
	 
/* if (!$("#id").val()) {
 alert("아이디를 입력하세요");
			$("#id").focus();
			return false;
		}
		if (!$("#pw").val()) {
			alert("비밀번호를 입력하세요");
			$("#pw").focus();
			return false;
		}
		idCheck = false;
		$.ajax({
			url : "loginCheck.json",
			type : "post",
			dataType : "json",
			async : false, 
			success : function(result) {
				$(result).each(function(key, value) {
					if ($("#id").val() == value.id && $("#pw").val() == value.pw) {
						idCheck = true;
					}
				}); // each
			} // success
		}); // ajax
		if (idCheck) {
			alert(id.value + "님 로그인하셨습니다.");
			location.href = 'index.html';
			return false;
		} else {
			alert("존재하지 않는 아이디거나 비밀번호를 잘못 입력하셨습니다.");
			$("#id").val("");
			$("#pw").val("");
			$("#id").focus();
			return false;
		}
	});
});
*/
	

