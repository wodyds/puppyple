package member;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO dao;

	@Override
	public int member_and_join(MemberVO vo) {
		return dao.member_and_join(vo);
	}

	@Override
	public MemberVO member_login(HashMap<String, String> map) {

		return dao.member_login(map);
	}

	@Override
	public int member_and_update(MemberVO vo) {
		return dao.member_and_update(vo);
	}

	@Override
	public int member_and_delete(String member_id) {
		return dao.member_and_delete(member_id);
	}

	@Override
	public int member_and_idChk(String member_id) {
		return dao.member_and_idChk(member_id);

	}

	public int member_and_nickname(String member_nickname) {
		return dao.member_and_nickChk(member_nickname);
	}

	public int member_and_join_kakao(MemberVO vo) {
		return dao.member_and_join_kakao(vo);
	}

	public int member_and_join_naver(MemberVO vo) {
		return dao.member_and_join_naver(vo);
	}

	public List<MemberVO> member_id_find(String member_email) {
		return dao.member_id_find(member_email);
	}

	public List<MemberVO> member_pw_find(MemberVO vo) {
		return dao.member_pw_find(vo);
	}

	//////////////////////////////////////////////////////////

	@Override
	public boolean member_social_email(MemberVO vo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean member_social_insert(MemberVO vo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean member_social_update(MemberVO vo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean member_join(MemberVO vo) {
		return dao.member_join(vo);
	}

	@Override
	public MemberVO member_web_login(HashMap<String, String> map) {
		return dao.member_web_login(map);
	}

	@Override
	public boolean member_web_update(MemberVO vo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean member_web_delete(String member_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean member_id_check(String member_id) {
		return dao.member_id_check(member_id);
	}

	@Override
	public boolean member_nickname_check(String member_nickname) {
		return dao.member_nickname_check(member_nickname);
	}

	@Override
	public boolean member_web_social_email(MemberVO vo) {
		return dao.member_web_social_email(vo);
	}

	@Override
	public boolean member_web_social_insert(MemberVO vo) {
		return dao.member_web_social_insert(vo);
	}

	@Override
	public boolean member_web_social_update(MemberVO vo) {
		return dao.member_web_social_update(vo);
	}

	public String web_member_id_find(String member_email) {
		return dao.web_member_id_find(member_email);
	}

	public String web_member_pw_find(MemberVO vo) {
		return dao.web_member_pw_find(vo);
	}

}
