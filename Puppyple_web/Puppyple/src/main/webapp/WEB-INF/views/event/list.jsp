
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
<%-- <link rel="stylesheet" href="<c:url value='/resources/css/common.css'/>" />
<link rel="stylesheet" href="<c:url value='/resources/css/list.css'/>" /> --%>
</head>
<body>
	<div id="content_wrap">

		<div id="community_menubar">
			<ul class="notice_menuber">
				<li><a href="list.no">공지사항</a></li>
				<li><a href="list.faq">F A Q</a></li>
				<li><a href="list.ev" class="on_listpage">이 벤 트</a></li>
			</ul>
			<div class="notice_title_div">
				<h1 class="title">
					이벤트<span class="small_title"><span class="small_title_l">|</span>
						event</span>
				</h1>
			</div>
		</div>


		<div class="event_list">
			<ul class="grid">
				<li><a href="event_detail1.ev">
						<div id="event_banner">
							<img src="imgs/puppypleevent1_banner.jpg" alt="puppyple_event">
						</div>
						<div id="event_title">반려동물 촬영 이벤트</div>
						<div id="event_date">2021.10.12 ~ 2021.10.21</div>
				</a></li>
			</ul>
		</div>

		<div class="event_list">
			<ul class="grid">
				<li>
					<a href="event_detail2.ev">
						<div id="event_banner">
							<img src="imgs/puppypleevent2_banner.jpg" alt="puppyple_event">
						</div>
						<div id="event_title">퍼피플 이벤트 캠핑·리조트 숙박권</div>
						<div id="event_date">2021.11.10 ~ 2021.11.21</div>
					</a>
				</li>
			</ul>
		</div>

	</div>

</body>
<!-- footer 영역 -->
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</html>
