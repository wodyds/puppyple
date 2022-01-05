<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/common.css">
</head>
<body>
	<nav id="youtube_menu">
		<div class="youtube_title_div">
			<h1 class="title">
				펫튜브<span class="small_title"><span class="small_title_l">|</span>
					pettube</span>
			</h1>
		</div>

		<div id="youtube_menubar">
			<ul class="youtube_gnb">
				<li><a href="youtube_daily.daily"
					class="${category eq 'daily' ? 'you_active' : '' }">일상</a></li>
				<li><a href="youtube_edu.edu"
					class="${category eq 'edu' ? 'you_active' : '' }">교육</a></li>
				<li><a href="youtube_health.health"
					class="${category eq 'health' ? 'you_active' : '' }">의료/건강</a></li>
				<li><a href="youtube_beauty.beauty"
					class="${category eq 'beauty' ? 'you_active' : '' }">미용</a></li>
				<li><a href="youtube_snack.snack"
					class="${category eq 'snack' ? 'you_active' : '' }">간식</a></li>
			</ul>
		</div>
	</nav>
</body>
</html>