package member;

import java.util.HashMap;

public interface MemberService {

	// 웹

	// 회원가입시 회원정보 저장 (C)
	boolean member_join(MemberVO vo);

	// 아이디, 비번 일치하는 회원정보조회(R)
	MemberVO member_web_login(HashMap<String, String> map);

	// 회원정보 변경저장 (U)
	boolean member_web_update(MemberVO vo);

	// 회원탈퇴시 회원정보 삭제 (D)
	boolean member_web_delete(String member_id);

	// 아이디 중복확인(R)
	boolean member_id_check(String member_id);

	// 닉네임 중복확인(R)
	boolean member_nickname_check(String member_nickname);

	// 소셜 회원 정보 존재여부 (R)
	boolean member_web_social_email(MemberVO vo);

	// 소셜 회원 정보 신규 저장(C)
	boolean member_web_social_insert(MemberVO vo);

	// 소셜 회원 정보 변경 저장(U)
	boolean member_web_social_update(MemberVO vo);

	
	// 안드로이드

	// 회원가입시 회원정보 저장 (C)
	int member_and_join(MemberVO vo);

	// 아이디, 비번 일치하는 회원정보조회(R)
	MemberVO member_login(HashMap<String, String> map);

	// 20211123
	// 회원정보 변경저장 (U)
	int member_and_update(MemberVO vo);

	// 20211124
	// 회원탈퇴시 회원정보 삭제 (D)
	int member_and_delete(String member_id);

	// 아이디 중복확인(R)
	int member_and_idChk(String member_id);

	// 소셜 회원 정보 존재여부 (R)
	boolean member_social_email(MemberVO vo);

	// 소셜 회원 정보 신규 저장(C)
	boolean member_social_insert(MemberVO vo);

	// 소셜 회원 정보 변경 저장(U)
	boolean member_social_update(MemberVO vo);

}
