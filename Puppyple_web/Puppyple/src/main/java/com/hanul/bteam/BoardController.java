package com.hanul.bteam;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.google.gson.Gson;

import board.BoardCommentVO;
import board.BoardServiceImpl;
import board.BoardVO;
import pet.PetVO;

@Controller
public class BoardController {
	@Autowired private BoardServiceImpl service;
	
	//게시판 댓글삭제 처리
	@ResponseBody
	@RequestMapping(value="/and_board_comment_Delete", method= {RequestMethod.GET, RequestMethod.POST})
	public void and_board_comment_Delete (int id, HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_board_comment_Delete");
		System.out.println(req.getParameter("id"));
		id = Integer.parseInt(req.getParameter("id"));
		
		int state = service.and_board_comment_Delete(id);
		System.out.println("state : "+state);
		
		common_and_board_Delete(res,state);
	}
	
	//게시판 댓글조회 처리
		@ResponseBody
		@RequestMapping(value = "/and_BoardComment_List", method = {RequestMethod.GET, RequestMethod.POST})
		public void and_BoardComment_List(int pid,HttpServletRequest req, HttpServletResponse res) throws Exception {
			System.out.println("and_BoardComment_List");

			pid = Integer.parseInt(req.getParameter("pid"));
			List<BoardCommentVO> boardList = new ArrayList<BoardCommentVO>();
			
			boardList = service.BoardComment_List(pid);
			
			System.out.println("service_boardListSize: " + boardList.size());
			
			common_boardCommentList(res, boardList);
		}
	
	// 게시판 댓글 등록
		@ResponseBody
		@RequestMapping(value="/and_board_CommentAdd", method= {RequestMethod.GET, RequestMethod.POST})
		public void and_board_CommentAdd (BoardCommentVO vo, HttpServletRequest req, HttpServletResponse res) throws Exception {
			System.out.println("and_board_CommentAdd");
			System.out.println(req.getParameter("member_id"));
			
			// 1. 파라미터 저장
			vo.setFlag(req.getParameter("flag"));
			vo.setPid(Integer.parseInt(req.getParameter("pid")));
			vo.setMember_id(req.getParameter("member_id"));
			vo.setContent(req.getParameter("content"));
			
			// 2. Check values
			
			int state = service.board_CommentAdd(vo);
			System.out.println("state : "+state);
			common_board_Add_Nofile(res,state);
			
		}
	
	// 게시판 첨부파일 삭제 처리
		
		@ResponseBody
		@RequestMapping(value="/and_board_Modify_Deletefile", method= {RequestMethod.GET, RequestMethod.POST})
		public void and_board_Modify_Deletefile (HttpServletRequest req, HttpServletResponse res) throws Exception {
			System.out.println("and_board_Modify_Deletefile");
			System.out.println(req.getParameter("member_id"));
			
			// 1. 파라미터 저장
			int id = Integer.parseInt(req.getParameter("id"));
			
			
			// 2. Check values
			
			int state = service.board_Modify_Deletefile(id);
			System.out.println("state : "+state);
			common_board_Modify_Nofile(res,state);
			
		}
		
		//게시판 조회수 증가 처리
			@ResponseBody
			@RequestMapping(value="/and_update_boardCount", method= {RequestMethod.GET, RequestMethod.POST})
			public void and_update_boardCount (HttpServletRequest req, HttpServletResponse res) throws Exception {
				System.out.println("and_update_boardCount");
				System.out.println(req.getParameter("member_id"));
				
				// 1. 파라미터 저장
				int id = Integer.parseInt(req.getParameter("id"));
				
				// 2. Check values
				
				int state = service.and_update_boardCount(id);
				System.out.println("state : "+state);
				common_board_Modify_Nofile(res,state);
				
			}
		
	//게시판 수정 처리 (첨부파일x)
		@ResponseBody
		@RequestMapping(value="/and_board_Modify_Nofile", method= {RequestMethod.GET, RequestMethod.POST})
		public void and_board_Modify_Nofile (BoardVO vo, HttpServletRequest req, HttpServletResponse res) throws Exception {
			System.out.println("and_board_Modify_Nofile");
			System.out.println(req.getParameter("member_id"));
			
			// 1. 파라미터 저장
			vo.setId(Integer.parseInt(req.getParameter("id")));
			vo.setFlag(req.getParameter("flag"));
			vo.setMember_id(req.getParameter("member_id"));
			vo.setBoard_title(req.getParameter("board_title"));
			vo.setBoard_content(req.getParameter("board_content"));
			
			// 2. Check values
			
			int state = service.board_Modify_Nofile(vo);
			System.out.println("state : "+state);
			common_board_Modify_Nofile(res,state);
			
		}
	
	//게시판 수정 처리
		@ResponseBody
		@RequestMapping(value="/and_board_Modify", method= {RequestMethod.GET, RequestMethod.POST})
		public void and_board_Modify (HttpServletRequest req, HttpServletResponse res) throws Exception {
			System.out.println("and_board_Modify");
			System.out.println(req.getParameter("member_id"));
			BoardVO vo = new BoardVO();
			// 1. 파라미터 저장
			vo.setId(Integer.parseInt(req.getParameter("id")));
			vo.setFlag(req.getParameter("flag"));
			vo.setMember_id(req.getParameter("member_id"));
			vo.setBoard_title(req.getParameter("board_title"));
			vo.setBoard_content(req.getParameter("board_content"));
			
			String board_filename = "";
			MultipartRequest multi = (MultipartRequest)req;
			MultipartFile file = multi.getFile("board_filepath");
			
			if(file != null) {
				board_filename = file.getOriginalFilename();
				System.out.println("filename : "+board_filename);
				
				if(file.getSize() > 0) {
					String realImgPath = req.getSession().getServletContext().getRealPath("/resources/upload/");
					System.out.println("realpath : "+realImgPath);
					System.out.println("filesize : "+file.getSize());
					
					// 이미지 파일을 서버에 저장
					try {
						file.transferTo(new File(realImgPath,board_filename));
					} catch (Exception e) {
						e.getMessage();
					}
					vo.setBoard_filepath("upload/"+board_filename);
				}
			}
			
			vo.setBoard_filename(board_filename);
			
			// 2. Check values
			
			int state = service.board_Modify(vo);
			System.out.println("state : "+state);
			common_board_Modify(res,state);
			
		}
	
	// 게시판 삭제 처리
	@ResponseBody
	@RequestMapping(value="/and_board_Delete", method= {RequestMethod.GET, RequestMethod.POST})
	public void and_board_Delete (int id, HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_board_Delete");
		System.out.println(req.getParameter("id"));
		id = Integer.parseInt(req.getParameter("id"));
		
		int state = service.and_board_Delete(id);
		System.out.println("state : "+state);
		
		common_and_board_Delete(res,state);
	}
	
	//게시판 신규 등록 처리 (첨부파일x)
		@ResponseBody
		@RequestMapping(value="/and_board_Add_Nofile", method= {RequestMethod.GET, RequestMethod.POST})
		public void and_board_Add_Nofile (BoardVO vo, HttpServletRequest req, HttpServletResponse res) throws Exception {
			System.out.println("and_board_Add_Nofile");
			System.out.println(req.getParameter("member_id"));
			
			// 1. 파라미터 저장
			vo.setFlag(req.getParameter("flag"));
			vo.setMember_id(req.getParameter("member_id"));
			vo.setBoard_title(req.getParameter("board_title"));
			vo.setBoard_content(req.getParameter("board_content"));
			
			// 2. Check values
			
			int state = service.board_add_Nofile(vo);
			System.out.println("state : "+state);
			common_board_Add_Nofile(res,state);
			
		}
	
	//게시판 신규 등록 처리
	@ResponseBody
	@RequestMapping(value="/and_board_Add", method= {RequestMethod.GET, RequestMethod.POST})
	public void and_board_add (HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_board_Add");
		System.out.println(req.getParameter("member_id"));
		BoardVO vo = new BoardVO(); 
		// 1. 파라미터 저장
		vo.setFlag(req.getParameter("flag"));
		vo.setMember_id(req.getParameter("member_id"));
		vo.setBoard_title(req.getParameter("board_title"));
		vo.setBoard_content(req.getParameter("board_content"));
		
		String board_filename = "";
		MultipartRequest multi = (MultipartRequest)req;
		MultipartFile file = multi.getFile("board_filepath");
		
		if(file != null) {
			board_filename = file.getOriginalFilename();
			System.out.println("filename : "+board_filename);
			
			if(file.getSize() > 0) {
				String realImgPath = req.getSession().getServletContext().getRealPath("/resources/upload/");
				System.out.println("realpath : "+realImgPath);
				System.out.println("filesize : "+file.getSize());
				
				// 이미지 파일을 서버에 저장
				try {
					file.transferTo(new File(realImgPath,board_filename));
				} catch (Exception e) {
					e.getMessage();
				}
				vo.setBoard_filepath("upload/"+board_filename);
			}
		}
		
		vo.setBoard_filename(board_filename);
		
		// 2. Check values
		
		int state = service.board_add(vo);
		System.out.println("state : "+state);
		common_board_Add(res,state);
		
	}
	
	//내가 쓴 1:1 문의 글 조회
		@ResponseBody
		@RequestMapping(value = "/and_BoardList_Inquiry_admin", method = {RequestMethod.GET, RequestMethod.POST})
		public void and_BoardList_Inquiry_admin(HttpServletRequest req, HttpServletResponse res) throws Exception {
			
			System.out.println("and_BoardList_Inquiry_admin");

			List<BoardVO> boardList = new ArrayList<BoardVO>();
			
			System.out.println("Before service_boardListSize: " + boardList.size());
			
			boardList = service.and_BoardList_Inquiry_Admin();
			
			System.out.println("After service_boardListSize: " + boardList.size());
			
			common_boardList(res, boardList);
		}	
	
	//내가 쓴 1:1 문의 글 조회
		@ResponseBody
		@RequestMapping(value = "/and_BoardList_Inquiry", method = {RequestMethod.GET, RequestMethod.POST})
		public void and_BoardList_Inquiry(String member_id,HttpServletRequest req, HttpServletResponse res) throws Exception {
			
			System.out.println("and_BoardList_Inquiry");
			member_id = req.getParameter("member_id");

			List<BoardVO> boardList = new ArrayList<BoardVO>();
			
			System.out.println("Before service_boardListSize: " + boardList.size());
			
			boardList = service.and_BoardList_Inquiry(member_id);
			
			System.out.println("After service_boardListSize: " + boardList.size());
			
			common_boardList(res, boardList);
		}	
	
	//게시판 상세 조회 처리
		@ResponseBody
		@RequestMapping(value = "/and_Board_detail", method = {RequestMethod.GET, RequestMethod.POST})
		public void and_board_detail(HttpServletRequest req, HttpServletResponse res) throws Exception {
			System.out.println("and_Board_detail");
			
			//1.Save the req parameter
			int id = Integer.parseInt(req.getParameter("id"));

			List<BoardVO> boardList = new ArrayList<BoardVO>();
			
			boardList = service.board_detail(id);
			
			
			common_boardDetail(res, boardList);
		}

	//FAQ 조회 처리
	@ResponseBody
	@RequestMapping(value = "/and_BoardList_Event", method = {RequestMethod.GET, RequestMethod.POST})
	public void and_BoardList_Event(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_BoardList_Event");

		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		System.out.println("Before service_boardListSize: " + boardList.size());
		
		boardList = service.board_list_Event();
		
		System.out.println("After service_boardListSize: " + boardList.size());
		
		common_boardList(res, boardList);
	}

	//내가 쓴 글 조회 처리
	@ResponseBody
	@RequestMapping(value = "/and_BoardList_My", method = {RequestMethod.GET, RequestMethod.POST})
	public void and_BoardList_My(String member_id,HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		System.out.println("and_BoardList_My");
		member_id = req.getParameter("member_id");
		
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		System.out.println("Before service_boardListSize: " + boardList.size());
		
		boardList = service.board_list_My(member_id);
		
		System.out.println("After service_boardListSize: " + boardList.size());
		
		common_boardList(res, boardList);
	}	
		
	//FAQ 조회 처리
	@ResponseBody
	@RequestMapping(value = "/and_BoardList_FAQ", method = {RequestMethod.GET, RequestMethod.POST})
	public void and_BoardList_FAQ(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_BoardList_FAQ");

		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		System.out.println("Before service_boardListSize: " + boardList.size());
		
		boardList = service.board_list_FAQ();
		
		System.out.println("After service_boardListSize: " + boardList.size());
		
		common_boardList(res, boardList);
	}
		
	//공지글 조회 처리
	@ResponseBody
	@RequestMapping(value = "/and_BoardList_Notice", method = {RequestMethod.GET, RequestMethod.POST})
	public void and_BoardList_Notice(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_BoardList_Notice");

		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		System.out.println("Before service_boardListSize: " + boardList.size());
		
		boardList = service.board_list_Notice();
		
		System.out.println("After service_boardListSize: " + boardList.size());
		
		common_boardList(res, boardList);
	}
	
	//중고거래 조회 처리
	@ResponseBody
	@RequestMapping(value = "/and_BoardList_Trade", method = {RequestMethod.GET, RequestMethod.POST})
	public void and_BoardList_Trade(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_BoardList_Trade");
		
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		System.out.println("Before service_boardListSize: " + boardList.size());
		
		boardList = service.board_list_Trade();
		
		System.out.println("After service_boardListSize: " + boardList.size());
		
		common_boardList(res, boardList);
	}
	
	//갤러리 조회 처리
	@ResponseBody
	@RequestMapping(value = "/and_BoardList_Gallery", method = {RequestMethod.GET, RequestMethod.POST})
	public void and_BoardList_Gallery(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_BoardList_Gallery");
		
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		System.out.println("Before service_boardListSize: " + boardList.size());
		
		boardList = service.board_list_Gallery();
		
		System.out.println("After service_boardListSize: " + boardList.size());
		
		common_boardList(res, boardList);
	}
		
	//정보게시판 조회 처리
	@ResponseBody
	@RequestMapping(value = "/and_BoardList_Info", method = {RequestMethod.GET, RequestMethod.POST})
	public void and_BoardList_Info(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_BoardList_Info");
		
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		System.out.println("Before service_boardListSize: " + boardList.size());
		
		boardList = service.board_list_Info();
		
		System.out.println("After service_boardListSize: " + boardList.size());
		
		common_boardList(res, boardList);
	}	
		
	//자유게시판 조회 처리
	@ResponseBody
	@RequestMapping(value = "/and_BoardList_Free", method = {RequestMethod.GET, RequestMethod.POST})
	public void and_BoardList_Free(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_BoardList_Free");
		
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		System.out.println("Before service_boardListSize: " + boardList.size());
		
		boardList = service.board_list_free();
		
		System.out.println("After service_boardListSize: " + boardList.size());
		
		common_boardList(res, boardList);
	}
	
	//메인화면 전체게시판 조회 처리
		@ResponseBody
		@RequestMapping(value = "/and_MainBoardList_All", method = {RequestMethod.GET, RequestMethod.POST})
		public void and_MainboardList(HttpServletRequest req, HttpServletResponse res) throws Exception {
			System.out.println("and_MainBoardList_All");
			
			
			List<BoardVO> boardList = new ArrayList<BoardVO>();
			
			System.out.println("Before service_boardListSize: " + boardList.size());
			
			boardList = service.Main_board_list();
			
			System.out.println("After service_boardListSize: " + boardList.size());
			
			common_boardList(res, boardList);
		}
		
	//메인화면 공지사항 조회 처리
			@ResponseBody
			@RequestMapping(value = "/and_MainBoardList_Notice", method = {RequestMethod.GET, RequestMethod.POST})
			public void and_MainBoardList_Notice(HttpServletRequest req, HttpServletResponse res) throws Exception {
				System.out.println("and_MainBoardList_Notice");
	
				
				List<BoardVO> boardList = new ArrayList<BoardVO>();
				
				System.out.println("Before service_boardListSize: " + boardList.size());
				
				boardList = service.Main_Notice_list();
				
				System.out.println("After service_boardListSize: " + boardList.size());
				
				common_boardList(res, boardList);
			}
			
	
	//전체게시판 조회 처리
	@ResponseBody
	@RequestMapping(value = "/and_BoardList_All", method = {RequestMethod.GET, RequestMethod.POST})
	public void and_boardList(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_BoardList_All");
		
		List<BoardVO> boardList = new ArrayList<BoardVO>();
		
		System.out.println("Before service_boardListSize: " + boardList.size());
		
		boardList = service.board_list();
		
		System.out.println("After service_boardListSize: " + boardList.size());
		
		common_boardList(res, boardList);
	}
	
	//안드로이드 게시판 정보확인 ArrayList처리
	public void common_boardCommentList (HttpServletResponse res, List<BoardCommentVO> boardList) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(boardList);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}
	
	// 안드로이드 게시판 수정(파일없음)
	public void common_board_Modify_Nofile (HttpServletResponse res, int state) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(state);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}
	
	//안드로이드 게시판 수정
	public void common_board_Modify (HttpServletResponse res, int state) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(state);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}
	
	//안드로이드 게시판 정보확인 ArrayList처리
	public void common_boardList (HttpServletResponse res, List<BoardVO> boardList) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(boardList);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}
	//안드로이드 게시판 상세조회 ArrayList 처리
	public void common_boardDetail (HttpServletResponse res, List<BoardVO> boardList) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(boardList);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}
	//안드로이드 게시판 신규 등록
	public void common_board_Add (HttpServletResponse res, int state) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(state);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}
	//안드로이드 게시판 신규 등록(nofile)
	public void common_board_Add_Nofile (HttpServletResponse res, int state) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(state);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}
	//안드로이드 게시판 글 삭제
	public void common_and_board_Delete (HttpServletResponse res, int state) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(state);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}
			
}
