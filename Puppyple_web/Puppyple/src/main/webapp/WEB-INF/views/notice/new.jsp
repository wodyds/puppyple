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
<script type="text/javascript" src='js/file_check.js'></script>
</head>
<body>
	<div id="new_content_wrap">

		<div class="new_title_div">
			<h1 class="new_title">글쓰기</h1>
			<p></p>
		</div>

		<form action="insert.no" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<th>제목</th>
					<td><input type="text" class="new_title_wrap"
						name='board_title' title='제목' class='chk' autofocus /></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><textarea class="new_content_wrap" name='board_content'
							title='내용' class='chk'></textarea></td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td class='left'><label> <a><img
								src='imgs/select.png' class='file-img'
								style="padding-left: 13px;" /></a> <input type="file"
							id='attach-file' name="file" />
					</label> <span id='file-name'></span> <a id='delete-file'><i
							class="fas fa-minus-circle"></i></a></td>
				</tr>
			</table>
		</form>

		<div class="button">
			<a class='btn-fill' onclick='if ( emptyCheck() ) $("form").submit()'>저장</a>
			<a class='btn-empty' href='list.no'>취소</a>
		</div>

	</div>
</body>
<!-- footer 영역 -->
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</html>