<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- header영역 -->
<jsp:include page="/WEB-INF/views/include/header.jsp" />
<link rel="stylesheet" href="css/mypage.css">
<!-- 마이페이지 영역 (layouy1은 메뉴바 고정 / 연결되는 페이지는 모두 layout2 안에서 표출시켜야 함)) -->
<div id="mypage">
	<div id="mypage_wrap">
		<!-- <section id="layout1"> 마이페이지 메뉴바 고정-->
		<jsp:include page="/WEB-INF/views/include/mypage_side.jsp" />

		<!-- <section id="layout2"> 안에 페이지 전환  -->
		<section id="layout2">
			<div>
				<div id="mypage_menu_title">
					<h3>퍼피플 회원정보</h3>
				</div>

				<div id="mp_table_wrap">
					<div id="user_info">

						<div id='list-top'>
							<div class="wrap">
								<c:forEach items="${page.list}" begin="1" end="1" var="vo">
									<table class="list_info">
										<tr>
											<th class="member_cnt">총 회원 수</th>
											<td>${vo.member_cnt}명</td>
										</tr>
										<tr>
											<th class="pet_cnt">총 등록된 펫 수</th>
											<td>${vo.pet_cnt}마리</td>
										</tr>
										<tr>
											<th class="boardcomment_cnt">총 댓글 수</th>
											<td>${vo.boardcomment_cnt}개</td>
										</tr>
										<tr>
											<th class="board_cnt">총 게시물 수</th>
											<td>${vo.board_cnt}개</td>
										</tr>
										<tr>
											<th class="board_B_cnt">자유게시판</th>
											<td>${vo.board_B_cnt}개</td>
										</tr>
										<tr>
											<th class="board_I_cnt">정보공유</th>
											<td>${vo.board_I_cnt}개</td>
										</tr>
										<tr>
											<th class="board_G_cnt">갤러리</th>
											<td>${vo.board_G_cnt}개</td>
										</tr>
										<tr>
											<th class="board_T_cnt">중고거래</th>
											<td>${vo.board_T_cnt}개</td>
										</tr>
										<tr>
											<th class="board_N_cnt">공지사항</th>
											<td>${vo.board_N_cnt}개</td>
										</tr>
										<tr>
											<th class="board_F_cnt">FAQ</th>
											<td>${vo.board_F_cnt}개</td>
										</tr>
										<tr>
											<th class="board_E_cnt">이벤트</th>
											<td>${vo.board_E_cnt}개</td>
										</tr>
										<tr>
											<th class="board_M_cnt">1:1문의</th>
											<td>${vo.board_M_cnt}개</td>
										</tr>

									</table>
								</c:forEach>
							</div>
							<div id="modify"></div>
						</div>
					</div>
					<!-- mp_wrqp -->
				</div>
			</div>
		</section>
	</div>
	<!-- mypage_wrap -->
</div>
<!-- mypage -->


<!-- footer 영역 -->
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
