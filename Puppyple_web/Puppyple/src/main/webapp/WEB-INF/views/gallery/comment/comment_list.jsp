<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:forEach items="${list }" var="vo" varStatus="status">
		${status.index eq 0 ? '<hr>' : '' }
		<div data-id="${vo.id }" class="left" id="comment_inner">${vo.member_nickname }<span
			id="inner_writerdate">[${vo.writedate }]</span>
		<!-- 로그인한 사용자가 작성한 댓글인 경우 수정/삭제가 가능 -->
		<c:if
			test="${vo.member_id eq loginInfo.member_id || loginInfo.member_admin eq 'Y'}">
			<span id="inner_modify"> <a class="btn-fill-s btn-modify-save">수정</a>
				<a class="btn-fill-s btn-delete-cancel">삭제</a>
			</span>
		</c:if>
		<div class="original" id="inner_content">${fn:replace(fn:replace(vo.content, lf, '<br>' ), crlf, '<br>') }</div>
		<div class="modify" style="display: none; margin-top: 6px;"></div>
	</div>
	<hr>
</c:forEach>

<script>
	$('.btn-modify-save').on(
			'click',
			function() {
				var $div = $(this).closest('div');

				if ($(this).text() == '수정') {

					//$div.children('.modify').css('height', $div.children('.original').height()-6);

					//줄바꿈 태그 변환
					var tag = "<textarea style='width:99%; height:90%;'>"
							+ $div.children('.original').html().replace(
									/<br>/g, '\n') + "</textarea>";
					$div.children('.modify').html(tag);
					display($div, 'm');
				} else {
					var comment = {
						id : $div.data('id'),
						content : $div.children('.modify').find('textarea')
								.val()
					};
					//alert(JSON.stringify(comment)); //JSON형태로 잘 출력되는지 확인

					$.ajax({
						url : 'gallery/comment/update',
						data : JSON.stringify(comment),
						contentType : 'application/json',
						type : 'post',
						success : function(data) {
							alert(data);
							comment_list();
						},
						error : function(req, text) {
							alert(text + ':' + req.status);
						}
					});
				}
			});

	//삭제 / 취소 버튼 클릭
	$('.btn-delete-cancel').on('click', function() {
		var $div = $(this).closest('div');

		if ($(this).text() == '취소') {
			display($div, 'd');
		} else {
			if (confirm('정말 삭제하시겠습니까?')) {
				$.ajax({
					url : 'gallery/comment/delete/' + $div.data('id'),
					success : function() {
						comment_list();
					},
					error : function(req, text) {
						alert(text + ':' + req.status);
					},
				});
			}
		}
	})

	function display(div, mode) {
		//수정 상태(m) : 저장/취소, 원글 안 보이고, 수정 글은 보이고 
		//보기 상태(d) : 수정/삭제, 원글 보이고, 수정 글은 안 보이고
		div.find('.btn-modify-save').text(mode == 'm' ? '저장' : '수정');
		div.find('.btn-delete-cancel').text(mode == 'm' ? '취소' : '삭제');
		div.children('.original')
				.css('display', mode == 'm' ? 'none' : 'block');
		div.children('.modify').css('display', mode == 'm' ? 'block' : 'none');
	}
</script>
