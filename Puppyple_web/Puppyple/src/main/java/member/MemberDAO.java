package member;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

//저장소 역할을 하므로 어노테이션을 해준다. DB에서 들어오는 데이터를 저장해서
//가지고 있는 형태를 취하게 된다.

@Repository
public class MemberDAO implements MemberService {

	@Autowired
	@Qualifier("bteam")
	private SqlSession sql;

	@Override
	public int member_and_join(MemberVO vo) {
		return sql.insert("member.mapper.and_join", vo);
	}

	@Override
	public MemberVO member_login(HashMap<String, String> map) {
		return sql.selectOne("member.mapper.and_login", map);
	}

	@Override
	public int member_and_update(MemberVO vo) {

		return sql.update("member.mapper.and_update", vo);
	}

	@Override
	public int member_and_delete(String member_id) {
		return sql.delete("member.mapper.and_delete", member_id);
	}

	@Override
	public int member_and_idChk(String member_id) {

		return sql.selectOne("member.mapper.and_idChk", member_id);
	}

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

	public int member_and_nickChk(String member_nickname) {
		return sql.selectOne("member.mapper.and_nickChk", member_nickname);
	}

	public int member_and_join_kakao(MemberVO vo) {
		return sql.insert("member.mapper.and_join_kakao", vo);
	}

	public int member_and_join_naver(MemberVO vo) {
		return sql.insert("member.mapper.and_join_naver", vo);
	}

	public List<MemberVO> member_id_find(String member_email) {
		return sql.selectList("member.mapper.and_id_find", member_email);
	}

	public List<MemberVO> member_pw_find(MemberVO vo) {
		return sql.selectList("member.mapper.and_pw_find", vo);
	}

	/////////////////////////////////////////////////////////////////////

	@Override
	public boolean member_join(MemberVO vo) {
		return sql.insert("member.mapper.join", vo) == 1 ? true : false;
	}

	@Override
	public MemberVO member_web_login(HashMap<String, String> map) {
		return sql.selectOne("member.mapper.web_login", map);
	}

	@Override
	public boolean member_web_update(MemberVO vo) {
		return false;
	}

	@Override
	public boolean member_web_delete(String member_id) {
		return false;
	}

	@Override
	public boolean member_id_check(String member_id) {
		return (Integer) sql.selectOne("member.mapper.member_id_check", member_id) == 0 ? true : false;
	}

	@Override
	public boolean member_nickname_check(String member_nickname) {
		return (Integer) sql.selectOne("member.mapper.member_nickname_check", member_nickname) == 0 ? true : false;
	}

	@Override
	public boolean member_web_social_email(MemberVO vo) {
		return (Integer) sql.selectOne("member.mapper.web_social_email", vo) == 0 ? true : false;
	}

	@Override
	public boolean member_web_social_insert(MemberVO vo) {
		return sql.insert("member.mapper.web_social_insert", vo) == 0 ? false : true;
	}

	@Override
	public boolean member_web_social_update(MemberVO vo) {
		return sql.update("member.mapper.web_social_update", vo) == 0 ? false : true;

	}

	public String web_member_id_find(String member_email) {
		return sql.selectOne("member.mapper.web_id_find", member_email);
	}

	public String web_member_pw_find(MemberVO vo) {
		return sql.selectOne("member.mapper.web_pw_find", vo);
	}

}
