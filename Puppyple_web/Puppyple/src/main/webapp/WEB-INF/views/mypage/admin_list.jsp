<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<!-- header영역 -->
<jsp:include page="/WEB-INF/views/include/header.jsp" />
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/mypage.css">
</head>
<body>
	<div id="mypage">
		<div id="mypage_wrap">
			<!-- <section id="layout1"> 마이페이지 메뉴바 고정-->
			<jsp:include page="/WEB-INF/views/include/mypage_side.jsp" />

			<!-- <section id="layout2"> 안에 페이지 전환  -->
			<section id="layout2">
				<div>
					<div id="mypage_menu_title">
						<h3>회원관리</h3>
					</div>
					<div id="mp_table_wrap">
						<form action="admin_list.mp" method="post">
							<input type="hidden" name="curPage" value="1" /> <input
								type="hidden" name="id" />
							<!-- detail에 보내질 id -->
							<div id='list-top'></div>
							<div>
								<div class="list-title">
									<ul>
										<li class='w-pct25'>회원아이디</li>
										<li class='w-pct25'>회원닉네임</li>
										<li class='w-pct25'>회원이메일</li>
										<li class='w-pct25'>등록일</li>
									</ul>
								</div>
								<div class="list-content">
									<!-- 조회된 목록이 없을 경우 정보 표시 -->
									<c:if test="${ empty page.list }">
										<div>
											<p>회원 정보가 없습니다.</p>
										</div>
									</c:if>
									<c:forEach items="${page.list}" var="vo">
										<ul id="mp_post_tbody">
											<li class="w-pct25"><a
												href='admin_detail.mp?member_id=${vo.member_id }'>${vo.member_id}</a></li>
											<li class="w-pct25">${vo.member_nickname }</li>
											<li class="w-pct25">${vo.member_email }</li>
											<li class="w-pct25">${vo.member_joindate }</li>
										</ul>
									</c:forEach>
								</div>
							</div>
							<div id="search" class="mp_search">
								<ul class="search_wrap">
									<li class="search_eg1"><select name="search"
										class='w-px65'>
											<option value="all"
												${page.search eq 'all' ? 'selected' : '' }>전체</option>
											<option value="member_id"
												${page.search eq 'member_id' ? 'selected' : ''}>아이디</option>
											<option value="member_nickname"
												${page.search eq 'member_nickname' ? 'selected' : ''}>닉네임</option>
									</select></li>
									<li class="search_eg2"><input type="text" id="keyword"
										name="keyword" value="${page.keyword }" class="w-pw300">
										<a class="btn-fill" onclick="$('form').submit()">검색</a></li>
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
