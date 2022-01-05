<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>퍼피플 회원가입</title>
<link rel="shortcut icon" href="imgs/favicon.ico" type="image/x-icon">
<link rel="icon" href="imgs/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="css/join.css">
<link rel="stylesheet" type="text/css" href="https://cdn.rawgit.com/innks/NanumSquareRound/master/nanumsquareround.min.css">
<link rel="stylesheet" type="text/css" href="//cdn.rawgit.com/young-ha/webfont-archive/master/css/Godo.css">
<style type="text/css">
.valid {
	color: green;
}
.invalid {
	color: red;
}

</style>
<script src="https://kit.fontawesome.com/794175a030.js" crossorigin="anonymous" ></script>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>

</head>
<body>
	<form method="post" action="join" id="form" name="form" onsubmit="return go_join()">
		<div id="wrap">
			<div id="joinForm">
			
				<div class="logo">
					<a href="<c:url value='/' />"><img src="imgs/logo.png" alt="퍼피플로고"></a>
				</div>
			
				<div id="idForm">
					<label for="member_id">아이디</label> 
					<input type="text" class="chk" name="member_id" id="member_id" placeholder="ID를 입력하세요 (영문 / 숫자 4~11자)" autofocus /> 
						<p class="valid"></p>
					<div class="id_warning_notext">
						<i class="fas fa-exclamation-circle"></i>
						<p>필수 정보입니다.</p>
					</div>	
					<div class="id_warning_used">
						<i class="fas fa-exclamation-circle"></i>
						<p>이미 사용하고 있는 아이디입니다.</p>
					</div>
					<div class="id_warning_not">
						<i class="fas fa-exclamation-circle"></i>
						<p>아이디는 영문/숫자 4~11자 입니다.</p>
					</div>
				</div>
				
				<div id="pwForm">
					<label for="member_pw">비밀번호</label>
					<div class="pw_wrap">
						<input type="password" class="chk" name="member_pw" id="member_pw" placeholder="비밀번호를 입력하세요 (4~11자 영문 대 소문자, 숫자, 특수문자)" />
						<div class="eye">
							<i class="fa fa-eye"></i>
						</div>
						<p class="valid"></p>
			
						<div class="pw_warning_notext">
							<i class="fas fa-exclamation-circle"></i>
							<p>필수 정보입니다.</p>
						</div>
						<div class="pw_warning_not">
							<i class="fas fa-exclamation-circle"></i>
							<p>4~11자 영문 대 소문자, 숫자, 특수문자를 사용하세요.</p>
						</div>
					</div>
				</div>
				
				<div id="pwForm2">
					<label for="pw_re_check">비밀번호 재확인</label>
					<input type="password" class="chk" name="pw_re_check" id="pw_re_check" placeholder="비밀번호를 재입력하세요" />
					<p class="valid"></p> 
					
					<div class="pw_warning_notext2">
						<i class="fas fa-exclamation-circle"></i>
						<p>필수 정보입니다.</p>
					</div>
					<div class="pw_warning_not2">
						<i class="fas fa-exclamation-circle"></i>
						<p>비밀번호가 일치하지 않습니다.</p>
					</div>
				</div>
				
				<div id="nicknameForm">
					<label for="member_nickname">닉네임</label>
					<input type="text" class="chk" name="member_nickname" id="member_nickname" placeholder="사용할 닉네임을 입력하세요 (한/영/숫자 2~9자)" />
					<p class="valid"></p> 
					<div class="nic_warning_notext">
						<i class="fas fa-exclamation-circle"></i>
						<p>필수 정보입니다.</p>
					</div>
					<div class="nic_warning_used">
						<i class="fas fa-exclamation-circle"></i>
						<p>이미 사용하고 있는 닉네임입니다.</p>
					</div>
					<div class="nic_warning_not">
						<i class="fas fa-exclamation-circle"></i>
						<p>닉네임은 한/영/숫자 2~9자 입니다.</p>
					</div>
				</div>
				
				<div id="phoneForm">
					<label for="member_phone">전화번호</label> 
					<input type="text" class="chk" name="member_phone" id="member_phone" placeholder="전화번호를 입력하세요 ( - 없이)" maxlength="11"  />
					<p class="valid"></p> 
					<div class="phone_warning_notext">
						<i class="fas fa-exclamation-circle"></i>
						<p>필수 정보입니다.</p>
					</div>
					<div class="phone_warning_not">
						<i class="fas fa-exclamation-circle"></i>
						<p> - 없이 숫자만 입력해주세요.</p>
					</div>
				</div>
				
				<div id="emailForm">
					<label for="member_email">이메일</label> 
					<input type="text" class="chk" name="member_email"	id="member_email" placeholder="이메일을 입력하세요"  />
					<p class="valid"></p> 
					<div class="email_warning_notext">
						<i class="fas fa-exclamation-circle"></i>
						<p>필수 정보입니다.</p>
					</div>
					<div class="email_warning_not">
						<i class="fas fa-exclamation-circle"></i>
						<p>이메일 주소를 다시 확인해주세요.</p>
					</div>
					
				</div>
				
				<div id="end">
					<input type="submit" id="submit" value="가입하기"/> 
				</div>
				
				<div id="link_login">
					<p>이미 계정이 있다면 <a href="login">여기</a>를 클릭하여 로그인 하세요</p>
				</div>
				<div id="footer">
					<p>© 퍼피플 PUPPYPLE</p>
				</div>
				</div>
			</div>
			
	</form>
	
<script type="text/javascript">

function go_join() {
	 var id = /^[a-z]\w{3,11}$/;
	 var email = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
	 var regPw = /^[a-zA-Z0-9]{4,12}$/;
	 var regNickname = /^[a-z가-힣0-9]{2,9}$/;
	 var regPhone = /^(010|070|02|031|032|033|041|042|043|044|051|052|053|054|055|061|062|063|064)\d{4}\d{4}$/;
	 var nick_check = false;
	 var id_check = false;
	 // 아이디 중복확인 ajax
	 $.ajax({
			url : 'member_id_check'
			, async : false
			, data : {member_id:$('#member_id').val()}
			, success : function (res) {
				if(res != true ){
					$('.id_warning_used').css('display','block').css('color','#731aed');
					$('.id_warning_notext').css('display','none');
					$('.id_warning_not').css('display','none');
					$('#member_id').focus();
					return false;
				}else{
					$('.id_warning_used').css('display','none');
					id_check = true;
				}
			}
		 });
	 
	 // 닉네임 중복확인 ajax
	 $.ajax({
			url : 'member_nickname_check'
			, async : false
			, data : {member_nickname:$('#member_nickname').val()}
			, success : function (res) {
				if(res != true ){
					$('.nic_warning_used').css('display','block').css('color','#731aed');
					$('.nic_warning_notext').css('display','none');
					$('.nic_warning_not').css('display','none');
					$('#member_nickname').focus();
					return false;
				}else{
					$('.nic_warning_used').css('display','none');
					nick_check = true;
				}
			}
		 });
	 
	 if($('#member_id').val() == ""){
		 $('.id_warning_notext').css('display','block').css('color','#731aed');
		 $('.id_warning_not').css('display','none');
		 $('.id_warning_used').css('display','none');
		 $('#member_id').focus();
		 return false;
	 }else if(!id.test($('#member_id').val())){
		 $('.id_warning_not').css('display','block').css('color','#731aed');
		 $('.id_warning_notext').css('display','none');
		 $('.id_warning_used').css('display','none');
		 $('#member_id').val("");
		 $('#member_id').focus();
		 return false;
	 }else {
		 $('.id_warning_not').css('display','none');
		 $('.id_warning_notext').css('display','none');
	 }
	 
	 if($('#member_pw').val() == ""){
		 $('.pw_warning_notext').css('display','block').css('color','#731aed');
		 $('.pw_warning_not').css('display','none');
		 $('#member_pw').focus();
		 return false;
	 }else if(!regPw.test($('#member_pw').val())){
		 $('.pw_warning_notext').css('display','none');
		 $('.pw_warning_not').css('display','block').css('color','#731aed');
		 $('#member_pw').val("");
		 $('#pw_re_check').val("");
		 $('#member_pw').focus();
		 return false;
	 }else{
		 $('.pw_warning_notext').css('display','none');
		 $('.pw_warning_not').css('display','none');
	 }
	 
	 
	 if($('#pw_re_check').val() == ""){
		 $('.pw_warning_notext2').css('display','block').css('color','#731aed');
		 $('.pw_warning_not2').css('display','none');
		 $('#pw_re_check').focus();
		 return false;
	 }else if( $('#member_pw').val() != $('#pw_re_check').val() ){
		 $('.pw_warning_not2').css('display','block').css('color','#731aed');
		 $('.pw_warning_notext2').css('display','none');
		 $('#pw_re_check').val("");
		 $('#pw_re_check').focus();
		 return false;
	 }else {
		 $('.pw_warning_notext2').css('display','none');
		 $('.pw_warning_not2').css('display','none');
	 }
	 
	 
	 if($('#member_nickname').val() == ""){
		 $('.nic_warning_notext').css('display','block').css('color','#731aed');
		 $('.nic_warning_not').css('display','none');
		 $('.nic_warning_used').css('display','none');
		 $('#member_nickname').focus();
		 return false;
	 }else if(!regNickname.test($('#member_nickname').val())){
		 $('.nic_warning_not').css('display','block').css('color','#731aed');
		 $('.nic_warning_notext').css('display','none');
		 $('.nic_warning_used').css('display','none');
		 $('#member_nickname').val("");
		 $('#member_nickname').focus();
		 return false;
	 }else {
		 $('.nic_warning_not').css('display','none');
		 $('.nic_warning_notext').css('display','none');
	 }
	 
	 if($('#member_phone').val() == ""){
		 $('.phone_warning_not').css('display','none');
		 $('.phone_warning_notext').css('display','block').css('color','#731aed');
		 $('#member_phone').focus();
		 return false;
	 }else if(!regPhone.test($('#member_phone').val())){
		 $('.phone_warning_notext').css('display','none');
		 $('.phone_warning_not').css('display','block').css('color','#731aed');
		 $('#member_phone').val("");
		 $('#member_phone').focus();
		 return false;
	 }else{
		 $('.phone_warning_notext').css('display','none');
		 $('.phone_warning_not').css('display','none');
	 }
	 
	 if($('#member_email').val() == ""){
		 $('.email_warning_not').css('display','none');
		 $('.email_warning_notext').css('display','block').css('color','#731aed');
		 $('#member_email').focus();
		 return false;
	 }else if(!email.test($('#member_email').val())){
		 $('.email_warning_notext').css('display','none');
		 $('.email_warning_not').css('display','block').css('color','#731aed');
		 $('#member_email').val("");
		 $('#member_email').focus();
		 return false;
	 }
	 if(nick_check == false){
		 return false;
	 }
	 if(id_check == false){
		 return false;
	 }
} 



//눈 아이콘 클릭 시 패스워드 보이기
$(".eye").click(function() {
 $("#member_pw").toggleClass("active");
 if($("#member_pw").hasClass("active") == true) {
   $('.fa.fa-eye').attr('class', 'fa fa-eye-slash').css('margin-left','-1px');
   $("#member_pw").attr("type", "text");
   $("#pw_re_check").attr("type", "text");
 } else {
   $('.fa.fa-eye-slash').attr('class', 'fa fa-eye').css('margin-left','0px');;
   $("#member_pw").attr("type", "password");
   $("#pw_re_check").attr("type", "password");
 }
});

</script>
</body>
</html>