/** 공통 --- 입력항목 확인 */

// 내용이 없을 경우 저장 불가 처리

function emptyCheck() {
	
	var ok = true;
	$('.chk').each(function(){
		
		// 입력 값이 없다면
		if($(this).val() == ''){
		// 해당 태그의 속성 중 title 값을 가져와 + '을 입력하세요!' 경고창을 띄움
			alert($(this).attr('title') + '을 입력하세요!');
		// 해당 태그의 포커스 위치
			$(this).focus();
			ok = false;
			return ok;
		}
		
		
	});
	return ok;
}