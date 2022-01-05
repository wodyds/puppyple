package inquiry;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class InquiryDAO implements InquiryService {

	@Autowired
	@Qualifier("bteam")
	private SqlSession sql;

	@Override
	public void inquiry_insert(InquiryVO vo) {
		sql.insert("inquiry.mapper.insert", vo);
	}

	@Override
	public InquiryVO inquiry_detail(HashMap<String, String> map) {
		return sql.selectOne("inquiry.mapper.detail", map);
	}

	@Override
	public void inquiry_update(InquiryVO vo) {
		sql.update("inquiry.mapper.update", vo);
	}

	@Override
	public void inquiry_delete(int id) {
		sql.delete("inquiry.mapper.delete", id);
	}

	@Override
	public InquiryPage inquiry_list(InquiryPage page) {
		// 총 글의 개수를 조회 (totlList)
		int pagecnt = sql.selectOne("inquiry.mapper.totalList", page);
		page.setTotallist(pagecnt); // 총 글의 수

		// 전체 글을 조회하여 List
		List<InquiryVO> list = sql.selectList("inquiry.mapper.list", page);
		page.setList(list);
		return page;
	}

	@Override
	public int inquiry_comment_insert(InquiryCommentVO vo) {
		return sql.insert("inquiry.mapper.comment_insert", vo);
	}

	@Override
	public int inquiry_comment_update(InquiryCommentVO vo) {
		return sql.update("inquiry.mapper.comment_update", vo);
	}

	@Override
	public void inquiry_comment_delete(int id) {
		sql.delete("inquiry.mapper.comment_delete", id);
	}

	@Override
	public List<InquiryCommentVO> inquiry_comment_list(int pid) {
		return sql.selectList("inquiry.mapper.comment_list", pid);
	}

}
