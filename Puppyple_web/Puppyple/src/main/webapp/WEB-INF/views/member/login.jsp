<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>퍼피플 로그인</title>
<link rel="shortcut icon" href="imgs/favicon.ico" type="image/x-icon">
<link rel="icon" href="imgs/favicon.ico" type="image/x-icon">
<link rel="stylesheet" href="css/login.css">
<link rel="stylesheet" type="text/css"
	href="https://cdn.rawgit.com/innks/NanumSquareRound/master/nanumsquareround.min.css">
<link rel="stylesheet" type="text/css"
	href="//cdn.rawgit.com/young-ha/webfont-archive/master/css/Godo.css">
<script src="https://kit.fontawesome.com/794175a030.js"
	crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script src="js/login.js"></script>
</head>
<body>
	<div id="wrap">
		<form action="#" id="loginForm" name="loginForm" method="get">

			<div class="logo">
				<a href="<c:url value='/' />"><img src="imgs/logo.png"
					alt="퍼피플로고" /></a>
			</div>

			<div id="idForm">
				<label for="member_id">아이디</label> <input type="text"
					name="member_id" id="member_id" placeholder="ID를 입력하세요" />
				<div class="error fillId">
					<i class="fas fa-exclamation-circle"></i>
					<p>아이디를 입력해 주세요.</p>
				</div>
			</div>

			<div id="pwForm">
				<label for="member_pw">비밀번호</label>
				<div class="pw_wrap">
					<input type="password" name="member_pw" id="member_pw"
						placeholder="비밀번호를 입력하세요" />
					<div class="eye">
						<i class="fa fa-eye"></i>
					</div>
				</div>
				<div class="error fillPw">
					<i class="fas fa-exclamation-circle"></i>
					<p>비밀번호를 입력해 주세요.</p>
				</div>
			</div>

			<div id="end">
				<input type="submit" id="submit" onclick="go_login()" value="로그인" />
			</div>

		</form>

		<div id="link_find">
			<p>
				<a href="id_find.find">아이디 찾기</a>&emsp;|&emsp;<a href="pw_find.find">비밀번호찾기</a>
			</p>
		</div>

		<div id="link_join">
			<p>
				잠깐! 회원이 아니세요? <a href="member">가입</a>하세요
			</p>
		</div>

		<div id="social_login">
			<h3>간편 로그인</h3>
			<div class="naver_login">
				<button class="naver" onclick="location.href = 'naverLogin' ">
					<img src="imgs/naver_login.png" /> <span>네이버로 로그인</span>
				</button>
			</div>
			<div class="kakao_login">
				<button class="kakao" onclick="location.href = 'kakaoLogin' ">
					<img src="imgs/kakao_login.png" /></a> <span>카카오로 로그인</span>
				</button>
			</div>
		</div>


		<div id="footer">
			<p>© 퍼피플 PUPPYPLE</p>
		</div>

	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#member_id").focus();
		});
		function go_login() {
			// 아이디 입력값이 없으면 
			if ($('#member_id').val() == '') {
				alert("아이디를 입력하세요!");
				$('#member_id').focus();
				return;
				// 비밀번호 입력값이 없으면 
			} else if ($('#member_pw').val() == '') {
				alert("비밀번호를 입력하세요!");
				$('#member_pw').focus();
				return;
			}

			//login을 위한 ajax 통신 설정
			$.ajax({
				url : 'puppypleLogin',
				data : {
					member_id : $('#member_id').val(),
					member_pw : $('#member_pw').val()
				},
				success : function(response) {
					if (response) {
						location = '<c:url value = "/" />';

					} else {
						alert("아이디나 비밀번호가 일치하지 않습니다.");
					}
				},
				error : function(req, text) {
					alert(text + ':' + req.status);
				}

			});

		}
	</script>

</body>
</html>

