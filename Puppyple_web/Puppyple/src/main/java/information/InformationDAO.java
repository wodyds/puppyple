package information;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


@Repository
public class InformationDAO implements InformationService {

	@Autowired @Qualifier("bteam") private SqlSession sql;
	
	@Override
	public void information_insert(InformationVO vo) {
		sql.insert("information.mapper.insert", vo);
	}

	@Override
	public InformationVO information_detail(HashMap<String, String> map) {
		return sql.selectOne("information.mapper.detail", map);
	}

	@Override
	public void information_update(InformationVO vo) {
		sql.update("information.mapper.update", vo);
	}

	@Override
	public void information_delete(int id) {
		sql.delete("information.mapper.delete", id);
	}

	@Override
	public void information_read(int id) {
		sql.update("information.mapper.read", id);
	}

	@Override
	public InformationPage information_list(InformationPage page) {
		// 총 글의 개수를 조회 (totlList)
		int pagecnt = sql.selectOne("information.mapper.totalList", page);
		page.setTotallist(pagecnt);	// 총 글의 수
		
		// 전체 글을 조회하여 List
		List<InformationVO> list = sql.selectList("information.mapper.list", page);
		page.setList(list);
		return page;
	}

	@Override
	public int information_comment_insert(InformationCommentVO vo) {
		return sql.insert("information.mapper.comment_insert", vo);
	}

	@Override
	public int information_comment_update(InformationCommentVO vo) {
		 return sql.update("information.mapper.comment_update", vo);
	}

	@Override
	public void information_comment_delete(int id) {
		sql.delete("information.mapper.comment_delete", id);
	}

	@Override
	public List<InformationCommentVO> information_comment_list(int pid) {
		return sql.selectList("information.mapper.comment_list", pid);
	}


}
