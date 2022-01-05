<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- header영역 -->
<jsp:include page="/WEB-INF/views/include/header.jsp" />
<link rel="stylesheet" href="css/mypage.css">
</head>
<body>
	<!-- 마이페이지 영역 (layouy1은 메뉴바 고정 / 연결되는 페이지는 모두 layout2 안에서 표출시켜야 함)) -->
	<div id="mypage">
		<div id="mypage_wrap">

			<!-- <section id="layout1"> 마이페이지 메뉴바 고정-->
			<jsp:include page="/WEB-INF/views/include/mypage_side.jsp" />

			<!-- <section id="layout2"> 안에 페이지 전환  -->
			<section id="layout2">
				<div>
					<div id="mypage_menu_title">
						<h3>회원정보수정</h3>
					</div>
					<div id="mp_wrap">
						<div id="checkForm">

							<i class="fas fa-check"></i>
							<p>개인정보 변경을 원하실 경우 본인확인을 위해 비밀번호를 한번 더 입력해주세요.</p>

							<div class="form">
								<label for="pw_check">비밀번호 입력</label> <input type="password"
									name="pw_check" id="pw_check"
									onkeypress="if (event.keyCode == 13) pw_check()" autofocus />
								<input type="submit" value="확인" class="submit" id="go_check" />
							</div>


							<!-- <a class="submit" onclick="go_check">확인</a> -->
						</div>
					</div>
					<!-- mp_wrqp -->
				</div>
			</section>
		</div>
		<!-- mypage_wrap -->
	</div>
	<!-- mypage -->

	<script type="text/javascript">
		$('#go_check').on('click', function() {
			pw_check();
		})

		function pw_check() {
			if ($('#pw_check').val() == '') {
				alert('비밀번호를 입력하세요');
				$('#pw_check').focus();
				return;
			}
			$
					.ajax({
						url : 'member_pw_check_pw',
						data : {
							member_pw : $('#pw_check').val()
						},
						success : function(response) {
							if (response) {
								location = "member_modify.mp?member_id=${loginInfo.member_id}";
							} else {
								alert("비밀번호가 일치하지 않습니다.")
							}
						},
						error : function(req, text) {
							alert(text + ':' + req.status);
						}
					});
		}
	</script>

</body>
<!-- footer 영역 -->
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</html>