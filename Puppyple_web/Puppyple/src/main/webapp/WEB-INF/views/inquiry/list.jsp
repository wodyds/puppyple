<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<!-- header영역 -->
<jsp:include page="/WEB-INF/views/include/header.jsp" />
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/mypage.css">
<link rel="stylesheet" href="css/common.css">
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
						<h3>1:1문의</h3>
					</div>

					<div id="mp_table_wrap">
						<form action="list.inq" method="post">
							<input type="hidden" name="curPage" value="1" /> <input
								type="hidden" name="id" />
							<!-- detail에 보내질 id -->
							<input type="hidden" name="flag" value="M" />

							<table style="width: 100%; border-collapse: collapse;"
								cellspacing="0" cellpadding="0">
								<thead class="list-title-mypage">
									<tr>
										<!-- <th class=''>No</th> -->
										<th class=''>제목</th>
										<th class=''>작성자</th>
										<th class=''>등록일</th>
									</tr>
								</thead>
								<tbody id="mp_post_tbody">
									<c:if test="${ empty page.list }">
										<tr>
											<td id="no_content">등록된 글이 없습니다.</td>
										</tr>
									</c:if>
									<c:forEach items="${page.list}" var="vo">
										<c:if
											test="${vo.member_id eq loginInfo.member_id || loginInfo.member_admin eq 'Y'}">
											<tr>
												<%-- <td class="text-content">${vo.no }</td> --%>
												<td class="text-content"><a
													href='detail.inq?id=${vo.id }'>${vo.board_title }</a></td>
												<td class="text-content">${vo.member_nickname }</td>
												<td>${vo.board_writedate }</td>
											</tr>
										</c:if>

									</c:forEach>
								</tbody>
							</table>
							<div class="inq_writing">
								<ul class="writing_wrap">
									<li><a href='new.inq' class="btn-fill">글쓰기</a></li>
								</ul>
							</div>
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
