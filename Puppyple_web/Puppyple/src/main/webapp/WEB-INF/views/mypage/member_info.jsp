<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- header영역 -->
<jsp:include page="/WEB-INF/views/include/header.jsp"/>
<link rel="stylesheet" href="css/mypage.css">
<!-- 마이페이지 영역 (layouy1은 메뉴바 고정 / 연결되는 페이지는 모두 layout2 안에서 표출시켜야 함)) -->
<div id="mypage">
	<div id="mypage_wrap">
		<!-- <section id="layout1"> 마이페이지 메뉴바 고정-->
		<jsp:include page="/WEB-INF/views/include/mypage_side.jsp"/> 
		
		<!-- <section id="layout2"> 안에 페이지 전환  -->
		<section id="layout2">
			<div>
				<div id="mypage_menu_title">
					<h3>회원정보</h3>
				</div>
				<div id="mp_wrap">
					<div id="user_info">
						<h5>${vo.member_nickname}님이 입력하신 회원정보입니다.</h5>
						<table id="user_info_table">
							
								<tr id="id_info">
									<th>아이디</th>
									<td>${vo.member_id}</td>
								</tr>
								<tr id="nickname_info">
									<th>닉네임</th>
									<td>${vo.member_nickname}</td>
								</tr>
								<tr id="phone_info">
									<th>전화번호</th>
									<td>${vo.member_phone}</td>
								</tr>
								<tr id="email_info">
									<th>이메일</th>
									<td>${vo.member_email}</td>
								</tr>
						</table>
	
						<div id="modify">
							<a href="member_pw_check.mp"><input type="button" id="modify_btn" value="회원정보수정" /></a> 
						</div>
					</div>
				</div><!-- mp_wrqp -->
			</div>
		</section> 
	</div><!-- mypage_wrap --> 
</div><!-- mypage --> 
</body>

 
<!-- footer 영역 -->
 <jsp:include page="/WEB-INF/views/include/footer.jsp"/> 