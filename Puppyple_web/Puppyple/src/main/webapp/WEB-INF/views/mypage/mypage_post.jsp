<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<!-- header영역 -->
<jsp:include page="/WEB-INF/views/include/header.jsp" />
<link rel="stylesheet" href="css/mypage.css">
<link rel="stylesheet" href="css/common.css">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
						<h3>내가 쓴 글 목록</h3>
					</div>

					<div id="mp_table_wrap">
						<form action="mypage_post.mp" method="post">
							<input type="hidden" name="curPage" value="1" />
							<table style="width: 100%; border-collapse: collapse;"
								cellspacing="0" cellpadding="0">
								<thead class="list-title-mypage">
									<tr>
										<th class=''>게시판 종류</th>
										<th class=''>제목</th>
										<th class=''>등록일</th>
										<th class=''>조회수</th>
									</tr>
								</thead>
								<tbody id="mp_post_tbody">
									<c:forEach items="${page.list}" var="vo">
										<tr>
											<td><c:if test="${vo.flag eq 'B'}">
													자유게시판
											<td class="text-content"><a
														href='detail.com?id=${vo.id }'>${vo.board_title }</a></td>
												</c:if> <c:if test="${vo.flag eq 'I'}">
													정보공유
											<td class="text-content"><a
														href='detail.info?id=${vo.id }'>${vo.board_title }</a></td>
												</c:if> <c:if test="${vo.flag eq 'G'}">
													갤러리
											<td class="text-content"><a
														href='detail.gal?id=${vo.id }'>${vo.board_title }</a></td>
												</c:if> <c:if test="${vo.flag eq 'T'}">
													중고거래
											<td class="text-content"><a
														href='detail.tra?id=${vo.id }'>${vo.board_title }</a></td>
												</c:if> <c:if test="${vo.flag eq 'N'}">
													공지사항
											<td class="text-content"><a
														href='detail.no?id=${vo.id }'>${vo.board_title }</a></td>
												</c:if> <c:if test="${vo.flag eq 'F'}">
													FAQ
											<td class="text-content"><a
														href='detail.faq?id=${vo.id }'>${vo.board_title }</a></td>
												</c:if> <c:if test="${vo.flag eq 'M'}">
													1:1문의
											<td class="text-content"><a
														href='detail.inq?id=${vo.id }'>${vo.board_title }</a></td>
												</c:if> <c:if test="${vo.flag eq 'E'}">
													이벤트
											<td class="text-content"><a href='list.ev'>${vo.board_title }</a></td>
												</c:if></td>
											<td>${vo.board_writedate }</td>
											<td>${vo.board_readcnt}</td>
										</tr>
									</c:forEach>
								</tbody>

							</table>
						</form>
						<div>
							<jsp:include page="/WEB-INF/views/include/page.jsp" />
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
<!-- footer 영역 -->
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</html>