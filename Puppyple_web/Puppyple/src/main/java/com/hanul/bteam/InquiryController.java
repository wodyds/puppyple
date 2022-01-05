package com.hanul.bteam;

import java.io.File;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import common.CommonService;
import member.MemberVO;
import inquiry.InquiryCommentVO;
import inquiry.InquiryPage;
import inquiry.InquiryServiceImpl;
import inquiry.InquiryVO;

@Controller
public class InquiryController {

	@Autowired
	private InquiryServiceImpl service;
	@Autowired
	private InquiryPage page;
	@Autowired
	private CommonService common;

	// 댓글 변경 저장 처리 요청
	@ResponseBody
	@RequestMapping(value = "inquiry/comment/update", produces = "application/text; charset=utf-8")
	public String comment_update(@RequestBody InquiryCommentVO vo) {
		return service.inquiry_comment_update(vo) > 0 ? "댓글이 수정되었습니다" : "댓글 수정에 실패하였습니다";
	}

	// 댓글 삭제 처리 요청
	@ResponseBody
	@RequestMapping("/inquiry/comment/delete/{id}")
	public void comment_delete(@PathVariable int id) {
		service.inquiry_comment_delete(id);
	}

	// 1:1문의 글에 대한 댓글 목록조회 요청
	@RequestMapping("/inquiry/comment/list/{pid}")
	public String comment_list(@PathVariable int pid, Model model) { // 경로 형태로 들어온 값을 쓸수 있음
		// 해당 글에 대한 댓글들을 DB에서 조회한다
		model.addAttribute("list", service.inquiry_comment_list(pid));
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		return "inquiry/comment/comment_list";
	}

	// 1:1문의 글에 대한 댓글 저장처리 요청
	@ResponseBody
	@RequestMapping("/inquiry/comment/regist")
	public boolean comment_regist(InquiryCommentVO vo, HttpSession session) {
		// 작성자의 경우 member의 id 값을 담아야 하므로 로그인 정보 확인
		MemberVO member = (MemberVO) session.getAttribute("loginInfo");
		vo.setMember_id(member.getMember_id());

		// 화면에서 입력한 댓글 정보를 DB에 저장한 후 저장 여부를 반환

		return service.inquiry_comment_insert(vo) == 1 ? true : false;
		// 반환 결과가 1이면 true 아니면 false
	}

	// 1:1문의글 수정 저장 처리 요청
	@RequestMapping("/update.inq")
	public String update(InquiryVO vo, MultipartFile file, HttpSession session, String attach, Model model) {

		// 작성자의 경우 member의 id 값을 담아야 하므로 로그인 정보 확인
		MemberVO member = (MemberVO) session.getAttribute("loginInfo");

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", Integer.toString(vo.getId()));
		map.put("loginInfo_member_id", member.getMember_id());

		InquiryVO inquiry = service.inquiry_detail(map);
		String uuid = session.getServletContext().getRealPath("resources") + "/" + inquiry.getBoard_filepath();

		// 원래 파일이 첨부된 경우 이전 파일을 삭제하고 변경한 파일을 저장
		if (!file.isEmpty()) {
			// 원래 첨부 파일이 없었는데 수정시 첨부한 경우
			vo.setBoard_filename(file.getOriginalFilename());
			vo.setBoard_filepath(common.fileUpload(file, session));

			// 원래 첨부된 파일이 있었다면 물리적인 디스크에서 해당 파일 삭제
			// 서버에 파일이 있는지 파악
			if (inquiry.getBoard_filename() != null) {
				// 파일 정보를 File 형태의 f 에 할당
				File f = new File(uuid);
				// 기존 첨부 파일이 있다면 삭제
				if (f.exists())
					f.delete();
			}
		} else {
			// 파일을 첨부하지 않은 경우
			// 원래 첨부된 파일이 있었는데 삭제한 경우
			if (attach.isEmpty()) { // 첨부된 파일명이 없을 때
				if (inquiry.getBoard_filepath() != null) { // 원래 첨부된 파일이 있었다면
					File f = new File(uuid);
					if (f.exists())
						f.delete(); // 물리 디스크의 파일을 삭제
				}
			} else {
				// 원래 첨부된 파일을 그대로 사용하는 경우
				vo.setBoard_filename(inquiry.getBoard_filename());
				vo.setBoard_filepath(inquiry.getBoard_filepath());
			}
		}

		// 화면에서 변경 입력한 정보를 DB에 변경 저장한 후 상세화면으로 연결
		service.inquiry_update(vo);
		return "redirect:detail.inq?id=" + vo.getId();
	}

	// 1:1문의글 삭제처리 요청
	@RequestMapping("/delete.inq")
	public String delete(int id, HttpSession session, Model model) {

		// 작성자의 경우 member의 id 값을 담아야 하므로 로그인 정보 확인
		MemberVO member = (MemberVO) session.getAttribute("loginInfo");

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", Integer.toString(id));
		map.put("loginInfo_member_id", member.getMember_id());

		// 1:1문의글에 대한 모든 정보 조회
		InquiryVO inquiry = service.inquiry_detail(map);
		String uuid = session.getServletContext().getRealPath("resources") + "/" + inquiry.getBoard_filepath();

		// 파일명 또는 파일경로 있는지 판단 (없지 않다면)
		if (inquiry.getBoard_filename() != null) {
			// 디렉토리 또는에 대한 접근 권한을 가진 File 개체를 통해 파일 위치 할당
			File file = new File(uuid);
			// upload 폴더 내 file 이 존재한다면 삭제 처리
			if (file.exists())
				file.delete();
		}

		// 해당 1:1문의글 정보를 DB에서 삭제한 후 목록화면으로 연결
		service.inquiry_delete(id);
		model.addAttribute("uri", "list.inq");
		model.addAttribute("page", page);
		return "redirect:list.inq";
	}

	// 1:1문의글의 첨부 파일을 다운로드 요청
	@RequestMapping("/download.inq")
	public void download(int id, HttpSession session, HttpServletResponse response) {

		// 작성자의 경우 member의 id 값을 담아야 하므로 로그인 정보 확인
		MemberVO member = (MemberVO) session.getAttribute("loginInfo");

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", Integer.toString(id));
		map.put("loginInfo_member_id", member.getMember_id());
		// 해당 1:1문의글의 첨부파일 정보를 DB에서 조회해와 해당 파일을 서버로부터 다운로드 함.
		InquiryVO inquiry = service.inquiry_detail(map);
		common.fileDownload(inquiry.getBoard_filename(), inquiry.getBoard_filepath(), session, response);
		// 파일 업,다운로드 처리를 다른 게시판에서 처리하기 때문에 CommonService를 활용
	}

	// 1:1문의 상세화면 요청
	@RequestMapping("/detail.inq")
	public String detail(int id, Model model, HttpSession session) {

		// 작성자의 경우 member의 id 값을 담아야 하므로 로그인 정보 확인
		MemberVO member = (MemberVO) session.getAttribute("loginInfo");

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", Integer.toString(id));
		// map.put("loginInfo_member_id", member.getMember_id());

		if (member != null) {
			map.put("loginInfo_member_id", member.getMember_id());
		} else if (member == null) {
			map.put("loginInfo_member_id", "");
		}

		// 선택한(id) 1:1문의 정보를 DB에서 조회해 와 상세화면 출력
		model.addAttribute("vo", service.inquiry_detail(map));
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("page", page);

		return "inquiry/detail";
	}

	// 신규 1:1문의 저장 처리 요청
	@RequestMapping("/insert.inq")
	public String insert(InquiryVO vo, HttpSession session, MultipartFile file) {

//			MemberVO member = (MemberVO) session.getAttribute("loginInfo");
//			vo.setWriter(member.getId());

		// 로그인 된 사용자의 id를 가져와 글쓴이(writer)에 담기 위한 처리
		vo.setMember_id(((MemberVO) session.getAttribute("loginInfo")).getMember_id());

		if (!file.isEmpty()) {// 파일이 있는 경우
			// 파일 첨부 처리 부분
			vo.setBoard_filename(file.getOriginalFilename());
			vo.setBoard_filepath(common.fileUpload(file, session));
		}

		// 화면에서 입력한 정보를 DB에 저장한 후 화면으로 연결(출력)
		service.inquiry_insert(vo);

		return "redirect:list.inq"; // 리턴 시 1:1문의 목록 화면으로 이동 처리
	}

	// 1:1문의글 수정 화면 요청
	@RequestMapping("/modify.inq")
	public String modify(int id, Model model, HttpSession session) {
		// 작성자의 경우 member의 id 값을 담아야 하므로 로그인 정보 확인
		MemberVO member = (MemberVO) session.getAttribute("loginInfo");

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", Integer.toString(id));
		map.put("loginInfo_member_id", member.getMember_id());

		model.addAttribute("vo", service.inquiry_detail(map));
		return "inquiry/modify";
	}

	// 신규 1:1문의 입력 화면 요청
	@RequestMapping("/new.inq")
	public String inquiry() {
		return "inquiry/new";
	}

	// 1:1문의 리스트
	@RequestMapping("/list.inq")
	public String list(HttpSession session, String search, String keyword, String flag,
			@RequestParam(defaultValue = "10") int pageList, @RequestParam(defaultValue = "1") int curPage,
			Model model) {
		session.setAttribute("category", "inq");
		// 현재 페이지에 대한 정보를 담음.
		page.setCurPage(curPage);
		// 검색조건, 검색어 정보를 담음.
		page.setSearch(search);
		page.setKeyword(keyword);
		page.setPageList(pageList); // 페이지당 보여질 글 목록수
		page.setFlag(flag);

		// DB에서 1:1문의글 목록을 조회한 후 목록화면에 출력
		model.addAttribute("page", service.inquiry_list(page));

		return "inquiry/list";
	}

}
