package com.hanul.bteam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import favorites.FavoritesServiceImpl;
import favorites.FavoritesVO;

@Controller
public class FavoritesController {

	@Autowired
	private FavoritesServiceImpl service;

	// 좋아요 ajax 기능으로 구현
	// 좋아요 추가 기능 location.href(community)
	@RequestMapping(value = "/favorites_insert", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void favorites_insert(FavoritesVO vo, Model model, HttpServletRequest req, HttpServletResponse response,
			HttpSession session) {
		// 1. 일단 좋아요 이미지 클릭시 컨트롤러로 오는지 콘솔창에 확인필요
		System.out.println("favorites_insert");
		System.out.println("favorites_insert" + req.getParameter("pid"));
		System.out.println("favorites_insert" + req.getParameter("flag"));
		System.out.println("favorites_insert" + req.getParameter("like_member_id"));

		vo.setPid(Integer.parseInt(req.getParameter("pid")));
		vo.setFlag(req.getParameter("flag"));
		vo.setLike_member_id(req.getParameter("like_member_id"));

		int state = service.favorites_insert(vo);

		System.out.println("favorite_insert: " + state);

	}

	// 좋아요 삭제 기능 location.href(community)
	@RequestMapping(value = "/favorites_delete", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void favorites_delete_com(FavoritesVO vo, Model model, HttpServletRequest req, HttpServletResponse response,
			HttpSession session) {
		// 1. 일단 좋아요 이미지 클릭시 컨트롤러로 오는지 콘솔창에 확인필요
		System.out.println("favorites_delete");
		System.out.println("favorites_insert" + req.getParameter("pid"));
		System.out.println("favorites_insert" + req.getParameter("flag"));
		System.out.println("favorites_insert" + req.getParameter("like_member_id"));

		vo.setPid(Integer.parseInt(req.getParameter("pid")));
		vo.setFlag(req.getParameter("flag"));
		vo.setLike_member_id(req.getParameter("like_member_id"));

		int state = service.favorites_delete(vo);

		System.out.println("favorite_delete: " + state);

	}

	// 좋아요 몇명했는지 카운트 하는 기능
	@RequestMapping("/favorits_count/{pid}")
	public String favorits_count(@PathVariable int pid, Model model, HttpServletRequest req,
			HttpServletResponse response, HttpSession session) {
		// 1. 일단 컨트롤러타는지 확인 필요
		System.out.println("favorits_count");
		System.out.println(req.getParameter("pid"));

		model.addAttribute("favorites_count", service.favorites_count(pid));

		return "community/favorite/favorites_count";
	}

}
