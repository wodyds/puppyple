<%@page import="java.util.Date"%>
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
				<li><a href="list.com" >자유게시판</a></li>
				<li><a href="list.info">정보 공유</a></li>
				<li><a href="list.gal">갤 러 리</a></li>
				<li><a href="list.tra" class="on_listpage">중고 거래</a></li>
			</ul>
			<div class="title_div">
				<h1 class="title">중고거래<span class="small_title"><span class="small_title_l">|</span> trade</span></h1>
			</div>
		</div>
	
		<form action="list.tra" method="post">
			<input type="hidden" name="curPage" value="1" />
			<input type="hidden" name="id" /><!-- detail에 보내질 id -->
			<input type="hidden" name="flag" value="T"/>
	
			<div id='list-top'>
				<div class="wrap">
					<ul class="select">
						<li>
							<select name='pageList' class='w-px75' onchange="$('form').submit()" >
								<option value='10' ${page.pageList eq 10 ? 'selected' : '' }>10개씩</option>
								<option value='15' ${page.pageList eq 15 ? 'selected' : '' } >15개씩</option>
								<option value='20' ${page.pageList eq 20 ? 'selected' : '' } >20개씩</option>
								<option value='25' ${page.pageList eq 25 ? 'selected' : '' } >25개씩</option>
								<option value='30' ${page.pageList eq 30 ? 'selected' : '' } >30개씩</option>
							</select>
						</li>
					</ul>
					
					<div id="search">
						<ul class="search_wrap">
							<li class="search_eg1">
								<select name="search" class='w-px65'>
									<option value="all" ${page.search eq 'all' ? 'selected' : '' }>전체</option>
									<option value="board_title" ${page.search eq 'board_title' ? 'selected' : ''}>제목</option>
									<option value="board_content" ${page.search eq 'board_content' ? 'selected' : ''}>내용</option>
									<option value="member_id" ${page.search eq 'member_id' ? 'selected' : ''}>작성자</option>
								</select>
							</li>
							<li class="search_eg2"><input type="text" id="keyword" name="keyword" value="${page.keyword }" class="w-pw300">
							<a class="btn-fill" onclick="$('form').submit()">검색</a></li>
						</ul>
					</div>

				</div>
			</div>
		
		<div id="list_wrap">
			<ul id=list_grid_wrap>
				<!-- 조회된 목록이 없을 경우 정보 표시 -->
					<c:if test="${ empty page.list }">
						<tr>
							<td><p id="no_content">등록된 글이 없습니다.</p></td>
						</tr>
					</c:if>
				<c:forEach items="${page.list }" var="vo">
					<li>
						<c:if test="${ not empty vo.board_filename }">
							<a style="background-image : url(${vo.board_filepath });" id="preview" href='detail.tra?id=${vo.id }'></a>
							<!-- 미리보기 이미지가 나오는곳 -->
						</c:if> 
						<c:if test="${empty vo.board_filename }">
							<a id="preview_not" href='detail.tra?id=${vo.id }'></a>
							<!-- 파일이미지가 없을 경우 기본 이미지가 나오는곳 -->
						</c:if>
	
						<div class="list_grid_title">
							<a href='detail.tra?id=${vo.id }'>${vo.board_title }</a>
							 <%-- <span> ${empty vo.board_filename ? '' : '<img src="imgs/file.png" class="file-img" style="cursor: default;"/> '}</span> --%>
						</div>
						<div class="list_grid_user">${vo.member_nickname }</div>
						<div class="list_grid_readcnt">
							<i class="fa fa-eye"></i>&nbsp;[${vo.board_readcnt }]<span
								class="list_grid_date">${vo.board_writedate }</span>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>	
		
		</form>

		<!-- 로그인 된 경우만 글쓰기 가능  -->
		<c:if test="${! empty loginInfo }">
			<div class="writing">
				<ul class="writing">
					<li><a href='new.tra' class="btn-fill">글쓰기</a></li>
				</ul>
			</div>
		</c:if>

		<div>
			<jsp:include page="/WEB-INF/views/include/page.jsp" />
		</div>
	</div>	
		
<script type="text/javascript" src='js/file_check.js?v<%=new Date().getTime()%>'></script>

<script type="text/javascript">
		$(function() {
			// 첨부된 파일이 이미지 파일인 경우 미리보기 되게끔..
			if(${ !empty vo.board_filename}){ // 첨부 파일이 있다면
				if(isImage('${vo.board_filename}')){ // 이미지 파일이면
					$('#preview').html('<img src="${vo.board_filepath}" id="preview-img" />');
					
					}
				}
		});

</script>
</body>
<!-- footer 영역 -->
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</html>
