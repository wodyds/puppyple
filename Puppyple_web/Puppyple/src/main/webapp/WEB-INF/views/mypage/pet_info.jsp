<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
      <jsp:include page="/WEB-INF/views/include/mypage_side.jsp"/> 
      
      <!-- <section id="layout2"> 안에 페이지 전환  -->
      <section id="layout2">
         <div>
            <div id="mypage_menu_title">
               <h3>반려동물정보</h3>
            </div>
            <div id="mp_wrap">
               <div id="pet_info">
                  <c:forEach items="${list }" var="vo">
                  
                  <div id="pet_info_table">
   
                     <div id="pet_list_layout">
                        <div id="pet_list_inner1">
                    	<c:if test="${ not empty vo.pet_filepath }">
						<a style="background-image : url(${vo.pet_filepath });" id="pet_preview" ></a>
						<!-- 미리보기 이미지가 나오는곳 -->
					</c:if> 
					<c:if test="${empty vo.pet_filepath }">
						<!-- 파일이미지가 없을 경우 기본 이미지가 나오는곳 -->
						<a id="pet_preview_not" ></a>
					</c:if>
                        </div>
                        <div id="pet_list_inner2">   
                           <div id="pet_name">
                              <span class="title">이름</span>
                              <span class="content">${vo.pet_name}</span>
                           </div>
                           <div id="pet_age">
                              <span class="title">나이</span>
                              <span class="content">${vo.pet_age}</span>
                           </div>
                           <div id="pet_gender">
                              <span class="title">성별</span>
                             <c:if test="${vo.pet_gender eq 'M'}">
                              <span class="content">수컷</span>
                             </c:if>
                             <c:if test="${vo.pet_gender eq 'F'}">
                              <span class="content">암컷</span>
                             </c:if>
                           </div>
                           <div id="pet_breed">
                              <span class="title">견종</span>
                              <span class="content">${vo.pet_breed}</span>
                           </div>
                           <div id="pet_weight">
                              <span class="title">무게</span>
                              <span class="content">${vo.pet_weight}</span>
                           </div>
                           <div id="pet_nuetering">
                              <span class="title">중성화</span>
                              <c:if test="${vo.pet_nuetering eq 'Y'}">
                              <span class="content">O</span>
                             </c:if>
                              <c:if test="${vo.pet_nuetering eq 'N'}">
                              <span class="content">X</span>
                             </c:if>
                           </div>
                        </div>   
                     </div><!-- pet_list_layout -->
                     
                     <div id="pet_modifiy_box">
                        <a href="pet_info_modifypage.pet?pet_id=${vo.pet_id }"><input type="button" id="pet_modifiy_btn" value="수정" /></a>
                        <a onclick=" if(confirm('정말 삭제하시겠습니까?')) { href='pet_info_delete.pet?pet_id=${vo.pet_id }'} "><input type="button" id="pet_delete_btn" value="삭제" /></a>
                     </div><!-- pet_modifiy_box -->
                     
                  </div><!-- pet_info_table -->
                  </c:forEach>
                  
                  <div id="addPet">
                     <a href="pet_info_new.pet"><input type="button" id="addPet_btn" value="+ 추가" /></a> 
                  </div>
                  
               </div>
            </div><!-- mp_wrqp -->
         </div>
      </section> 
      
   </div><!-- mypage_wrap --> 
</div><!-- mypage --> 
<script type="text/javascript" src='js/pet_file_check.js?v<%=new Date().getTime()%>'></script>

<script type="text/javascript">
		$(function() {
			// 첨부된 파일이 이미지 파일인 경우 미리보기 되게끔..
			if(${ !empty vo.pet_filename}){ // 첨부 파일이 있다면
				if(isImage('${vo.pet_filename}')){ // 이미지 파일이면
					$('#pet_preview').html('<img src="${vo.pet_filepath}" id="pet_preview-img" />');
					
					}
				}
		});

</script>
</body>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</html>