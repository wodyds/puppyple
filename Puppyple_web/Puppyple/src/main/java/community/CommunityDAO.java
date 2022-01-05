package community;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CommunityDAO implements CommunityService {

	@Autowired
	@Qualifier("bteam")
	private SqlSession sql;

	@Override
	public void community_insert(CommunityVO vo) {
		sql.insert("community.mapper.insert", vo);
	}

	@Override
	public CommunityVO community_detail(HashMap<String, String> map) {
		return sql.selectOne("community.mapper.detail", map);
	}

	@Override
	public void community_update(CommunityVO vo) {
		sql.update("community.mapper.update", vo);
	}

	@Override
	public void community_delete(int id) {
		sql.delete("community.mapper.delete", id);
	}

	@Override
	public void community_read(int id) {
		sql.update("community.mapper.read", id);
	}

	@Override
	public CommunityPage community_list(CommunityPage page) {
		// 총 글의 개수를 조회 (totlList)
		int pagecnt = sql.selectOne("community.mapper.totalList", page);
		page.setTotallist(pagecnt); // 총 글의 수

		// 전체 글을 조회하여 List
		List<CommunityVO> list = sql.selectList("community.mapper.list", page);
		page.setList(list);
		return page;
	}

	@Override
	public int community_comment_insert(CommunityCommentVO vo) {
		return sql.insert("community.mapper.comment_insert", vo);
	}

	@Override
	public int community_comment_update(CommunityCommentVO vo) {
		return sql.update("community.mapper.comment_update", vo);
	}

	@Override
	public void community_comment_delete(int id) {
		sql.delete("community.mapper.comment_delete", id);
	}

	@Override
	public List<CommunityCommentVO> community_comment_list(int pid) {
		return sql.selectList("community.mapper.comment_list", pid);
	}

	@Override
	public CommunityPage community_list(HashMap<String, Object> map) {
		CommunityPage page = (CommunityPage) map.get("page");
		// 총 글의 개수를 조회 (totlList)
		int pagecnt = sql.selectOne("community.mapper.totalList", map);
		page.setTotallist(pagecnt); // 총 글의 수

		// 전체 글을 조회하여 List
		List<CommunityVO> list = sql.selectList("community.mapper.list", map);
		page.setList(list);
		return page;
	}

}
