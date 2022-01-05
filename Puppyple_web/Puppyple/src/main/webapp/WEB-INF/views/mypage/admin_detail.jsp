<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<!-- header영역 -->
<jsp:include page="/WEB-INF/views/include/header.jsp" />
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/no_detail.css">
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
						<h3>회원 상세 정보</h3>
					</div>
					<div id="mp_wrap">
						<div id="user_info">
							<h5>${vo.member_nickname}님의회원정보입니다.</h5>

							<table id="user_info_table">
								<tr id="id_info">
									<th>회원아이디</th>
									<td>${vo.member_id}</td>
								</tr>
								<tr id="nickname_info">
									<th>회원닉네임</th>
									<td>${vo.member_nickname}</td>
								</tr>
								<tr id="phone_info">
									<th>회원전화번호</th>
									<td>${vo.member_phone}</td>
								</tr>
								<tr id="email_info">
									<th>회원이메일</th>
									<td>${vo.member_email}</td>
								</tr>
								<tr id="naver_info">
									<th>가입날짜</th>
									<td>${vo.member_joindate}</td>
								</tr>
								<tr id="kakao_info">
									<th>카카오로 가입</th>
									<td>${vo.member_kakao}</td>
								</tr>
								<tr id="naver_info">
									<th>네이버로 가입</th>
									<td>${vo.member_naver}</td>
								</tr>
								<tr id="pet_cnt">
									<th>등록 된 펫 수</th>
									<td>${vo.pet_cnt}</td>
								</tr>
								<tr id="board_cnt">
									<th>등록한 게시물 수</th>
									<td>${vo.board_cnt}</td>
								</tr>
								<tr id="boardcomment_cnt">
									<th>등록한 댓글 수</th>
									<td>${vo.boardcomment_cnt}</td>
								</tr>
							</table>
						</div>
						<div id="pet_info" style="margin-top: 50px;">
							<c:forEach items="${list }" var="vo">

								<div id="pet_info_table">

									<div id="pet_list_layout">
										<div id="pet_list_inner1">
											<c:if test="${ not empty vo.pet_filepath }">
												<a style="background-image : url(${vo.pet_filepath });"
													id="pet_preview"></a>
												<!-- 미리보기 이미지가 나오는곳 -->
											</c:if>
											<c:if test="${empty vo.pet_filepath }">
												<!-- 파일이미지가 없을 경우 기본 이미지가 나오는곳 -->
												<a id="pet_preview_not"></a>
											</c:if>
										</div>
										<div id="pet_list_inner2">
											<div id="pet_name">
												<span class="title">이름</span> <span class="content">${vo.pet_name}</span>
											</div>
											<div id="pet_age">
												<span class="title">나이</span> <span class="content">${vo.pet_age}</span>
											</div>
											<div id="pet_gender">
												<span class="title">성별</span>
												<c:if test="${vo.pet_gender eq 'M'}">
													<span class="content">수컷</span>
												</c:if>
												<c:if test="${vo.pet_gender eq 'F'}">
													<span class="content">암컷</span>
												</c:if>
											</div>
											<div id="pet_breed">
												<span class="title">견종</span> <span class="content">${vo.pet_breed}</span>
											</div>
											<div id="pet_weight">
												<span class="title">무게</span> <span class="content">${vo.pet_weight}</span>
											</div>
											<div id="pet_nuetering">
												<span class="title">중성화</span>
												<c:if test="${vo.pet_nuetering eq 'Y'}">
													<span class="content">O</span>
												</c:if>
												<c:if test="${vo.pet_nuetering eq 'N'}">
													<span class="content">X</span>
												</c:if>
											</div>
										</div>
									</div>
									<!-- pet_list_layout -->

									<div id="pet_modifiy_box">
										<a
											onclick=" if(confirm('정말 삭제하시겠습니까?')) { href='admin_pet_info_delete.mp?pet_id=${vo.pet_id }'} "><input
											type="button" id="pet_delete_btn"
											class="admin_pet_delete_btn" value="삭제" /></a>
									</div>
									<!-- pet_modifiy_box -->
								</div>
								<!-- pet_info_table -->

							</c:forEach>
						</div>
						<!-- pet_info -->

						<div class="div_button">
							<div class="button">
								<div class="list_btn1">
									<a class="btn-fill" id="board_list" href="admin_list.mp">회원목록</a>
								</div>
								<div class="list_btn2">
									<a class="btn-fill" id="btn-delete"
										onclick=" if(confirm('정말 삭제하시겠습니까?')) { href='admin_delete.mp?member_id=${vo.member_id }'} ">회원삭제</a>
								</div>
							</div>
						</div>
					</div>
					<!-- mp_wrqp -->
				</div>
			</section>
		</div>
		<!-- mypage_wrap -->
	</div>
	<!-- mypage -->
</body>
<script type="text/javascript" src='js/board_file_check.js'></script>

<!-- footer 영역 -->
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</html>