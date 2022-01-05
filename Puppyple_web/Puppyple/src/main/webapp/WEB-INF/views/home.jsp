<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="/WEB-INF/views/include/header.jsp" />
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

	<!-- 비주얼 영역 -->
	<div id="visual" class="slider">
		<div class="vis1"></div>
		<div class="vis2"></div>
		<div class="vis3"></div>
		<div class="vis4"></div>
		<div class="vis5"></div>
	</div>


	<!-- 콘텐츠 영역  -->
	<div id="contents">
		<section id="content1">
			<!-- 다운로드 -->
			<div id="cont1_wrap">
				<div class="cont1_left">
					<img src="imgs/mockup.png" class="mockup">
				</div>
				<div class="cont1_right">
					<h3>퍼피플 앱 다운로드</h3>
					<a><img src="imgs/google-play-badge.svg"
						class="down_img google"></a> <a><img
						src="imgs/app-store-badge.svg" class="down_img apple"></a>
				</div>
			</div>


			<!-- shapedivider -->
			<section class="curved">

				<div class="scroll-down-wrap no-border">
					<span class="section-down-arrow"> <span id="introduce">SCROLL</span>
						<path class="nectar-scroll-icon-path" fill="none" stroke="#111111"
							stroke-width="2" stroke-miterlimit="10"
							d="M15,1.118c12.352,0,13.967,12.88,13.967,12.88v18.76  c0,0-1.514,11.204-13.967,11.204S0.931,32.966,0.931,32.966V14.05C0.931,14.05,2.648,1.118,15,1.118z"></path>
					</span>
				</div>

				<h2>반갑습니다 퍼피플입니다!</h2>
			</section>
		</section>

		<section id="content2">
			<!-- iot기능 -->
		</section>
		<section id="content3">
			<!-- 지도기능 -->
		</section>
		<section id="content4">
			<!-- 커뮤니티 -->
		</section>
		<section id="content5">
			<!-- 유튜브 -->
		</section>

	</div>
	<div
		style="position: fixed; bottom: 50px; right: 50px; width: 48px; height: 48px; overflow: hidden; z-index: 9999;">
		<!-- <a href="#header" style="border:1px solid black; padding: 10px 13px 10px 13px; background-color: white; opacity: 0.7;">↑</a> -->
		<a href="#header" style="width: 0; height: 0;"><img
			src="imgs/arrow_top1.png"></a>
	</div>

</body>
<!-- footer 영역 -->
<jsp:include page="/WEB-INF/views/include/footer.jsp" />

</html>