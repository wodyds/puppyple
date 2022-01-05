package com.hanul.bteam;

import java.io.File;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import gallery.GalleryPage;
import member.MemberVO;
import mypage.MypageVO;
import mypage.MypagePage;
import mypage.MypagePostPage;
import mypage.MypageServiceImpl;

@Controller
public class MypageController {

	@Autowired
	private MypageServiceImpl service;
	@Autowired
	private MypagePostPage page;
	@Autowired
	private MypagePage admin_page;

	/* 관리자용 */

	// 퍼피플 정보 화면 요청
	@RequestMapping("/admin_current.mp")
	public String admin_current(Model model) {
		model.addAttribute("page", service.mypage_admin_list(admin_page));
		return "mypage/admin_current";

	}

	// 관리자 펫정보 삭제기능
	@RequestMapping("/admin_pet_info_delete.mp")
	public String pet_info_delete(int pet_id, HttpSession session, HttpServletRequest req, HttpServletResponse res,
			Model model) {

		service.admin_pet_web_petDelete(pet_id);

		MemberVO member = (MemberVO) session.getAttribute("loginInfo");
		String member_id = member.getMember_id();

		model.addAttribute("list", service.admin_pet_petList(member_id));
		return "redirect:admin_list.mp";
	}

	// 관리자 회원정보 삭제처리 요청
	@RequestMapping("/admin_delete.mp")
	public String admin_delete(String member_id, HttpSession session) {

		// 해당 회원 정보를 DB에서 삭제한 후 목록화면으로 연결
		service.member_info_delete(member_id);
		return "redirect:admin_list.mp";
	}

	// 관리자 회원정보 상세화면 요청
	@RequestMapping("/admin_detail.mp")
	public String admin_detail(HttpSession session, Model model, String member_id) {

		// 해당 회원정보를 DB에서 조회해와 상세화면에 출력
		model.addAttribute("vo", service.admin_detail(member_id));
		model.addAttribute("list", service.admin_pet_petList(member_id));

		return "mypage/admin_detail";
	}

	// 회원관리 리스트
	@RequestMapping("/admin_list.mp")
	public String admin_list(HttpSession session, String search, String keyword,
			@RequestParam(defaultValue = "10") int pageList, @RequestParam(defaultValue = "1") int curPage,
			Model model) {

		// 현재 페이지에 대한 정보를 담음.
		admin_page.setCurPage(curPage);
		// 검색조건, 검색어 정보를 담음.
		admin_page.setSearch(search);
		admin_page.setKeyword(keyword);
		admin_page.setPageList(pageList); // 페이지당 보여질 글 목록수

		// DB에서 공지글 목록을 조회한 후 목록화면에 출력
		model.addAttribute("page", service.mypage_admin_list(admin_page));

		return "mypage/admin_list";

	}

	/* 여기까지 관리자용 */

	// Mypage 내가 좋아요한 글 보기
	@RequestMapping("mypage_favorites.mp")
	public String mypage_favorites(HttpSession session, String search, String keyword, String flag,
			@RequestParam(defaultValue = "10") int pageList, @RequestParam(defaultValue = "1") int curPage,
			Model model) {

		System.out.println("내가 좋아요한 글 보기 페이지");

		MemberVO member = (MemberVO) session.getAttribute("loginInfo");
		String member_id = member.getMember_id();

		page.setMember_id(member_id);
		page.setCurPage(curPage);
		page.setPageList(pageList);

		model.addAttribute("page", service.mypage_favorites(page));

		return "mypage/mypage_favorites";
	}

	// Mypage 내가 쓴글 보기
	@RequestMapping("mypage_post.mp")
	public String mypage_post(HttpSession session, String search, String keyword, String flag,
			@RequestParam(defaultValue = "10") int pageList, @RequestParam(defaultValue = "1") int curPage,
			Model model) {
		// 1. include/mypage_side에서 클릭시 MypageController를 타는지를 확인
		// 및 리턴페이지로 넘어가는지 확인
		System.out.println("내가 쓴글 보기 페이지");
		// 문제없음

		MemberVO member = (MemberVO) session.getAttribute("loginInfo");
		String member_id = member.getMember_id();

		// 아이디넣어줘야함
		// 다른 page 처리와 다르게 MypagePostPage에는 member_id getter&setter 따로 만듬

		// session에 있는 로그인된 아이디를 담음
		page.setMember_id(member_id);
		// 현재 페이지에 대한 정보를 담음.
		page.setCurPage(curPage);
		// 페이지당 보여질 글 목록수
		page.setPageList(pageList);

		// 2. 내가 쓴 글들 관련 service 및 sql 기능 구현
		// DB에서 내가 쓴글 목록을 조회한 후 목록화면에 출력
		model.addAttribute("page", service.mypage_post(page));

		return "mypage/mypage_post";
	}

	// 회원정보 수정처리
	@RequestMapping(value = "/member_modify_ok.mp", produces = "text/html; charset=utf-8")
	public String member_info_modify(MemberVO vo, HttpServletRequest req, HttpSession session) {
		vo.setMember_pw(req.getParameter("member_pw"));
		vo.setMember_nickname(req.getParameter("member_nickname"));
		vo.setMember_phone(req.getParameter("member_phone"));
		vo.setMember_email(req.getParameter("member_email"));
		vo.setMember_id(req.getParameter("member_id"));

		service.web_member_modify(vo);
		session.removeAttribute("loginInfo");
		return "redirect:/";
	}

	// 회원정보 삭제처리 요청
	@RequestMapping("/member_delete.mp")
	public String member_info_delete(String member_id, HttpSession session) {

		// 해당 공지글 정보를 DB에서 삭제한 후 목록화면으로 연결
		service.member_info_delete(member_id);
		session.removeAttribute("loginInfo");
		return "redirect:/";
	}

	// 회원 정보수정 화면 요청
	@RequestMapping("/member_modify.mp")
	public String member_modify(String member_id, Model model) {
		model.addAttribute("vo", service.member_info(member_id));
		return "mypage/member_modify";

	}

	// 비밀번호 확인 처리 요청(회원탈퇴)
	@ResponseBody
	@RequestMapping("/member_leave_check_pw")
	public MemberVO modify_leave_check(MemberVO vo, HttpSession session, String member_pw) {
		MemberVO member = (MemberVO) session.getAttribute("loginInfo");
		vo.setMember_id(member.getMember_id());

		String pwCh = member.getMember_pw();
		boolean check;

		if (member_pw.equals(pwCh)) {
			return member;

		} else {
			return null;
		}

	}

	// 비밀번호 확인 처리 요청
	@ResponseBody
	@RequestMapping("/member_pw_check_pw")
	public boolean modify_check(MemberVO vo, HttpSession session, String member_pw) {
		MemberVO member = (MemberVO) session.getAttribute("loginInfo");
		vo.setMember_id(member.getMember_id());

		String pwCh = member.getMember_pw();
		boolean check;

		if (member_pw.equals(pwCh)) {
			return true;

		} else {
			return false;
		}
	}

	// 정보수정 전 비밀번호 확인(회원탈퇴)
	@RequestMapping("/member_leave_check.mp")
	public String modify_leave_check() {
		return "mypage/member_leave_check";
	}

	// 정보수정 전 비밀번호 확인
	@RequestMapping("/member_pw_check.mp")
	public String modify_leave() {
		return "mypage/member_pw_check";
	}

	// 마이페이지 화면 요청
	@RequestMapping("/member_info.mp")
	public String member_info(HttpSession session, Model model, String member_id) {
		// title 태그에 사용 할 수 있도록 지정(session)
		session.setAttribute("category", "mp");
//			model.addAttribute("vo", service.mypage());
		model.addAttribute("vo", service.member_info(member_id));

		return "mypage/member_info";

	}

}
