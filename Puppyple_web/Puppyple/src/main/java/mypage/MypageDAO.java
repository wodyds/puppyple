package mypage;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import community.CommunityCommentVO;
import community.CommunityVO;
import gallery.GalleryVO;
import member.MemberVO;
import pet.PetVO;

//저장소 역할을 하므로 어노테이션을 해준다. DB에서 들어오는 데이터를 저장해서
//가지고 있는 형태를 취하게 된다.

@Repository
public class MypageDAO implements MypageService {

	@Autowired
	@Qualifier("bteam")
	private SqlSession sql;

	@Override
	public void member_info_delete(String member_id) {
		sql.delete("mypage.mapper.info_delete", member_id);
	}

	@Override
	public void member_info_update(MypageVO vo) {
		sql.update("mypage.mapper.info_update", vo);
	}

	@Override
	public MypageVO member_info(String member_id) {
		return sql.selectOne("mypage.mapper.member_info", member_id);
	}

	public void web_member_modify(MemberVO vo) {
		sql.update("mypage.mapper.web_member_modify", vo);
	}

	@Override
	public MypagePostPage mypage_post(MypagePostPage page) {
		// 총 글의 개수를 조회(totalList)

		String member_id = page.getMember_id();

		int pagecnt = sql.selectOne("mypage.mapper.totalList", member_id);
		page.setTotallist(pagecnt);// 총글의 수

		// 전체 글을 조회하여 List
		List<CommunityVO> list = sql.selectList("mypage.mapper.myPostList", page);
		page.setList(list);

		return page;
	}

	@Override
	public MypagePostPage mypage_favorites(MypagePostPage page) {

		String member_id = page.getMember_id();

		int pagecnt = sql.selectOne("mypage.mapper.totalListFavorites", member_id);
		page.setTotallist(pagecnt);

		// 전체 글을 조회하여 List
		List<CommunityVO> list = sql.selectList("mypage.mapper.myFavoritesList", page);
		page.setList(list);

		return page;
	}

	@Override
	public MypagePage mypage_admin_list(MypagePage page) {
		// 총 글의 개수를 조회 (totlList)
		int pagecnt = sql.selectOne("mypage.mapper.admin_totalList", page);
		page.setTotallist(pagecnt); // 총 글의 수

		// 전체 글을 조회하여 List
		List<MypageVO> list = sql.selectList("mypage.mapper.admin_list", page);
		page.setList(list);
		return page;
	}

	@Override
	public void admin_delete(String member_id) {
		sql.delete("mypage.mapper.admin_delete", member_id);
	}

	@Override
	public MypageVO admin_detail(String member_id) {
		return sql.selectOne("mypage.mapper.admin_detail", member_id);
	}

	@Override
	public List<MypageVO> admin_pet_petList(String member_id) {
		return sql.selectList("mypage.mapper.admin_pet_petList", member_id);
	}

	@Override
	public void admin_pet_web_petDelete(int pet_id) {
		sql.delete("mypage.mapper.admin_web_petDelete", pet_id);

	}

}
