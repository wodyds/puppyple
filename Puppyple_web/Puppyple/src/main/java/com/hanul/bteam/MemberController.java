package com.hanul.bteam;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.net.URL;

import javax.inject.Inject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.tiles.request.Request;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import board.BoardVO;
import common.CommonService;
import member.MemberServiceImpl;
import member.MemberVO;

@Controller
public class MemberController {

	// 사용가능하도록 Autowired로 메모리에 올려놓는다.
	@Autowired
	private MemberServiceImpl service;
	@Autowired
	private CommonService common;
	private String naver_client_id = "J6_1iReuEAh2mHp0TiCo";
	private String kakao_client_id = "656e30c0cdae09c273417849d23116e7";

	// 비밀번호찾기 결과 화면
	@RequestMapping("/find_pw_check")
	public String pw_find_check(HttpServletRequest req, Model model, HttpServletResponse response) {
		MemberVO vo = new MemberVO();
		String member_id = req.getParameter("input_id");
		String member_email = req.getParameter("input_email");
		vo.setMember_id(member_id);
		vo.setMember_email(member_email);
		String pw = service.web_member_pw_find(vo);

		if (pw != null) {
			HtmlEmail mail = new HtmlEmail();

			mail.setHostName("smtp.naver.com");
			mail.setCharset("utf-8");
			mail.setDebug(true);

			// 로그인하기위한 아이디 비번 지정
			mail.setAuthentication("wodyds", "mild7072%%");
			mail.setSSLOnConnect(true);

			try {
				mail.setFrom("wodyds@naver.com", "퍼피플");
				mail.addTo(vo.getMember_email(), "퍼피플 회원님");
				mail.setSubject("퍼피플 비밀번호 찾기");

				StringBuffer msg = new StringBuffer("<html>");
				msg.append("<body>");
				msg.append("<a href='http://211.223.59.27:8002/bteam/'>"
						+ "<img src='https://postfiles.pstatic.net/MjAyMTEyMjNfMTk4/MDAxNjQwMjMwMDY5MzM5.Mztt2JO30HomTl2V26WbOlvCdWvRLJRg26ug7qjl6aAg.bN92Upv8i8qDRtjDbtpVZDcjvmM0Oru1SZA3Bpqrq7Ug.PNG.wodyds/logo.png?type=w773'/></a>");
				msg.append("<hr>");
				msg.append("<p>비밀번호 찾기 결과입니다.</p>");
				msg.append(
						"<p>회원님의 비밀번호는 " + "<b style='color: #6215e4; font-size:16px;'>" + pw + "</b>" + " 입니다.</p>");
				msg.append("<p>보안을 위해 비밀번호를 변경하실것을 추천드립니다.</p>");
				msg.append("</body>");
				msg.append("</html>");

				mail.setHtmlMsg(msg.toString());
				mail.send();

			} catch (EmailException e) {
				e.printStackTrace();
			}
		}

		model.addAttribute("pw", service.web_member_pw_find(vo));
		return "member/find_pw_check";
	}

	// 아이디찾기 결과 화면
	@RequestMapping("/find_id_check")
	public String id_find_check(HttpServletRequest req, Model model, HttpServletResponse response) {
		String member_email = req.getParameter("input_email");
		String id = service.web_member_id_find(member_email);
		model.addAttribute("id", id);
		if (id != null) {
			HtmlEmail mail = new HtmlEmail();

			mail.setHostName("smtp.naver.com");
			mail.setCharset("utf-8");
			mail.setDebug(true);

			// 로그인하기위한 아이디 비번 지정
			mail.setAuthentication("wodyds", "mild7072%%");
			mail.setSSLOnConnect(true);

			try {
				mail.setFrom("wodyds@naver.com", "퍼피플");
				mail.addTo(member_email, "퍼피플 회원님");
				mail.setSubject("퍼피플 아이디 찾기");

				StringBuffer msg = new StringBuffer("<html>");
				msg.append("<body>");
				msg.append("<a href='http://211.223.59.27:8002/bteam/'>"
						+ "<img src='https://postfiles.pstatic.net/MjAyMTEyMjNfMTk4/MDAxNjQwMjMwMDY5MzM5.Mztt2JO30HomTl2V26WbOlvCdWvRLJRg26ug7qjl6aAg.bN92Upv8i8qDRtjDbtpVZDcjvmM0Oru1SZA3Bpqrq7Ug.PNG.wodyds/logo.png?type=w773'/></a>");
				msg.append("<hr>");
				msg.append("<p>아이디 찾기 결과입니다.</p>");
				msg.append("<p>회원님의 아이디는 " + "<b style='color: #6215e4; font-size:16px;'>" + id + "</b>" + " 입니다.</p>");
				msg.append("<p>비밀번호가 기억나지 않으시다면 비밀번호 찾기 기능을 이용해주세요.</p>");
				msg.append("</body>");
				msg.append("</html>");

				mail.setHtmlMsg(msg.toString());
				mail.send();

			} catch (EmailException e) {
				e.printStackTrace();
			}
		}

		return "member/find_id_check";
	}

	// 아이디찾기 화면 요청
	@RequestMapping("/id_find.find")
	public String id_find(HttpSession session) {
		// title 태그에 사용 할 수 있도록 지정(session)
		session.setAttribute("category", "find");
		return "member/id_find";
	}

	// 비밀번호찾기 화면 요청
	@RequestMapping("/pw_find.find")
	public String pw_find(HttpSession session) {
		// title 태그에 사용 할 수 있도록 지정(session)
		session.setAttribute("category", "find");
		return "member/pw_find";
	}

	// 소셜로그인

	// 네이버 로그인 요청
	@RequestMapping("/naverLogin")
	public String naverLogin(HttpSession session) {
		// 네아로 연동 URL 생성
		String state = UUID.randomUUID().toString();
		session.setAttribute("state", state);

//					https://nid.naver.com/oauth2.0/authorize?response_type=code
//					&client_id=CLIENT_ID
//					&state=STATE_STRING
//					&redirect_uri=CALLBACK_URL

		StringBuffer url = new StringBuffer("https://nid.naver.com/oauth2.0/authorize?response_type=code");
		url.append("&client_id=").append(naver_client_id);
		url.append("&state=").append(state);
		url.append("&redirect_uri=http://211.223.59.27:8002/bteam/navercallback");
		return "redirect:" + url.toString();
	}

	// 네이버 Callback 메소드 선언

	@RequestMapping("/navercallback")
	public String navercallback(@RequestParam(required = false) String code, String state,
			@RequestParam(required = false) String error, HttpSession session) {

		// state 값이 맞지 않거나 error가 발생하면 토큰 발급 불가
		if (!state.equals(session.getAttribute("state")) || error != null) {
			return "redirect:/"; // 메인 페이지로 이동
		}

		// 3.4.4 접근토큰 발급 요청(※naver참고)

//					https://nid.naver.com/oauth2.0/token?grant_type=authorization_code
//					&client_id=jyvqXeaVOVmV
//					&client_secret=527300A0_COq1_XV33cf
//					&code=EIc5bFrl4RibFls1
//					&state=9kgsGTfH4j7IyAkg

		StringBuffer url = new StringBuffer("https://nid.naver.com/oauth2.0/token?grant_type=authorization_code");
		url.append("&client_id=").append(naver_client_id);
		url.append("&client_secret=2xzY4AE2dp");
		url.append("&code=").append(code);
		url.append("&state=").append(state);

		// common.requestAPI() 를 통해 출력받는 값의 형태가 json 이므로
		// json 객체를 사용하여 값을 할당 (json 라이브러리 pom.xml 주입)
		JSONObject json = new JSONObject(common.requestAPI(url));

		String token = json.getString("access_token");
		String type = json.getString("token_type");

		// 3.4.5 접근 토큰을 이용하여 프로필 API 호출하기

//					curl  -XGET "https://openapi.naver.com/v1/nid/me" \
//				      -H "Authorization: Bearer AAAAPIuf0L+qfDkMABQ3IJ8heq2mlw71DojBj3oc2Z6OxMQESVSrtR0dbvsiQbPbP1/cxva23n7mQShtfK4pchdk/rc="

		url = new StringBuffer("https://openapi.naver.com/v1/nid/me");
		json = new JSONObject(common.requestAPI(url, type + " " + token));

		// 문서페이지 > 튜토리얼 > Web 애플리케이션 문서를 보면 지금까지 진행한 부분을 확인 가능
		if (json.getString("resultcode").equals("00")) {
			json = json.getJSONObject("response");

			// 회원정보 DB에 값을 담아서 관리 MemberVO
			MemberVO vo = new MemberVO();
			vo.setSocial_type("member_naver"); // 소셜 로그인 형태를 지정("naver")
			// vo.setMember_id(json.getString("id"));
			// 소셜 로그인을 했을 경우 해당 정보를 저장하여 소셜 구분을 위함.
			vo.setSocial_email(json.getString("email"));
			// vo.setMember_nickname(json.getString("nickname"));

			vo.setMember_id(json.getString("id"));
			vo.setMember_pw(json.getString("email"));
			// vo.setMember_nickname("specialty");
			vo.setMember_nickname(json.getString("nickname"));
			// vo.setMember_phone("01012345678");
			vo.setMember_phone(json.getString("mobile"));
			vo.setMember_email(json.getString("email"));

			// 네이버 최초 로그인인 경우 회원정보 저장 (insert)
			// 네이버 로그인 이력이 있어 회원정보가 있다면 변경 저장

			if (service.member_web_social_email(vo)) {
				service.member_web_social_insert(vo);
			} else {
				// service.member_web_social_insert(vo);
			}
			session.setAttribute("loginInfo", vo);
		}

		return "redirect:/"; // 로그인 시 루트(home.jsp)로 이동
	}

	// 카카오 로그인 요청
	@RequestMapping("/kakaoLogin")
	public String kakaoLogin(HttpSession session) {
		// 카카오아이디로 연동 URL 생성
		String state = UUID.randomUUID().toString();
		session.setAttribute("state", state);

//					https://kauth.kakao.com/oauth/authorize?response_type=code
//					&client_id={REST_API_KEY}
//					&redirect_uri={REDIRECT_URI}

		StringBuffer url = new StringBuffer("https://kauth.kakao.com/oauth/authorize?response_type=code");
		url.append("&client_id=").append(kakao_client_id);
		url.append("&state=").append(state);
		url.append("&redirect_uri=http://localhost/bteam/kakaocallback");
		return "redirect:" + url.toString();
	}

	// 카카오 Callback 메소드 선언

	@RequestMapping("/kakaocallback")
	public String kakaocallback(@RequestParam(required = false) String code, String state,
			@RequestParam(required = false) String error, HttpSession session) {

		// state 값이 맞지 않거나 error가 발생하면 토큰 발급 불가
		if (!state.equals(session.getAttribute("state")) || error != null) {
			return "redirect:/"; // 메인 페이지로 이동
		}

		StringBuffer url = new StringBuffer("https://kauth.kakao.com/oauth/token?grant_type=authorization_code");
		url.append("&client_id=").append(kakao_client_id);
		url.append("&client_secret=yTNOtlegzrf2y02XeAQh4Ek2bzZpsTk3");
		url.append("&code=").append(code);

		// common.requestAPI() 를 통해 출력받는 값의 형태가 json 이므로
		// json 객체를 사용하여 값을 할당 (json 라이브러리 pom.xml 주입)
		JSONObject json = new JSONObject(common.requestAPI(url));

		String token = json.getString("access_token");
		String type = json.getString("token_type");

		url = new StringBuffer("https://kapi.kakao.com/v2/user/me");
		json = new JSONObject(common.requestAPI(url, type + " " + token));

		if (!json.isEmpty()) {

			JSONObject properties = json.getJSONObject("properties");
			JSONObject kakao_account = json.getJSONObject("kakao_account");

			// 회원정보 DB에 값을 담아서 관리 MemberVO
			MemberVO vo = new MemberVO();
			vo.setSocial_type("member_kakao"); // 소셜 로그인 형태를 지정("kakao")
			vo.setSocial_email(kakao_account.getString("email"));

			vo.setMember_id(kakao_account.getString("email"));
			vo.setMember_pw(kakao_account.getString("email"));
			vo.setMember_nickname(properties.getString("nickname"));
			vo.setMember_phone("01012345678");
			vo.setMember_email(kakao_account.getString("email"));
			// 최초 로그인인 경우 회원정보 저장 (insert)
			// 로그인 이력이 있어 회원정보가 있다면 변경 저장
			if (service.member_web_social_email(vo)) {
				service.member_web_social_insert(vo);
			} else {
				// service.member_web_social_insert(vo);
			}
			session.setAttribute("loginInfo", vo);
		}
		return "redirect:/"; // 로그인 시 루트(home.jsp)로 이동
	}

	// Web

	// 회원 가입 처리 요청
	@ResponseBody
	@RequestMapping(value = "/join", produces = "text/html; charset=utf-8")
	public String join(MemberVO vo, HttpServletRequest req, HttpSession session) {
		StringBuffer msg = new StringBuffer("<script>");
		if (service.member_join(vo)) {
			msg.append("alert('회원가입을 축하드립니다.'); location='").append(req.getContextPath()).append("'");
//					msg.append("alert('회원가입을 축하드립니다.'); location='login'")
			HtmlEmail mail = new HtmlEmail();

			mail.setHostName("smtp.naver.com");
			mail.setCharset("utf-8");
			mail.setDebug(true);

			// 로그인하기위한 아이디 비번 지정
			mail.setAuthentication("wodyds", "mild7072%%");
			mail.setSSLOnConnect(true);

			try {
				mail.setFrom("wodyds@naver.com", "퍼피플");
				mail.addTo(vo.getMember_email(), "퍼피플 회원님");
				mail.setSubject("퍼피플 신규가입을 환영합니다.");

				StringBuffer msg1 = new StringBuffer("<html>");
				msg1.append("<body>");
				msg1.append("<a href='http://211.223.59.27:8002/bteam/'>"
						+ "<img src='https://postfiles.pstatic.net/MjAyMTEyMjNfMTk4/MDAxNjQwMjMwMDY5MzM5.Mztt2JO30HomTl2V26WbOlvCdWvRLJRg26ug7qjl6aAg.bN92Upv8i8qDRtjDbtpVZDcjvmM0Oru1SZA3Bpqrq7Ug.PNG.wodyds/logo.png?type=w773'/></a>");
				msg1.append("<hr>");
				msg1.append("<p>반려동물들의 소통과 공유의 창 퍼피플에 가입해주셔서 감사합니다.</p>");
				msg1.append("<p>퍼피플은 퍼피플 전용 어플리케이션을 통해 언제 어디서든 접속하실 수 있습니다.</p>");
				msg1.append("<p>퍼피플과 함께 즐거운시간 되시길 바랍니다.</p>");
				msg1.append("</body>");
				msg1.append("</html>");

				mail.setHtmlMsg(msg1.toString());
				mail.send();

			} catch (EmailException e) {
				e.printStackTrace();
			}

		} else {
			msg.append("alert('회원가입 실패'); location='member'");
		}
		msg.append("</script>");

		return msg.toString();
	}

	// ID 중복 확인 요청
	@ResponseBody
	@RequestMapping("/member_id_check")
	public boolean member_id_check(String member_id) {
		return service.member_id_check(member_id);
	}

	// 닉네임 중복 확인 요청
	@ResponseBody
	@RequestMapping("/member_nickname_check")
	public boolean member_nickname_check(String member_nickname) {
		return service.member_nickname_check(member_nickname);
	}

	// 회원 가입 페이지 화면 이동
	@RequestMapping("/member")
	public String member(HttpSession session) {
		// 타이틀에 회원가입 명시하기 위한 session
		session.setAttribute("category", "join");
		return "member/join";
	}

	// 로그아웃 처리 요청
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		// 세션에 담긴 로그인 정보를 삭제한다
		session.removeAttribute("loginInfo");

		// 로그아웃 시 루트(home.jsp)로 이동
		return "redirect:/";
	}

	// 로그인 처리 요청
	@ResponseBody // 자바 객체를 Client(Http) 요청의 body 내용으로 매핑함(전송)
	@RequestMapping("/puppypleLogin")
	public boolean login(String member_id, String member_pw, HttpSession session) {
		// 화면에서 전송한 아이디, 비밀번호가 일치하는 회원 정보를 조회
		// 매개변수 2개는 HashMap 형태로 담아 service 전달
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("member_id", member_id);
		map.put("member_pw", member_pw);
		MemberVO vo = service.member_web_login(map);
		session.setAttribute("loginInfo", vo);

		return vo == null ? false : true; // 결과값이 null이면 false / null 아니면 true

	}
	
	// 로그인 화면 요청
	@RequestMapping("/login")
	public String login(HttpSession session) {
		// title 태그에 사용 할 수 있도록 지정(session)
		session.setAttribute("category", "login");
		return "member/login";
	}

/////////////////////////////////////////////////////////////////////////////////////

	// Android
	// 회원탈퇴기능구형

	@ResponseBody
	@RequestMapping(value = "/and_memberDelete", method = { RequestMethod.GET, RequestMethod.POST })
	public void and_memberDelete(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_memberDelete");

		// 1. Save the req parameter
		String member_id = req.getParameter("member_id");

		// 2. Check values
		// http://192.168.0.8:8989/bteam/and_memberDelete?member_id=aaa
		// Copy the url and paste the url on web program and check the console
		System.out.println(member_id);

		int state = service.member_and_delete(member_id);

		common_memberDelete(res, state);

	}

	// 회원가입정보 수정
	@ResponseBody // 아래를 일단 다 받아서 요청한곳으로 정보를 통째로 이동한다.
	@RequestMapping(value = "/and_memberModify")
	public void and_memberModify(MemberVO vo, HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_memberModify");

		// 1. Save the req parameter
		vo.setMember_id(req.getParameter("member_id"));
		vo.setMember_pw(req.getParameter("member_pw"));
		vo.setMember_phone(req.getParameter("member_phone"));
		vo.setMember_email(req.getParameter("member_email"));
		vo.setMember_nickname(req.getParameter("member_nickname"));

		// 2. Check values
		// http://192.168.0.8:8989/bteam/and_memberModify?member_id=aaa&member_pw=aaa&member_nickname=aaa
		// Copy the url and paste the url on web program and check the console
		System.out.println(vo.getMember_id() + vo.getMember_pw() + vo.getMember_nickname());

		int state = service.member_and_update(vo);

		common_memberModify(res, state);

	}

	// 아이디 중복체크
	@ResponseBody
	@RequestMapping(value = "/and_idChk", method = { RequestMethod.GET, RequestMethod.POST })
	public void and_idChk(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_idChk");

		// 1. Save the req parameter
		String member_id = req.getParameter("member_id");

		int state = service.member_and_idChk(member_id);

		System.out.println(state);

		common_idChk(res, state);
	}

	// 아이디 중복체크
	@ResponseBody
	@RequestMapping(value = "/and_nickChk", method = { RequestMethod.GET, RequestMethod.POST })
	public void and_nickChk(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_nickChk");

		// 1. Save the req parameter
		String member_nickname = req.getParameter("member_nickname");

		int state = service.member_and_nickname(member_nickname);

		System.out.println(state);

		common_nickChk(res, state);
	}

	// 아이디 찾기
	@ResponseBody
	@RequestMapping(value = "/and_id_find", method = { RequestMethod.GET, RequestMethod.POST })
	public void and_id_find(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_id_find");

		String member_email = req.getParameter("member_email");
		// 1.Save the req parameter

		// 2. Check values
		// http://192.168.0.8:8989/bteam/and_petList?member_id=aaa

		List<MemberVO> memberList = new ArrayList<MemberVO>();

		memberList = service.member_id_find(member_email);

		common_memberList(res, memberList);
	}

	// 비밀번호 찾기
	@ResponseBody
	@RequestMapping(value = "/and_pw_find", method = { RequestMethod.GET, RequestMethod.POST })
	public void and_pw_find(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_pw_find");

		MemberVO vo = new MemberVO();
		vo.setMember_id(req.getParameter("member_id"));
		vo.setMember_email(req.getParameter("member_email"));

		// 1.Save the req parameter

		// 2. Check values
		// http://192.168.0.8:8989/bteam/and_petList?member_id=aaa

		List<MemberVO> memberList = new ArrayList<MemberVO>();

		memberList = service.member_pw_find(vo);

		common_memberList(res, memberList);
	}

	// 회원가입처리
	@ResponseBody // 아래를 일단 다 받아서 이동한다.
	@RequestMapping(value = "/and_join", method = { RequestMethod.GET, RequestMethod.POST })
	public void and_join(MemberVO vo, HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_join");

		// 1. Save the req parameter
		vo.setMember_id(req.getParameter("member_id"));
		vo.setMember_pw(req.getParameter("member_pw"));
		vo.setMember_nickname(req.getParameter("member_nickname"));
		vo.setMember_phone(req.getParameter("member_phone"));
		vo.setMember_email(req.getParameter("member_email"));

		// 2. Check values
		// http://192.168.0.8:8989/bteam/join?member_id=aaa&member_pw=aaa&member_nickname=aaa
		// Copy the url and paste the url on web program and check the console
		System.out.println(vo.getMember_id() + vo.getMember_pw() + vo.getMember_nickname());

		int state = service.member_and_join(vo);

		common_join(res, state);
	}

	// 카카오회원가입처리
	@ResponseBody // 아래를 일단 다 받아서 이동한다.
	@RequestMapping(value = "/and_join_kakao", method = { RequestMethod.GET, RequestMethod.POST })
	public void and_joinKakao(MemberVO vo, HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_join_kakao");

		// 1. Save the req parameter
		vo.setMember_id(req.getParameter("member_id"));
		vo.setMember_pw(req.getParameter("member_pw"));
		vo.setMember_phone(req.getParameter("member_phone"));
		vo.setMember_email(req.getParameter("member_email"));
		vo.setMember_nickname(req.getParameter("member_nickname"));
		vo.setMember_kakao(req.getParameter("member_kakao"));
		vo.setMember_admin(req.getParameter("member_admin"));

		// 2. Check values
		// http://192.168.0.8:8989/bteam/join?member_id=aaa&member_pw=aaa&member_nickname=aaa
		// Copy the url and paste the url on web program and check the console
		System.out.println(vo.getMember_id() + vo.getMember_pw() + vo.getMember_nickname());

		int state = service.member_and_join_kakao(vo);

		common_join_kakao(res, state);
	}

	// 네이버회원가입처리
	@ResponseBody // 아래를 일단 다 받아서 이동한다.
	@RequestMapping(value = "/and_join_naver", method = { RequestMethod.GET, RequestMethod.POST })
	public void and_joinNaver(MemberVO vo, HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_join_naver");

		// 1. Save the req parameter
		vo.setMember_id(req.getParameter("member_id"));
		vo.setMember_pw(req.getParameter("member_pw"));
		vo.setMember_phone(req.getParameter("member_phone"));
		vo.setMember_email(req.getParameter("member_email"));
		vo.setMember_nickname(req.getParameter("member_nickname"));
		vo.setMember_naver(req.getParameter("member_naver"));

		// 2. Check values
		// http://192.168.0.8:8989/bteam/join?member_id=aaa&member_pw=aaa&member_nickname=aaa
		// Copy the url and paste the url on web program and check the console
		System.out.println(vo.getMember_id() + vo.getMember_pw() + vo.getMember_nickname());

		int state = service.member_and_join_naver(vo);

		common_join_naver(res, state);
	}

	// 로그인
	@ResponseBody
	@RequestMapping(value = "/and_login", method = { RequestMethod.GET, RequestMethod.POST })
	public void and_login(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("and_login");

		// 1. Save the req parameter
		String member_id = req.getParameter("member_id");
		String member_pw = req.getParameter("member_pw");

		// 2. Check values
		// http://192.168.0.8:8989/bteam/and_join?member_id=aaa&member_pw=aaa
		// Copy the url and paste the url on web program and check the console
		System.out.println(member_id + ", " + member_pw);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("member_id", member_id);
		map.put("member_pw", member_pw);

		MemberVO vo = service.member_login(map);

		common_login(res, vo);
	}

//////////////////////////////////////////////////////////////////////////////////////
	
	//20211124 안드로이드 회원탈퇴 기능
	public void common_memberDelete(HttpServletResponse res, int state) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(state);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}

	// 20211123 안드로이드 회원정보 수정
	public void common_memberModify(HttpServletResponse res, int state) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(state);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}

	// 안드로이드 닉네임 중복체크
	public void common_nickChk(HttpServletResponse res, int state) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(state);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}

	// 안드로이드 아이디 중복체크
	public void common_idChk(HttpServletResponse res, int state) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(state);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}

	// 안드로이드 네이버 회원가입 처리
	public void common_join_naver(HttpServletResponse res, int state) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(state);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}

	// 안드로이드 카카오 회원가입 처리
	public void common_join_kakao(HttpServletResponse res, int state) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(state);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}

	// 안드로이드 회원가입 처리
	public void common_join(HttpServletResponse res, int state) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(state);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}

	// 안드로이드 로그인 처리
	public void common_login(HttpServletResponse res, MemberVO vo) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(vo);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}

	// 멤버 정보확인 ArrayList처리
	public void common_memberList(HttpServletResponse res, List<MemberVO> boardList) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(boardList);
		PrintWriter out;
		out = res.getWriter();
		out.println(json);
	}

}// class MemberController