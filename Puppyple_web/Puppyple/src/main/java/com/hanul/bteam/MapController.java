package com.hanul.bteam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MapController {

	// 맵 화면 요청
	@RequestMapping ("/map.map")
	public String PuppyMap() {
		return "map/map";
	}
	
}
