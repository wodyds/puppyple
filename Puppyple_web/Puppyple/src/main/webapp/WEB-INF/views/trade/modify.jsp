<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<!-- header영역 -->
 <jsp:include page="/WEB-INF/views/include/header.jsp"/> 
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/common.css">
<script type="text/javascript" src='js/common.js'></script>
<script type="text/javascript" src='js/file_check.js'></script>
<!-- <script type="text/javascript" src='js/file_check.js'></script> -->
</head>
<body>
	<div id="new_content_wrap">
		
		<div class="modify_title_div">
			<h1 class="modify_title">중고거래 글 수정</h1>
		</div>
		<form method="post" enctype="multipart/form-data" action="update.tra">
		<input type="hidden" name='id' value="${vo.id }" />
		<input type="hidden" name='attach' />
			<table>
				<tr>
				<th class='w-px120'>제목</th>
				<td><input type="text" name='board_title' title='제목'  class="new_title_wrap"  
					value="${vo.board_title }" class='chk' autofocus/></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name='board_content' class="new_content_wrap"  class='chk' title='내용'>${vo.board_content}</textarea></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				 <td>
				 <div class="board_file-name">
				<!-- 첨부 파일이 없는 경우, 첨부된 파일 삭제하고 다른 파일로 변경하는 경우 -->
				 	<label>
				 		<a><img src='imgs/select.png' class='file-img' /></a>
				 		<input type="file" name='file' id='attach-file' />
				 	</label>
				 	<span id='file-name'>${vo.board_filename }</span>
				<!-- 첨부 파일이 있었는데 삭제하는 경우 -->	
					<a id='delete-file' style='display: ${empty vo.board_filename ? "none" : "inline"}'>
						<i class="fas fa-minus-circle"></i></a>
					</div>
				</td> 
			</tr>
			</table>
		</form>
		 <div class='button'>
			<a class='btn-fill'  onclick =" if ( emptyCheck() ) { $('[name=attach]').val($('#file-name').text() );  $('form').submit()  } " >저장</a>
			<a class='btn-empty' href='detail.tra?id=${vo.id}'>취소</a>
		</div> 
		
	</div>
<script type="text/javascript">
if(${ !empty vo.board_filename}){ 
	// 첨부 파일이 있는 글의 경우 삭제버튼 보이게
	$('#delete-file').css("display", "inline");{
		// 첨부 파일이 이미지 파일인 경우 미리보기 되게
		if(isImage('${vo.board_filename}')){ // 이미지 파일이면
			$('#preview').html('<img src="${vo.board_filepath}" id="preview-img" />');
			
		}
	}
}
</script>
</body>
<!-- footer 영역 -->
 <jsp:include page="/WEB-INF/views/include/footer.jsp"/> 
</html>