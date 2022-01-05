<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- header영역 -->
<jsp:include page="/WEB-INF/views/include/header.jsp" />
<link rel="stylesheet" href="css/mypage.css">
<script src="https://kit.fontawesome.com/794175a030.js"
	crossorigin="anonymous"></script>
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
						<div id="user_info_modify">
							<h5>수정할 회원 정보를 입력하세요.</h5>
							<div id="leave">
								<a id="leave_btn" onclick=" button_event();">회원탈퇴</a>
								<!-- <a id="leave_btn"  onclick=" if(confirm('정말 삭제하시겠습니까?')) { href='mypage/member_leave_check'}">회원탈퇴</a> -->
							</div>
							<form action="member_modify_ok.mp" method="get" id="form"
								name="form" onsubmit="return go_modify()">
								<div id="form_wrap">
									<div id="idForm">
										<label for="member_id">아이디 <span>(수정불가)</span>
										</label> <input type="text" name="member_id" id="member_id"
											value="${vo.member_id}" readonly="readonly" />
									</div>
									<div id="pwForm">
										<label for="member_pw">비밀번호</label>
										<div class="pw_wrap">
											<input type="password" name="member_pw" id="member_pw"
												value="${vo.member_pw }"
												placeholder="4~11자 영문 대 소문자, 숫자, 특수문자를 사용하세요." autofocus />
											<div class="eye">
												<i class="fa fa-eye"></i>
											</div>
										</div>
										<div class="pw_warning_notext warning">
											<i class="fas fa-exclamation-circle"></i>
											<p>필수 정보입니다.</p>
										</div>
										<div class="pw_warning_not warning">
											<i class="fas fa-exclamation-circle "></i>
											<p>4~11자 영문 대 소문자, 숫자, 특수문자를 사용하세요.</p>
										</div>
									</div>
									<div id="pwForm2">
										<label for="pw_re_check">비밀번호 재확인</label> <input
											type="password" name="pw_re_check" id="pw_re_check"
											value="${vo.member_pw }" placeholder="비밀번호를 다시 입력해주세요." />
										<div class="pw_warning_notext2 warning">
											<i class="fas fa-exclamation-circle"></i>
											<p>필수 정보입니다.</p>
										</div>
										<div class="pw_warning_not2 warning">
											<i class="fas fa-exclamation-circle"></i>
											<p>비밀번호가 일치하지 않습니다.</p>
										</div>
									</div>
									<div id="nicknameForm">
										<label for="member_nickname">닉네임</label> <input type="text"
											name="member_nickname" id="member_nickname"
											value="${vo.member_nickname}"
											placeholder="닉네임은 한/영/숫자 2~9자 입니다." />
										<div class="nic_warning_notext warning">
											<i class="fas fa-exclamation-circle"></i>
											<p>필수 정보입니다.</p>
										</div>
										<div class="nic_warning_used warning">
											<i class="fas fa-exclamation-circle"></i>
											<p>이미 사용하고 있는 닉네임입니다.</p>
										</div>
										<div class="nic_warning_not  warning">
											<i class="fas fa-exclamation-circle"></i>
											<p>닉네임은 한/영/숫자 2~9자 입니다.</p>
										</div>
									</div>
									<div id="phoneForm">
										<label for="member_phone">전화번호</label> <input type="text"
											name="member_phone" id="member_phone"
											value="${vo.member_phone}" maxlength="11"
											placeholder="- 없이 숫자만 입력해주세요." />
										<div class="phone_warning_notext warning">
											<i class="fas fa-exclamation-circle"></i>
											<p>필수 정보입니다.</p>
										</div>
										<div class="phone_warning_not warning">
											<i class="fas fa-exclamation-circle"></i>
											<p>- 없이 숫자만 입력해주세요.</p>
										</div>
									</div>
									<div id="emailForm">
										<label for="member_email">이메일</label> <input type="text"
											name="member_email" id="member_email"
											value="${vo.member_email} " placeholder="이메일을 입력해주세요." />
										<div class="email_warning_notext warning">
											<i class="fas fa-exclamation-circle"></i>
											<p>필수 정보입니다.</p>
										</div>
										<div class="email_warning_not warning">
											<i class="fas fa-exclamation-circle"></i>
											<p>이메일 주소를 다시 확인해주세요.</p>
										</div>
									</div>
								</div>
								<!-- form_wrap  -->

								<div id="end">
									<input type="submit" id="submit" value="수정"
										onclick="if(confirm('수정하시겠습니까?')) $('form').submit()" /> <input
										type="reset" id="reset" value="취소"
										onclick=" if(confirm('취소하시겠습니까?')) history.go(-2)" />
								</div>

							</form>
						</div>
						<!-- user_info_modify -->
					</div>
					<!-- mp_wrqp -->

				</div>
			</section>
		</div>
		<!-- mypage_wrap -->
	</div>
	<!-- mypage -->

	<script type="text/javascript">
		function go_modify() {
			var post_nickname = "${vo.member_nickname}";
			console.log(post_nickname);
			var email = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
			var regPw = /^[a-zA-Z0-9]{4,12}$/;
			var regNickname = /^[a-z가-힣0-9]{2,9}$/;
			var regPhone = /^(010|070|02|031|032|033|041|042|043|044|051|052|053|054|055|061|062|063|064)\d{4}\d{4}$/;
			var check = false;
			$.ajax({
				url : 'member_nickname_check',
				async : false,
				data : {
					member_nickname : $('#member_nickname').val()
				},
				success : function(res) {
					if (res != true
							&& $('#member_nickname').val() == post_nickname) {
						check = true;
					} else if (res != true) {
						$('.nic_warning_used').css('display', 'block').css(
								'color', '#b22de4').css('font-size', '13px');
						$('.nic_warning_notext').css('display', 'none');
						$('.nic_warning_not').css('display', 'none');
						$('#member_nickname').focus();
						return false;
					} else {
						$('.nic_warning_used').css('display', 'none');
						check = true;
					}
				}
			});

			if ($('#member_pw').val() == "") {
				$('.pw_warning_notext').css('display', 'block').css('color',
						'#b22de4').css('font-size', '13px');
				$('.pw_warning_not').css('display', 'none');
				$('#member_pw').focus();
				return false;
			} else if (!regPw.test($('#member_pw').val())) {
				$('.pw_warning_notext').css('display', 'none');
				$('.pw_warning_not').css('display', 'block').css('color',
						'#b22de4').css('font-size', '13px');
				$('#member_pw').val("");
				$('#pw_re_check').val("");
				$('#member_pw').focus();
				return false;
			} else {
				$('.pw_warning_notext').css('display', 'none');
				$('.pw_warning_not').css('display', 'none');
			}

			if ($('#pw_re_check').val() == "") {
				$('.pw_warning_notext2').css('display', 'block').css('color',
						'#b22de4').css('font-size', '13px');
				$('.pw_warning_not2').css('display', 'none');
				$('#pw_re_check').focus();
				return false;
			} else if ($('#member_pw').val() != $('#pw_re_check').val()) {
				$('.pw_warning_not2').css('display', 'block').css('color',
						'#b22de4').css('font-size', '13px');
				$('.pw_warning_notext2').css('display', 'none');
				$('#pw_re_check').val("");
				$('#pw_re_check').focus();
				return false;
			} else {
				$('.pw_warning_notext2').css('display', 'none');
				$('.pw_warning_not2').css('display', 'none');
			}

			if ($('#member_nickname').val() == "") {
				$('.nic_warning_notext').css('display', 'block').css('color',
						'#b22de4').css('font-size', '13px');
				$('.nic_warning_not').css('display', 'none');
				$('.nic_warning_used').css('display', 'none');
				$('#member_nickname').focus();
				return false;
			} else if (!regNickname.test($('#member_nickname').val())) {
				$('.nic_warning_not').css('display', 'block').css('color',
						'#b22de4').css('font-size', '13px');
				$('.nic_warning_notext').css('display', 'none');
				$('.nic_warning_used').css('display', 'none');
				$('#member_nickname').val("");
				$('#member_nickname').focus();
				return false;
			} else {
				$('.nic_warning_not').css('display', 'none');
				$('.nic_warning_notext').css('display', 'none');
			}

			if ($('#member_phone').val() == "") {
				$('.phone_warning_not').css('display', 'none');
				$('.phone_warning_notext').css('display', 'block').css('color',
						'#b22de4').css('font-size', '13px');
				$('#member_phone').focus();
				return false;
			} else if (!regPhone.test($('#member_phone').val())) {
				$('.phone_warning_notext').css('display', 'none');
				$('.phone_warning_not').css('display', 'block').css('color',
						'#b22de4').css('font-size', '13px');
				$('#member_phone').val("");
				$('#member_phone').focus();
				return false;
			} else {
				$('.phone_warning_notext').css('display', 'none');
				$('.phone_warning_not').css('display', 'none');
			}

			if ($('#member_email').val() == "") {
				$('.email_warning_not').css('display', 'none');
				$('.email_warning_notext').css('display', 'block').css('color',
						'#b22de4').css('font-size', '13px');
				$('#member_email').focus();
				return false;
			} else if (!email.test($('#member_email').val())) {
				$('.email_warning_notext').css('display', 'none');
				$('.email_warning_not').css('display', 'block').css('color',
						'#b22de4').css('font-size', '13px');
				$('#member_email').val("");
				$('#member_email').focus();
				return false;
			}
			if (check == false) {
				return false;
			}
		}

		//눈 아이콘 클릭 시 패스워드 보이기
		$(".eye").click(
				function() {
					$("#member_pw").toggleClass("active");
					if ($("#member_pw").hasClass("active") == true) {
						$('.fa.fa-eye').attr('class', 'fa fa-eye-slash').css(
								'margin-left', '-1px');
						$("#member_pw").attr("type", "text");
					} else {
						$('.fa.fa-eye-slash').attr('class', 'fa fa-eye').css(
								'margin-left', '0px');
						;
						$("#member_pw").attr("type", "password");
					}
				});

		function button_event() {

			if (confirm("정말 삭제하시겠습니까??") == true) { //확인
				location = "member_leave_check.mp";

			} else { //취소

				return;

			}

		}
	</script>


</body>
<!-- footer 영역 -->
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</html>