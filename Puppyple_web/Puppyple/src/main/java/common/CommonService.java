package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CommonService {

	// 파일 다운로드 처리
	public void fileDownload(String board_filename, String board_filepath, HttpSession session,
			HttpServletResponse response) {

		// 실제 파일의 위치와 파일을 찾음
		File file = new File(session.getServletContext().getRealPath("resources") + "/" + board_filepath);
		// 파일의 형태를 확인
		String mime = session.getServletContext().getMimeType(board_filename);

		// 클라이언트에 파일을 첨부하여 쓰기 작업을 하는데 파일을
		// 첨부하는 건 header에 첨부 파일 정보를 넘겨줘야 함.
		try {
			board_filename = URLEncoder.encode(board_filename, "utf-8").replaceAll("\\+", "%20");

			response.setHeader("content-disposition", "attachment; filename = " + board_filename);

			ServletOutputStream out = response.getOutputStream();
			FileCopyUtils.copy(new FileInputStream(file), out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 펫 파일 다운로드 처리
	public void pet_fileDownload(String pet_filename, String pet_filepath, HttpSession session,
			HttpServletResponse response) {

		// 실제 파일의 위치와 파일을 찾음
		File file = new File(session.getServletContext().getRealPath("resources") + "/" + pet_filepath);
		// 파일의 형태를 확인
		String mime = session.getServletContext().getMimeType(pet_filename);

		// 클라이언트에 파일을 첨부하여 쓰기 작업을 하는데 파일을
		// 첨부하는 건 header에 첨부 파일 정보를 넘겨줘야 함.
		try {
			pet_filename = URLEncoder.encode(pet_filename, "utf-8").replaceAll("\\+", "%20");

			response.setHeader("content-disposition", "attachment; filename = " + pet_filename);

			ServletOutputStream out = response.getOutputStream();
			FileCopyUtils.copy(new FileInputStream(file), out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 파일 업로드 처리
	public String fileUpload(MultipartFile file, HttpSession session) {
		// 업로드 할 위치
		String resources = session.getServletContext().getRealPath("resources");
		// upload/OOOOOOOO_123.png
		String folder = resources + "/upload";
//		String uuid =  UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		String uuid = file.getOriginalFilename();

		File dir = new File(folder);

		if (!dir.exists())
			dir.mkdir();
		try {
//				file.transferTo(new File( uuid ));
			file.transferTo(new File(folder, uuid));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// upload/OOOOOOOO_123.png
		return folder.substring(resources.length() + 1) + "/" + uuid;
	}

	// 접근 토큰을 이용하여 프로필 API 호출하기 위하여 {access_token 과 token_type 값을 파라미터 전달}
	public String requestAPI(StringBuffer url, String property) {
		String result = ""; // result 변수 초기화 선언
		try {
//		      URL url = new URL(apiURL);
			// 연결할 개체가 HTTP 통신을 할 예정이므로 HTTP 간의 연결 개체 생성
			HttpURLConnection con = (HttpURLConnection) new URL(url.toString()).openConnection();
			con.setRequestMethod("GET");
			// 요청 헤더명이 "Authorization" 이므로 property 를 바꾸고,

			con.setRequestProperty("Authorization", property);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			System.out.print("responseCode=" + responseCode);
			if (responseCode >= 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8")); // 한글 깨짐 처리
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
			}
			String inputLine;
			StringBuffer res = new StringBuffer(); // 실제 값이 담겨진 변수 res 값을 리턴하여 보내줌.
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close(); // BufferedReader 닫기
			con.disconnect(); // HTTP 통신 연결 종료
			result = res.toString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	// API 명세 > 로그인 API 명세

	public String requestAPI(StringBuffer url) {
		String result = ""; // result 변수 초기화 선언
		try {
//		      URL url = new URL(apiURL);
			// 연결할 개체가 HTTP 통신을 할 예정이므로 HTTP 간의 연결 개체 생성
			HttpURLConnection con = (HttpURLConnection) new URL(url.toString()).openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			System.out.print("responseCode=" + responseCode);
			if (responseCode >= 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8")); // 한글 깨짐 처리
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
			}
			String inputLine;
			StringBuffer res = new StringBuffer(); // 실제 값이 담겨진 변수 res 값을 리턴하여 보내줌.
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close(); // BufferedReader 닫기
			con.disconnect(); // HTTP 통신 연결 종료
			result = res.toString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

}
