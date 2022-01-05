$(document).ready(function() {
	
	//유효성 검사 변수 선언
	var regId = /^[a-z]\w{4,11}$/;
	var regPw = /^[a-z]\w{4,11}$/;
	var regNickname = /^[가-힣]{2,5}$/;
	var regPhone = /^(010|070|02|031|032|033|041|042|043|044|051|052|053|054|055|061|062|063|064)\d{4}\d{4}$/;
	var regEmail = /^[a-z]\w{4,11}@[a-z]{2,10}[\.][a-z]{2,3}[\.]?[a-z]{0,2}$/;
	
	
	
	//submit 버든 클릭 했을때 
		$("#submit").click(function() {
			
			//입력란이 빈칸일 때 (작업 완료)
			// + 잘못된 입력칸 일 때 : 유효성검사  ← 스프링에서 하기
			if(!$("#id").val()){
				$(".fillId").show();
				$("#id").focus();
			}
			
			if(!$("#pw").val()){
				$(".fillPw").show();
				$("#pw").focus();
			}/*else if(!regPw.test($("#pw").val)){
				$(".pw_check").show();
				$("#pw").focus();
			} ← 스프링에서 하기 */
			
			if(!$("#pw_re_check").val()){
				$(".fillPw2").show();
				$("#pw_re_check").focus();
			}
			
			if(!$("#nickname").val()){
				$(".fillNickname").show();
				$("#nickname").focus();
			}
			
			if(!$("#phone").val()){
				$(".fillPhone").show();
				$("#phone").focus();
			}
			
			if(!$("#email").val()){
				$(".fillEmail").show();
				$("#email").focus();
			}
			
			return false;
		
		}); // submit click	
		
		
		// 눈 아이콘 클릭 시 패스워드 보이기
		$(".eye").click(function() {
		  $("#pw").toggleClass("active");
		  if($("#pw").hasClass("active") == true) {
		    $('.fa.fa-eye').attr('class', 'fa fa-eye-slash').css('margin-left','-1px');
		    $("#pw").attr("type", "text");
		  } else {
		    $('.fa.fa-eye-slash').attr('class', 'fa fa-eye').css('margin-left','0px');;
		    $("#pw").attr("type", "password");
		  }
		});
		
	
});	//ready
