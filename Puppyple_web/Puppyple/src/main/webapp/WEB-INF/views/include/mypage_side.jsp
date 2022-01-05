<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<section id="layout1">
		<div>

			<div class="mypage_title">
				<c:if test="${loginInfo.member_admin eq 'Y' }">
					<h3>관리자페이지</h3>
				</c:if>
				<c:if
					test="${empty loginInfo.member_admin || loginInfo.member_admin eq 'N' }">
					<h3>마이페이지</h3>
				</c:if>
			</div>
			<div class="color"></div>
			<ul class="mypage_menu">
				<c:if test="${loginInfo.member_admin eq 'Y' }">
					<li><a href="admin_list.mp">회원관리</a></li>
					<li><a href="admin_current.mp">퍼피플 회원정보</a></li>
					<li><a href="pet_info.pet">반려동물정보</a></li>
					<li><a href="mypage_post.mp">내가 쓴 글 보기</a></li>
					<li><a href="mypage_favorites.mp">좋아요 한 글 보기</a></li>
					<li><a href="list.inq">1:1문의</a></li>
				</c:if>
				<c:if
					test="${empty loginInfo.member_admin || loginInfo.member_admin eq 'N' }">
					<li><a href="member_info.mp?member_id=${loginInfo.member_id }">회원정보</a></li>
					<li><a href="pet_info.pet">반려동물정보</a></li>
					<li><a href="mypage_post.mp">내가 쓴 글 보기</a></li>
					<li><a href="mypage_favorites.mp">좋아요 한 글 보기</a></li>
					<li><a href="list.inq">1:1문의</a></li>
				</c:if>
			</ul>

		</div>
	</section>

</body>
</html>