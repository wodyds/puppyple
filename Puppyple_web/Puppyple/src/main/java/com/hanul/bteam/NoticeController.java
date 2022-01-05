package com.hanul.bteam;

import java.io.File;

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

import common.CommonService;
import member.MemberVO;
import notice.NoticePage;
import notice.NoticeServiceImpl;
import notice.NoticeVO;

@Controller
public class NoticeController {

	@Autowired
	private NoticeServiceImpl service;
	@Autowired
	private NoticePage page;
	@Autowired
	private CommonService common;

	// 공지글 수정 저장 처리 요청
	@RequestMapping("/update.no")
	public String update(NoticeVO vo, MultipartFile file, HttpSession session, String attach) {

		NoticeVO notice = service.notice_detail(vo.getId());
		String uuid = session.getServletContext().getRealPath("resources") + "/" + notice.getBoard_filepath();

		// 원래 파일이 첨부된 경우 이전 파일을 삭제하고 변경한 파일을 저장
		if (!file.isEmpty()) {
			// 원래 첨부 파일이 없었는데 수정시 첨부한 경우
			vo.setBoard_filename(file.getOriginalFilename());
			vo.setBoard_filepath(common.fileUpload(file, session));

			// 원래 첨부된 파일이 있었다면 물리적인 디스크에서 해당 파일 삭제
			// 서버에 파일이 있는지 파악
			if (notice.getBoard_filename() != null) {
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
				if (notice.getBoard_filepath() != null) { // 원래 첨부된 파일이 있었다면
					File f = new File(uuid);
					if (f.exists())
						f.delete(); // 물리 디스크의 파일을 삭제
				}
			} else {
				// 원래 첨부된 파일을 그대로 사용하는 경우
				vo.setBoard_filename(notice.getBoard_filename());
				vo.setBoard_filepath(notice.getBoard_filepath());
			}
		}

		// 화면에서 변경 입력한 정보를 DB에 변경 저장한 후 상세화면으로 연결
		service.notice_update(vo);
		return "redirect:detail.no?id=" + vo.getId();
	}

	// 공지글 삭제처리 요청
	@RequestMapping("/delete.no")
	public String delete(int id, HttpSession session) {

		// 공지글에 대한 모든 정보 조회
		NoticeVO notice = service.notice_detail(id);
		String uuid = session.getServletContext().getRealPath("resources") + "/" + notice.getBoard_filepath();

		// 파일명 또는 파일경로 있는지 판단 (없지 않다면)
		if (notice.getBoard_filename() != null) {
			// 디렉토리 또는에 대한 접근 권한을 가진 File 개체를 통해 파일 위치 할당
			File file = new File(uuid);
			// upload 폴더 내 file 이 존재한다면 삭제 처리
			if (file.exists())
				file.delete();
		}

		// 해당 공지글 정보를 DB에서 삭제한 후 목록화면으로 연결
		service.notice_delete(id);
		return "redirect:list.no";
	}

	// 공지글의 첨부 파일을 다운로드 요청
	@RequestMapping("/download.no")
	public void download(int id, HttpSession session, HttpServletResponse response) {
		// 해당 공지글의 첨부파일 정보를 DB에서 조회해와 해당 파일을 서버로부터 다운로드 함.
		NoticeVO notice = service.notice_detail(id);
		common.fileDownload(notice.getBoard_filename(), notice.getBoard_filepath(), session, response);
		// 파일 업,다운로드 처리를 다른 게시판에서 처리하기 때문에 CommonService를 활용
	}

	// 공지사항 상세화면 요청
	@RequestMapping("/detail.no")
	public String detail(int id, Model model) {

		// 상세화면 요청 전 조회수 증가
		service.notice_read(id);

		// 선택한(id) 공지사항 정보를 DB에서 조회해 와 상세화면 출력
		model.addAttribute("vo", service.notice_detail(id));
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("page", page);

		return "notice/detail";
	}

	// 신규 공지사항 저장 처리 요청
	@RequestMapping("/insert.no")
	public String insert(NoticeVO vo, HttpSession session, MultipartFile file) {

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
		service.notice_insert(vo);

		return "redirect:list.no"; // 리턴 시 공지사항 목록 화면으로 이동 처리
	}

	// 공지글 수정 화면 요청
	@RequestMapping("/modify.no")
	public String modify(int id, Model model) {
		model.addAttribute("vo", service.notice_detail(id));
		return "notice/modify";
	}

	// 신규 공지사항 입력 화면 요청
	@RequestMapping("/new.no")
	public String notice() {
		return "notice/new";
	}

	// 공지사항 리스트
	@RequestMapping("/list.no")
	public String list(HttpSession session, String search, String keyword, String flag,
			@RequestParam(defaultValue = "10") int pageList, @RequestParam(defaultValue = "1") int curPage,
			Model model) {

		session.setAttribute("category", "no");

		// 현재 페이지에 대한 정보를 담음.
		page.setCurPage(curPage);
//					검색조건, 검색어 정보를 담음.
		page.setSearch(search);
		page.setKeyword(keyword);
		page.setPageList(pageList); // 페이지당 보여질 글 목록수
		page.setFlag(flag);
//					
		// DB에서 공지글 목록을 조회한 후 목록화면에 출력
		model.addAttribute("page", service.notice_list(page));

		return "notice/list";
	}

}
