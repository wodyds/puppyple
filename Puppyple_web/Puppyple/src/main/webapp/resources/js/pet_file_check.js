/** 첨부 파일 관련 처리 함수   */

// 첨부파일 선택시
$(document).on('change', '#pet_attach-file', function() {
	var attached = this.files[0];
	
	if(attached){ // 첨부된 파일이 있을 경우
		$('#pet_file-name').text(attached.name);
		$('#delete-file').css('display', 'inline');
		
		/* #pet_preview 태그가 존재하는지 */
		if($('.pet_preview').length > 0) {
			if(isImage(attached.name)){ // 이미지 파일이면 미리보기 처리함
				$('.pet_preview').html('<img src="" id="pet_preview-img" />');
				
				var reader = new FileReader();
				reader.onload = function(e) {
					$('#pet_preview-img').attr('src', e.target.result);
				}
				reader.readAsDataURL(attached );
			// 이미지 파일이 아니면 미리보기 처리하지 않음
			}else {
				$('.pet_preview').html('');
			} 
		}
		
	}else { // 첨부된 파일이 없을 경우
		$('#pet_file-name').text('');
		$('#delete-file').css('display', 'none');
	}
	
}).on('click','#delete-file', function(){ // 첨부 파일 삭제시
	if($('.pet_preview').length > 0){
		$('.pet_preview').html('');		// id=pet_preview가 있으면
	}
	$('#pet_file-name').text('');
	$('#delete-file').css('display', 'none');
	
	$('#pet_attach-file').val(''); // 파일 태그의 첨부된 파일 정보 없애기
	
})

/* 이미지 파일 여부 확인 */
function isImage(pet_filename){
	//abc.txt, abc.png, abc.jpg, abc.hwp, abc.tiff...
	var ext = pet_filename.substring(pet_filename.lastIndexOf('.') + 1).toLowerCase();
	
	// 확장자 png, jpg, jpeg, gif, bmp, tiff...
	var imgs = ['png', 'jpg', 'jpeg', 'gif', 'bmp', 'pcx', 'tiff', 'jfif'];
	
	if(imgs.indexOf(ext) > -1) return true;
	else return false;
	
}