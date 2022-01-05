<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<!-- header영역 -->
<jsp:include page="/WEB-INF/views/include/header.jsp" />
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/common.css">
<script type="text/javascript" src='js/common.js'></script>
<script type="text/javascript"
	src='js/file_check.js?v<%=new Date().getTime()%>'></script>
</head>
<body>
	<div id="new_content_wrap">
		<div class="new_title_div">
			<h1 class="new_title">글쓰기</h1>
			<p></p>
		</div>
		<form action="insert.inq" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<th>제목</th>
					<td>
						<div class="new_title_wrap">
							<input type="text" name='board_title' title='제목' class='chk'
								id="board_title" maxlength="100" /> <input type="text"
								placeholder="(0/ 100)" id="textLengthCheck" disabled="disabled">
						</div>
					</td>
				</tr>
				<tr>
					<th>내용</th>
					<td><div class="new_content_wrap">
							<textarea name='board_content' title='내용' class='chk'
								id="board_content1"></textarea>
						</div></td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td class='left'><label> <a><img
								src='imgs/select.png' class='file-img' style="padding-left: 2px;"/></a> <input type="file"
							id='attach-file' name="file" />
					</label> <span id='file-name'></span> <a id='delete-file'><i
							class="fas fa-minus-circle"></i></a></td>
				</tr>
			</table>
		</form>

		<div class="button">
			<a class='btn-fill' onclick='if ( emptyCheck() ) $("form").submit()'>저장</a>
			<a class='btn-empty' href='list.inq'>취소</a>
		</div>

	</div>

</body>
<script>
	$("#board_title").keydown(function(e) {
		console.log("키다운!");
		var content = $(this).val();
		var conlength = getTextLength($(this).val());
		$("#textLengthCheck").val("(" + conlength + "/ 100)"); //실시간 글자수 카운팅
		if (conlength > 100) {
			alert("최대 100자까지 입력 가능합니다.");
			$("#board_title").val("");
			$('#textLengthCheck').html("(100 / 최대 100자)");
		}
	});
	function getTextLength(str) {
		var len = 0;

		for (var i = 0; i < str.length; i++) {
			if (escape(str.charAt(i)).length == 6) {
				len++;
			}
			len++;
		}
		return len;
	}
</script>
<!-- footer 영역 -->
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</html>