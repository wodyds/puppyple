<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/mypage.css">

</head>
<jsp:include page="/WEB-INF/views/include/header.jsp" />
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
						<h3>반려동물정보 수정</h3>
					</div>
					<div id="mp_wrap">
						<div id="pet_info_modify">
							<h5>수정할 반려동물 정보를 입력하세요.</h5>
							<form action="pet_info_modify.pet?pet_id=${vo.pet_id }"
								method="post" enctype="multipart/form-data"
								onsubmit="return go_modify()">
								<input type="hidden" name='attach' />
								<div id="form_wrap">

									<div id="photoForm">
										<label>사진</label>
										<div id="photoForm_inner">
											<div class="pet_preview"></div>
											<div class="file-wrap">
												<label for="pet_attach-file" class="file-label"> <input
													type="file" id='pet_attach-file' class="file" name="file" />
												<!-- 숨김 --> <a><i class="fas fa-plus-circle"></i></a></label>
												<div id="pet_file-name">${vo.pet_filename }</div>
												<a href="" id='delete-file'
													style='display: ${empty vo.pet_filename ? "none" : "inline"}'><i
													class="fas fa-minus-circle"></i></a>
											</div>
										</div>
									</div>

									<div id="nameForm">
										<label for="pet_name">이름</label> <input type="text"
											name="pet_name" id="pet_name" value="${vo.pet_name}"
											autofocus />
										<div class="warning pet_name_warning_notext">
											<i class="fas fa-exclamation-circle"></i>
											<p>필수 정보입니다.</p>
										</div>
									</div>

									<div id="ageForm">
										<label for="pet_age">나이</label> <input type="text"
											name="pet_age" id="pet_age" value="${vo.pet_age}" />
										<div class="warning pet_age_warning_notext">
											<i class="fas fa-exclamation-circle"></i>
											<p>필수 정보입니다.</p>
										</div>
									</div>

									<div id="breedForm">
										<label for="pet_breed">견종</label> <input type="text"
											name="pet_breed" id="pet_breed" value="${vo.pet_breed}" />
										<div class="warning pet_breed_warning_notext">
											<i class="fas fa-exclamation-circle"></i>
											<p>필수 정보입니다.</p>
										</div>
									</div>

									<div id="weightForm">
										<label for="pet_weight">무게</label> <input type="text"
											name="pet_weight" id="pet_weight" value="${vo.pet_weight}" />
										<div class="warning pet_weight_warning_notext">
											<i class="fas fa-exclamation-circle"></i>
											<p>필수 정보입니다.</p>
										</div>
									</div>

									<div id="genderForm">
										<label>성별</label>
										<div id="genderForm_inner">
											<div class="radio-wrap">
												<c:if test="${vo.pet_gender eq 'M' }">
													<input type="radio" name="pet_gender" id="M" value="M"
														class="radio M" checked="checked" />
													<!-- 숨김 -->
													<label for="M" class="radio-label M"><span
														class="radio-text M">수컷</span></label>
													<input type="radio" name="pet_gender" id="F" value="F"
														class="radio F" />
													<!-- 숨김 -->
													<label for="F" class="radio-label F"><span
														class="radio-text F">암컷</span></label>
												</c:if>
												<c:if test="${vo.pet_gender eq 'F' }">
													<input type="radio" name="pet_gender" id="M" value="M"
														class="radio M" />
													<!-- 숨김 -->
													<label for="M" class="radio-label M"><span
														class="radio-text M">수컷</span></label>
													<input type="radio" name="pet_gender" id="F" value="F"
														class="radio F" checked="checked" />
													<!-- 숨김 -->
													<label for="F" class="radio-label F"><span
														class="radio-text F">암컷</span></label>

												</c:if>
											</div>
										</div>
									</div>

									<div id="nueteringForm">
										<label>중성화</label>
										<div id="nueteringForm_inner">
											<div class="radio-wrap">
												<c:if test="${vo.pet_nuetering eq 'Y' }">
													<input type="radio" name="pet_nuetering" id="Y" value="Y"
														class="radio Y" checked="checked" />
													<!-- 숨김 -->
													<label for="Y" class="radio-label Y"><span
														class="radio-text Y">Yes</span></label>
													<input type="radio" name="pet_nuetering" id="N" value="N"
														class="radio N" />
													<!-- 숨김 -->
													<label for="N" class="radio-label N"><span
														class="radio-text N">No</span></label>
												</c:if>
												<c:if test="${vo.pet_nuetering eq 'N' }">
													<input type="radio" name="pet_nuetering" id="Y" value="Y"
														class="radio Y" />
													<!-- 숨김 -->
													<label for="Y" class="radio-label Y"><span
														class="radio-text Y">Yes</span></label>
													<input type="radio" name="pet_nuetering" id="N" value="N"
														class="radio N" checked="checked" />
													<!-- 숨김 -->
													<label for="N" class="radio-label N"><span
														class="radio-text N">No</span></label>

												</c:if>
											</div>
										</div>
									</div>

								</div>
								<!-- form_wrap  -->

								<div id="end">
									<input type="submit" id="submit" value="수정"
										onclick="if(confirm('수정하시겠습니까?')) $('form').submit()" /> <input
										type="reset" id="reset" value="취소"
										onclick=" if(confirm('취소하시겠습니까?')) history.go(-1)" />
								</div>

							</form>


						</div>
						<!-- pet_info_modify -->
					</div>
					<!-- mp_wrqp -->
				</div>
			</section>
		</div>
		<!-- mypage_wrap -->
	</div>
	<!-- mypage -->
	<script type="text/javascript"
		src='js/pet_file_check.js?v<%=new Date().getTime()%>'></script>
	<script type="text/javascript">
   function go_modify() {
   
      if($('#pet_name').val() == ""){
          $('.pet_name_warning_notext').css('display','block').css('color','#b22de4');
          $('#pet_name').focus();
          return false;
      }else{
       $('.pet_name_warning_notext').css('display','none');
    }
      if($('#pet_age').val() == ""){
          $('.pet_age_warning_notext').css('display','block').css('color','#b22de4');
          $('#pet_age').focus();
          return false;
      }else{
       $('.pet_age_warning_notext').css('display','none');
    }
      if($('#pet_breed').val() == ""){
          $('.pet_breed_warning_notext').css('display','block').css('color','#b22de4');
          $('#pet_breed').focus();
          return false;
      }else{
       $('.pet_breed_warning_notext').css('display','none');
    }
      if($('#pet_weight').val() == ""){
          $('.pet_weight_warning_notext').css('display','block').css('color','#b22de4');
          $('#pet_weight').focus();
          return false;
      }else{
       $('.pet_weight_warning_notext').css('display','none');
    }
   }
   if(${ !empty vo.pet_filename}){ 
      // 첨부 파일이 있는 글의 경우 삭제버튼 보이게
      $('#delete-file').css("display", "inline");{
         // 첨부 파일이 이미지 파일인 경우 미리보기 되게
         if(isImage('${vo.pet_filename}')){ // 이미지 파일이면
            $('.pet_preview').html('<img src="${vo.pet_filepath}" id="pet_preview-img" />');
            
         }
      }
   }

</script>


</body>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</html>