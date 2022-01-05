<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<!-- header영역 -->
<jsp:include page="/WEB-INF/views/include/header.jsp" />
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/no_detail.css">
</head>
<body>
	<div id="content_wrap">
		<div class="notice_title_div">
			<h1 class="title">
				공지사항<span class="small_title"><span class="small_title_l">|</span>
					notice</span>
			</h1>
		</div>
		<div class="board_content_wrap">
			<div class="board_title">${vo.board_title }</div>
			<div class="board_under_wrap">
				<div class="board_member_nickname">${vo.member_nickname }</div>
				<span class="board_writedate">${vo.board_writedate }</span>
				<div class="board_readcnt">
					<i class="fa fa-eye" style="margin-right: 6px;"></i>${vo.board_readcnt }</div>
			</div>
			<div class="board_filename_c">
				<c:if test="${ not empty vo.board_filename }">
					<div>${vo.board_filename }<a
							href="download.faq?id=${vo.id } " style="margin-left: 9px;"><i
							class="fas fa-download"></i></a>
					</div>
				</c:if>
			</div>
		</div>

		<div class="detail_board_content">
			<!-- 내용 중 엔터에 해당되는 부분을 <br> 태그로 치환 -->
			<p class="detail_board_content_inner">${fn:replace(vo.board_content, crlf, '<br>') }</p>
		</div>

		<div class="board_filename">
			<div class="board_filename_p"></div>
			<div class="board_filename_inner"></div>
		</div>

		<div style="width: 100%;">
			<div class="button comment_div">
				<div class="list_btn1">
					<a class="btn-fill" id="board_list" onclick="$('form').submit()">전체글
						보기</a>
				</div>
				<div class="list_btn2">
					<c:if
						test="${vo.member_id eq loginInfo.member_id || loginInfo.member_admin eq 'Y'}">
						<a class="btn-fill" id="news_btn-modify"
							onclick=" $('form').attr('action', 'modify.no'); $('form').submit()">글
							수정</a>
						<a class="btn-fill" id="news_btn-delete"
							onclick="  if(confirm('정말 삭제하시겠습니까?')) { href='delete.no?id=${vo.id }'} ">글
							삭제</a>
					</c:if>
				</div>
			</div>
		</div>

		<!-- 목록 요청에 필요한 데이터를 post 방식으로 전달하는 방법 -->
		<form action="list.no" method="post">
			<!-- 글 id -->
			<input type="hidden" name="id" value="${vo.id }" />
			<!-- 검색조건 -->
			<input type="hidden" name="search" value="${page.search }" />
			<!-- 검색어 -->
			<input type="hidden" name="keyword" value="${page.keyword }" />
			<!-- 현재페이지 -->
			<input type="hidden" name="curPage" value="${page.curPage }" />
			<!-- 페이지당 보여질 목록수 -->
			<input type="hidden" name="pageList" value="${page.pageList }" />
		</form>

	</div>
</body>
<!-- footer 영역 -->
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</html>