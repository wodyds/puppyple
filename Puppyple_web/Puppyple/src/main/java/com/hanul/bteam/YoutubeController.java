package com.hanul.bteam;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class YoutubeController {

	// 일상
	@RequestMapping("/youtube_daily.daily")
	public String daily(HttpSession session) {
		session.setAttribute("category", "daily");
		return "youtube/youtube_daily";
	}

	// 교육
	@RequestMapping("/youtube_edu.edu")
	public String education(HttpSession session) {
		session.setAttribute("category", "edu");
		return "youtube/youtube_edu";
	}

	// 의료/건강
	@RequestMapping("/youtube_health.health")
	public String health(HttpSession session) {
		session.setAttribute("category", "health");
		return "youtube/youtube_health";
	}

	// 미용
	@RequestMapping("/youtube_beauty.beauty")
	public String beauty(HttpSession session) {
		session.setAttribute("category", "beauty");
		return "youtube/youtube_beauty";
	}

	// 간식
	@RequestMapping("/youtube_snack.snack")
	public String snack(HttpSession session) {
		session.setAttribute("category", "snack");
		return "youtube/youtube_snack";
	}

}
