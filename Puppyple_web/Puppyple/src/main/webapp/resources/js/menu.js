$(document).ready(function(){
	
	// 메인메뉴 gnb에 li hover시 lnb 부드럽게 나타나기
	// 커뮤니티
	$('.community').mouseenter(function(){
		$('.lnb_menu1').stop(true).slideDown(350);
	});
	$('.community').mouseleave(function(){
		$('.lnb_menu1').stop(true).slideUp(350);
	});
	// 퍼피플 소식
	$('.notice').mouseenter(function(){
		$('.lnb_menu2').stop(true).slideDown(350);
	});
	$('.notice').mouseleave(function(){
		$('.lnb_menu2').stop(true).slideUp(350);
	});

});
