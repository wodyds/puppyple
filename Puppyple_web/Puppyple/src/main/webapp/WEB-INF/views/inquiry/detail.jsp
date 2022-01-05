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

<link rel="stylesheet" href="css/mypage.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/no_detail.css">
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
						<div class="inq_board_content_wrap">
							<div class="inq_board_title">${vo.board_title }</div>
							<div class="board_under_wrap">
								<div class="board_member_nickname">${vo.member_nickname }</div>
								<span class="board_writedate">${vo.board_writedate }</span>
							</div>
							<c:if test="${ not empty vo.board_filename }">
								<div style="text-align: right; width: 63%; margin: 0 auto;">${vo.board_filename }<a
										href="download.inq?id=${vo.id } " style="margin-left: 10px;"><i
										class="fas fa-download" style="margin-right: 5px;"></i></a>
								</div>
							</c:if>

							<div class="inq_detail_board_content">
								<c:if test="${ not empty vo.board_filename }">
									<a id="board_preview"></a>
								</c:if>
								<!-- 내용 중 엔터에 해당되는 부분을 <br> 태그로 치환 -->
								<p class="detail_board_content_inner">${fn:replace(vo.board_content, crlf, '<br>') }</p>
							</div>

							<div class="board_filename">
								<div class="board_filename_p"></div>
								<div class="board_filename_inner"></div>
							</div>

							<div style="width: 100%;">
								<div class="inq_comment_div">
									<div class="list_btn1">
										<a class="btn-fill" id="board_list"
											onclick="$('form').submit()">전체글 보기</a>
									</div>
									<div class="list_btn2">
										<c:if
											test="${vo.member_id eq loginInfo.member_id || loginInfo.member_admin eq 'Y'}">
											<a class="btn-fill"
												onclick=" $('form').attr('action', 'modify.inq'); $('form').submit()">글
												수정</a>
											<a class="btn-fill" id="inq_btn-delete"
												onclick=" if(confirm('정말 삭제하시겠습니까?')) { href='delete.inq?id=${vo.id }'} ">글
												삭제</a>
										</c:if>
									</div>

									<!-- 댓글 입력 처리 부분 -->
									<div class="comment">
										<div id="inq_comment_regist">
											<textarea id="comment"></textarea>
											<a class="btn-comment" onclick="comment_regist()">댓글 등록</a>
										</div>
										<div id="inq_comment_list"></div>
									</div>
								</div>
							</div>



							<!-- 목록 요청에 필요한 데이터를 post 방식으로 전달하는 방법 -->
							<form action="list.inq" method="post">
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

							<!-- 이미지를 크게 볼 수 있도록 처리 (pupup 형태) -->
							<div id="popup-background"></div>
							<div id="popup" class="center"></div>

						</div>
					</div>
					<!-- mp_wrqp -->
			</section>
		</div>
		<!-- mypage_wrap -->
	</div>
	<!-- mypage -->
</body>

<script type="text/javascript" src='js/board_file_check.js'></script>
<script type="text/javascript">
	// 댓글 등록하기
	function comment_regist(){
		if(${empty loginInfo}) { // 로그인 정보가 없으면 
			alert('댓글을 등록하려면 로그인하세요');
			return ;
		}else if($.trim($('#comment').val()) == ''){ // 로그인은 되어 있는데 댓글을 적지 않았다면
			alert('댓글을 입력하세요!');
		$('#comment').val('');
		$('#comment').focus();
		return;
			
		}
		$.ajax ({
			// 경로 형태로 url 지정
			url : "inquiry/comment/regist"
			, data : {pid:${vo.id}, content:$('#comment').val() }
			, success : function (response) {
				if(response){	// ture == ture T, false == true F
					alert('댓글이 등록되었습니다.');
				$('#comment').val('');
				comment_list();	// 댓글 목록 조회 요청 함수
				}else 
					alert('댓글 등록에 실패하였습니다.')
			}, error : function (req,text){
				alert(text + ":" + req.status);
			}
			
			
		});
		
		
	}
		function comment_list() {
			$.ajax({
				url: "inquiry/comment/list/${vo.id}"
//				, data : {pid:${vo.id}}
				, success : function(response) {
					$('#inq_comment_list').html(response);
				}, error : function(req,text){
					alert(text +  ":" + req.status);
				}
			})
		}
		
	</script>

<script type="text/javascript">
		$(function() {
			// 첨부된 파일이 이미지 파일인 경우 미리보기 되게끔..
			if(${ !empty vo.board_filename}){ // 첨부 파일이 있다면
				if(isImage('${vo.board_filename}')){ // 이미지 파일이면
					$('#board_preview').html('<img src="${vo.board_filepath}" id="board_preview-img" />');
					
				}
			}
			comment_list();	// 댓글 목록 조회 함수 호출
		});
		/* $(document).on('click', '#board_preview-img', function() {
			$('#popup, #popup-background').css('display', 'block');
			$('#popup').html('<img src="${vo.board_filepath}" class="popup" />');
		}).on('click', '#popup-background', function() {
			$('#popup, #popup-background').css('display', 'none');
		}) */
		</script>
<!-- footer 영역 -->
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</html>