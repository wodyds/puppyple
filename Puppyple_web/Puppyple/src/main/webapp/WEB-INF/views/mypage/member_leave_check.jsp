<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<!-- header영역 -->
<jsp:include page="/WEB-INF/views/include/header.jsp" />
<link rel="stylesheet" href="css/mypage.css">
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
						<h3>회원탈퇴</h3>
					</div>
					<div id="mp_wrap">
						<div id="checkForm">

							<i class="fas fa-check"></i>
							<p>회원탈퇴를 원하실 경우 본인확인을 위해 비밀번호를 한번 더 입력해주세요.</p>

							<div class="form">
								<label for="pw_check">비밀번호 입력</label> <input type="password"
									name="pw_check_leave" id="pw_check_leave"
									onkeypress="if (event.keyCode == 13) pw_check()" autofocus /> <input
									type="submit" value="확인" class="submit" id="go_check" />
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
			if ($('#pw_check_leave').val() == '') {
				alert('비밀번호를 입력하세요');
				$('#pw_check_leave').focus();
				return;
			}
			$.ajax({
				url : 'member_leave_check_pw',
				data : {
					member_pw : $('#pw_check_leave').val()
				},
				success : function(response) {
					leave_event(response);
				},
				error : function(req, text) {
					alert(text + ':' + req.status);
				}
			});
		}

		function leave_event(response) {
			if (confirm("정말 삭제하시겠습니까??") == true) { //확인
				location = "member_delete.mp?member_id=" + response.member_id;
			} else { //취소
				return;
			}
		}
	</script>

</body>
<!-- footer 영역 -->
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</html>