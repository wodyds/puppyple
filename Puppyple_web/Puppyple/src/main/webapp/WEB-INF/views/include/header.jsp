<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>퍼피플 PUPPYPLE</title>
<link rel="shortcut icon" href="imgs/favicon.ico" type="image/x-icon">
<link rel="icon" href="imgs/favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css"
	href="https://cdn.rawgit.com/innks/NanumSquareRound/master/nanumsquareround.min.css">
<link rel="stylesheet" type="text/css"
	href="//cdn.rawgit.com/young-ha/webfont-archive/master/css/Godo.css">
<script src="https://kit.fontawesome.com/794175a030.js"
	crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/header.css">
<link rel="stylesheet" href="css/jquery.bxslider.min.css">
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/jquery.bxslider.min.js"></script>
<script src="js/slider.js"></script>
<script src="js/menu.js"></script>
</head>
<body>
	<!-- 헤더 영역 시작 -->
	<header id="header">
		<div id="hd_wrap">

			<h1 class="logo">
				<a href='<c:url value='/' />'> <img src="imgs/logo.png"
					alt="퍼피플로고"></a>
			</h1>

			<div id="gnb_wrap">
				<div class="gnb_sub_wrap">
					<div class="gnb_sub">
						<ul>
							<!-- 로그인을 하지 않은 경우 -->
							<c:if test="${empty loginInfo }">
								<li><a href="login">로그인</a></li>
								<li><a href="member">회원가입</a></li>
							</c:if>
							<!-- 로그인을 한 경우 -->
							<c:if test="${! empty loginInfo}">
								<li>${loginInfo.member_nickname }님</li>
								<c:if test="${loginInfo.member_admin eq 'Y' }">
									<li><a href="admin_list.mp">관리자페이지</a></li>
								</c:if>
								<c:if
									test="${empty loginInfo.member_admin || loginInfo.member_admin eq 'N' }">
									<li><a
										href="member_info.mp?member_id=${loginInfo.member_id }">마이페이지</a></li>
								</c:if>
								<li><a href="logout">로그아웃</a></li>
							</c:if>

						</ul>
					</div>
				</div>
				<%-- <c:url value='/' /> --%>
				<nav id="menu">
					<ul class="gnb">
						<li><a href="<c:url value='/#introduce' />">퍼피플소개</a></li>
						<li><a href="map.map">퍼피맵</a></li>
						<li class="hover community"><a href="list.com">커뮤니티</a>
							<div id="lnb_wrap">
								<ul class="lnb lnb_menu1">
									<li><a href="list.com">자유게시판</a></li>
									<li><a href="list.info">정보 공유</a></li>
									<li><a href="list.gal">갤 러 리</a></li>
									<li><a href="list.tra">중고 거래</a></li>
								</ul>
							</div></li>
						<li><a href="youtube_daily.daily">펫튜브</a></li>
						<li class="hover notice"><a href="list.no">퍼피플소식</a>
							<div id="lnb_wrap">
								<ul class="lnb lnb_menu2">
									<li><a href="list.no">공지사항</a></li>
									<li><a href="list.faq">F A Q</a></li>
									<li><a href="list.ev">이벤트</a></li>
								</ul>
							</div></li>
					</ul>
				</nav>

			</div>

		</div>
	</header>
	<!-- 헤더 영역 끝 -->