package com.hanul.bteam;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ArduController {

	@RequestMapping(value = "/arduFeedMotor", method = { RequestMethod.GET, RequestMethod.POST })
	public String arduGetLed(HttpServletRequest req, Model model) {

		System.out.println("arduFeedMotor()");

		return "/iot/arduFeedMotor";
	}

}
