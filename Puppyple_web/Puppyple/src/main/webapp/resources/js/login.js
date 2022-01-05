$(document).ready(function() {
	
	//submit 버든 클릭 했을때 (로그인버튼)
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
		}
		return false;
	
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
	
}); //ready