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
</head>
<body>
	<div id="content_wrap">

		<div id="community_menubar">
			<ul>
				<li><a href="list.com">자유게시판</a></li>
				<li><a href="list.info" class="on_listpage">정보 공유</a></li>
				<li><a href="list.gal">갤 러 리</a></li>
				<li><a href="list.tra">중고 거래</a></li>
			</ul>
			<div class="title_div">
				<h1 class="title">
					정보공유<span class="small_title"><span class="small_title_l">|</span>
						information</span>
				</h1>
			</div>
		</div>

		<form action="list.info" method="post">
			<input type="hidden" name="curPage" value="1" /> <input
				type="hidden" name="id" />
			<!-- detail에 보내질 id -->
			<input type="hidden" name="flag" value="I" />

			<div id='list-top'>
				<div class="wrap">
					<ul class="select">
						<li><select name='pageList' class='w-px75'
							onchange="$('form').submit()">
								<option value='10' ${page.pageList eq 10 ? 'selected' : '' }>10개씩</option>
								<option value='15' ${page.pageList eq 15 ? 'selected' : '' }>15개씩</option>
								<option value='20' ${page.pageList eq 20 ? 'selected' : '' }>20개씩</option>
								<option value='30' ${page.pageList eq 30 ? 'selected' : '' }>30개씩</option>
						</select></li>
					</ul>

					<div id="search">
						<ul class="search_wrap">
							<li class="search_eg1"><select name="search" class='w-px65'>
									<option value="all" ${page.search eq 'all' ? 'selected' : '' }>전체</option>
									<option value="board_title"
										${page.search eq 'board_title' ? 'selected' : ''}>제목</option>
									<option value="board_content"
										${page.search eq 'board_content' ? 'selected' : ''}>내용</option>
									<option value="member_id"
										${page.search eq 'member_id' ? 'selected' : ''}>작성자</option>
							</select></li>
							<li class="search_eg2"><input type="text" id="keyword"
								name="keyword" value="${page.keyword }" class="w-pw300">
								<a class="btn-fill" onclick="$('form').submit()">검색</a></li>
						</ul>
					</div>
				</div>
			</div>

			<div id="list_wrap">
				<div class="list-title">
					<ul>
						<li class="w-pct7">No</li>
						<li class="w-pct50">제목</li>
						<li class="w-pct6">첨부파일</li>
						<li class="w-pct15">작성자</li>
						<li class="w-pct15">등록일</li>
						<li class="w-pct7">조회수</li>
					</ul>
				</div>
				<div class="list-content">
					<!-- 조회된 목록이 없을 경우 정보 표시 -->
					<c:if test="${ empty page.list }">
						<div>
							<p id="no_content">등록된 글이 없습니다.</p>
						</div>
					</c:if>
					<c:forEach items="${page.list}" var="vo">
						<ul>
							<li class="w-pct7">${vo.no}</li>
							<li class="w-pct50"
								style="text-align: left; padding-left: 65px; color: #7a7a7a;"><a
								href='detail.info?id=${vo.id }'>${vo.board_title }</a></li>
							<li class="w-pct6">${empty vo.board_filename ? '' : '<img src="imgs/file.png" class="file-img" style="cursor: default;"/>' }</li>
							<li class="w-pct15">${vo.member_nickname }</li>
							<li class="w-pct15">${vo.board_writedate }</li>
							<li class="w-pct7">${vo.board_readcnt}</li>
						</ul>
					</c:forEach>
				</div>
			</div>
		</form>

		<!-- 로그인된 경우만 글쓰기 가능  -->
		<c:if test="${! empty loginInfo }">
			<div class="writing">
				<ul class="writing_wrap">
					<li><a href='new.info' class="btn-fill">글쓰기</a></li>
				</ul>
			</div>
		</c:if>

		<div>
			<jsp:include page="/WEB-INF/views/include/page.jsp" />
		</div>
	</div>
</body>
<!-- footer 영역 -->
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</html>
