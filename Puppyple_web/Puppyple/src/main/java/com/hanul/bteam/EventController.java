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
import event.EventPage;
import event.EventServiceImpl;
import event.EventVO;

@Controller
public class EventController {

	@Autowired 
	private EventServiceImpl service;
	@Autowired
	private EventPage page;

	// 그리드 형식
	@RequestMapping("/event_detail1.ev")
	public String event1() {
		return "event/event_detail1";
	}

	// 그리드 형식
	@RequestMapping("/event_detail2.ev")
	public String event2() {
		return "event/event_detail2";
	}

	// 이벤트 리스트
	@RequestMapping("/list.ev")
	public String list(HttpSession session, Model model, @RequestParam(defaultValue = "1") int curPage, String search,
			String keyword) {

		session.setAttribute("category", "ev");
		// DB에서 이벤트글 목록을 조회해와 목록화면에 출력
//			model.addAttribute("list", service.event_list());

		// 현재 페이지에 대한 정보를 담음.
		page.setCurPage(curPage);
//			검색조건, 검색어 정보를 담음.
		page.setSearch(search);
		page.setKeyword(keyword);
//			
		// DB에서 이벤트글 목록을 조회한 후 목록화면에 출력
		model.addAttribute("page", service.event_list(page));

		return "event/list";
	}

}
