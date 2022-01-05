package com.hanul.bteam;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.google.gson.Gson;

import common.CommonService;
import gallery.GalleryVO;
import member.MemberVO;
import pet.PetServiceImpl;
import pet.PetVO;

@Controller
public class PetController {

	@Autowired
	private PetServiceImpl service;
	@Autowired
	private CommonService common;

	@RequestMapping("/pet_info_modify.pet")
	public String pet_info_modify(int pet_id, PetVO vo, MultipartFile file, HttpSession session, HttpServletRequest req,
			HttpServletResponse res, Model model, String attach) {

//	      if(vo.getPet_nuetering().equals("")) {
//	         vo.setPet_nuetering("N");
//	      }

		PetVO pet = service.pet_web_petSelect(pet_id);

		String uuid = session.getServletContext().getRealPath("resources") + "/" + pet.getPet_filepath();
		// 1. 컨트롤러를 타는지 확인
		System.out.println("펫수정정보 저장 처리 컨트롤러로 들어왔습니다.");

		if (!file.isEmpty()) {
			// 원래 첨부 파일이 없었는데 수정시 첨부한 경우
			vo.setPet_filename(file.getOriginalFilename());
			vo.setPet_filepath(common.fileUpload(file, session));

			// 원래 첨부된 파일이 있었다면 물리적인 디스크에서 해당 파일 삭제
			// 서버에 파일이 있는지 파악
			if (pet.getPet_filename() != null) {
				// 파일 정보를 File 형태의 f 에 할당
				File f = new File(uuid);
				// 기존 첨부 파일이 있다면 삭제
				if (f.exists())
					f.delete();
			}
		} else {
			// 파일을 첨부하지 않은 경우
			// 원래 첨부된 파일이 있었는데 삭제한 경우
			if (!attach.isEmpty()) { // 첨부된 파일명이 없을 때
				if (pet.getPet_filepath() != null) { // 원래 첨부된 파일이 있었다면
					File f = new File(uuid);
					if (f.exists())
						f.delete(); // 물리 디스크의 파일을 삭제
				}
			} else {
				// 원래 첨부된 파일을 그대로 사용하는 경우
				vo.setPet_filename(pet.getPet_filename());
				vo.setPet_filepath(pet.getPet_filepath());
			}
		}

		System.out.println(vo.getPet_filename());
		System.out.println(vo.getPet_filepath());

		// 이정보들을 가지고 sevice를 타서 펫정보 update 처리가 가능하게 한다.
		service.pet_web_petUpdate(vo);

		return "redirect:pet_info.pet";
	}

	// 펫 수정화면페이지 전환
	// 펫 페이지 2-2(수정) 화면 요청
	@RequestMapping("/pet_info_modifypage.pet")
	public String pet_info_modifypage(int pet_id, PetVO vo, HttpSession session, HttpServletRequest req,
			HttpServletResponse res, Model model) {
		// 1. 일단 Web에서 컨트롤러로 넘어오는지 확인
		System.out.println("펫수정페이지화면요청");
		System.out.println(pet_id);

		// 넘겨준 pet_id를 가지고 펫정보를 vo로 담아줘서
		// service.pet_web_petSelect(pet_id);

		// model로 PetVO를 담아서 modify 페이지로 넘긴다.
		model.addAttribute("vo", service.pet_web_petSelect(pet_id));

		return "mypage/pet_info_modify";

	}

	// 펫정보 삭제기능
	@RequestMapping("/pet_info_delete.pet")
	public String pet_info_delete(int pet_id, HttpSession session, HttpServletRequest req, HttpServletResponse res,
			Model model) {

		// 1.Save the req parameter
		System.out.println(pet_id);

		service.pet_web_petDelete(pet_id);

		MemberVO member = (MemberVO) session.getAttribute("loginInfo");
		String member_id = member.getMember_id();

		model.addAttribute("list", service.pet_petList(member_id));
		return "mypage/pet_info";
	}

	// 펫 추가 정보 입력후 추가 클릭시 아래 매핑으로 와서 추가 된 후 pet_info.jsp로 넘어감
	@RequestMapping("/pet_info_add.pet")
	public String pet_info_add(PetVO vo, MultipartFile file, HttpSession session, HttpServletRequest req,
			HttpServletResponse res, Model model) {

		// 1. 일단 Web에서 컨트롤러로 넘어오는지 확인
		System.out.println("펫추가요청처리");

		// 2. PetVO에 넘어오는 값들 저장 및 session에 있는 loginInfo값들을 받아서 VO setting
		MemberVO member = (MemberVO) session.getAttribute("loginInfo");

		vo.setMember_id(member.getMember_id());
		vo.setMember_nickname(member.getMember_nickname());
		vo.setPet_name(req.getParameter("pet_name"));
		vo.setPet_age(req.getParameter("pet_age"));
		vo.setPet_breed(req.getParameter("pet_breed"));
		vo.setPet_weight(req.getParameter("pet_weight"));
		vo.setPet_gender(req.getParameter("pet_gender"));

		if (req.getParameter("pet_nuetering") == "Y") {
			vo.setPet_nuetering("Y");
		} else if (req.getParameter("pet_nuetering") == null) {
			vo.setPet_nuetering("N");
		}

		System.out.println(vo.getMember_id());
		System.out.println(vo.getMember_nickname());
		System.out.println(vo.getPet_name());
		System.out.println(vo.getPet_age());
		System.out.println(vo.getPet_breed());
		System.out.println(vo.getPet_weight());
		System.out.println(vo.getPet_gender());
		System.out.println(vo.getPet_nuetering());

		if (!file.isEmpty()) {// 파일이 있는 경우
			// 파일 첨부 처리 부분
			vo.setPet_filename(file.getOriginalFilename());
			vo.setPet_filepath(common.fileUpload(file, session));
		}

		System.out.println(vo.getPet_filename());
		System.out.println(vo.getPet_filepath());

		service.pet_web_petAdd(vo);

		String member_id = member.getMember_id();

		model.addAttribute("list", service.pet_petList(member_id));
		return "redirect:pet_info.pet";
	}

	//  펫추가버튼 클릭시 화면페이지요청
	@RequestMapping("/pet_info_new.pet")
	public String pet_info_new(HttpSession session, HttpServletRequest req) {

		// 1. 일단 Web에서 컨트롤러로 넘어오는지 확인
		System.out.println("펫추가페이지화면요청");

		return "mypage/pet_info_add";

	}

	// 펫 페이지 2-1 화면 요청
	@RequestMapping("/pet_info.pet")
	public String pet_info(HttpSession session, Model model) {

		session.setAttribute("category", "pet");
		String member_id = ((MemberVO) session.getAttribute("loginInfo")).getMember_id();

		model.addAttribute("list", service.pet_petList(member_id));
		return "mypage/pet_info";

	}

	////////////////////////////////////////////////////////////

	// 펫정보수정(첨부파일x)
	@ResponseBody
	@RequestMapping(value = "/and_petModify_Nofile", method = { RequestMethod.GET, RequestMethod.POST })
	public void and_petModify_Nofile(PetVO vo, HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_petModify_Nofile");
		System.out.println(req.getParameter("pet_id") + req.getParameter("member_id"));

		// 1. Save the req parameter
		vo.setPet_id(Integer.parseInt(req.getParameter("pet_id")));
		vo.setPet_name(req.getParameter("pet_name"));
		vo.setPet_age(req.getParameter("pet_age"));
		vo.setPet_breed(req.getParameter("pet_breed"));
		vo.setPet_weight(req.getParameter("pet_weight"));
		vo.setPet_gender(req.getParameter("pet_gender"));
		vo.setPet_nuetering(req.getParameter("pet_nuetering"));

		// 2. Check values

		System.out.println(vo.getPet_name() + " " + vo.getPet_age() + " " + vo.getPet_breed());

		int state = service.pet_and_petModify_Nofile(vo);

		common_petModify(res, state);
	}

	// 펫정보수정
	@ResponseBody
	@RequestMapping(value = "/and_petModify", method = { RequestMethod.GET, RequestMethod.POST })
	public void and_petModify(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_petModify");
		System.out.println(req.getParameter("pet_id") + req.getParameter("member_id"));

		PetVO vo = new PetVO();
		// 1. Save the req parameter
		vo.setPet_id(Integer.parseInt(req.getParameter("pet_id")));
		vo.setPet_name(req.getParameter("pet_name"));
		vo.setPet_age(req.getParameter("pet_age"));
		vo.setPet_breed(req.getParameter("pet_breed"));
		vo.setPet_weight(req.getParameter("pet_weight"));
		vo.setPet_gender(req.getParameter("pet_gender"));
		vo.setPet_nuetering(req.getParameter("pet_nuetering"));

		String pet_filename = "";

		MultipartRequest multi = (MultipartRequest) req;
		MultipartFile file = multi.getFile("pet_filepath");

		if (file != null) {
			pet_filename = file.getOriginalFilename();
			System.out.println("fileName: " + pet_filename);

			if (file.getSize() > 0) {
				String realImgPath = req.getSession().getServletContext().getRealPath("/resources/upload");

				System.out.println("realpath: " + realImgPath);
				System.out.println("filesize: " + file.getSize());

				// 이미지 파일을 서버에 서장
				try {
					file.transferTo(new File(realImgPath, pet_filename));
				} catch (Exception e) {
					e.getMessage();
				}
			}
			vo.setPet_filepath("upload/" + pet_filename);
		}

		vo.setPet_filename(pet_filename);

		// 2. Check values

		System.out.println(vo.getPet_name() + " " + vo.getPet_age() + " " + vo.getPet_breed());

		int state = service.pet_and_petModify(vo);

		common_petModify(res, state);
	}

	// 펫정보리스트뷰처리
	@ResponseBody
	@RequestMapping(value = "/and_petDelete", method = { RequestMethod.GET, RequestMethod.POST })
	public void and_petDelete(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_petDelete");

		// 1.Save the req parameter
		int pet_id = Integer.parseInt(req.getParameter("pet_id"));
		String member_id = req.getParameter("member_id");

		service.pet_and_petDelete(pet_id);
	}

	// 펫정보리스트뷰처리
	@ResponseBody
	@RequestMapping(value = "/and_petList", method = { RequestMethod.GET, RequestMethod.POST })
	public void and_petList(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_petList");

		// 1.Save the req parameter
		String member_id = req.getParameter("member_id");

		List<PetVO> petList = new ArrayList<PetVO>();

		System.out.println("Before service_petListSize: " + petList.size());

		petList = service.pet_and_petList(member_id);

		System.out.println("After service_petListSize: " + petList.size());

		common_petList(res, petList);
	}

	// 펫 정보 등록
	@ResponseBody
	@RequestMapping(value = "/and_petAdd", method = { RequestMethod.GET, RequestMethod.POST })
	public void and_petAdd(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_petAdd");
		System.out.println(req.getParameter("pet_name"));
		PetVO vo = new PetVO();
		// 1. Save the req parameter
		vo.setMember_id(req.getParameter("member_id"));
		vo.setPet_name(req.getParameter("pet_name"));
		vo.setPet_age(req.getParameter("pet_age"));
		vo.setPet_breed(req.getParameter("pet_breed"));
		vo.setPet_weight(req.getParameter("pet_weight"));
		vo.setPet_gender(req.getParameter("pet_gender"));
		vo.setPet_nuetering(req.getParameter("pet_nuetering"));

		String pet_filename = "";
		// String realImgPath = "";

		MultipartRequest multi = (MultipartRequest) req;
		MultipartFile file = multi.getFile("pet_filepath");

		if (file != null) {
			pet_filename = file.getOriginalFilename();
			System.out.println("fileName: " + pet_filename);

			if (file.getSize() > 0) {
				String realImgPath = req.getSession().getServletContext().getRealPath("/resources/upload/");

				System.out.println("realpath: " + realImgPath);
				System.out.println("filesize: " + file.getSize());

				// 이미지 파일을 서버에 서장
				try {
					file.transferTo(new File(realImgPath, pet_filename));
				} catch (Exception e) {
					e.getMessage();
				}
				vo.setPet_filepath("upload/" + pet_filename);
			}
		}

		vo.setPet_filename(pet_filename);

		// 2. Check values

		System.out.println(vo.getPet_name() + " " + vo.getPet_age() + " " + vo.getPet_breed());

		int state = service.pet_and_petAdd(vo);

		common_petAdd(res, state);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	// 펫 삭제 후 안드로이드 펫정보확인 ArrayList처리
	public void common_petDeleteList(HttpServletResponse res, List<PetVO> petList) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(petList);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}

	// 안드로이드 펫정보확인 ArrayList처리
	public void common_petList(HttpServletResponse res, List<PetVO> petList) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(petList);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}

	// 펫 정보 등록
	public void common_petAdd(HttpServletResponse res, int state) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(state);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}

	// 펫 정보 등록
	public void common_petModify(HttpServletResponse res, int state) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(state);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}

}// class PetController
