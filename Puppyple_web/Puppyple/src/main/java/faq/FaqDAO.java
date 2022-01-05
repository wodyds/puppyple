package faq;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class FaqDAO implements FaqService {

	@Autowired
	@Qualifier("bteam")
	private SqlSession sql;

	@Override
	public void faq_insert(FaqVO vo) {
		sql.insert("faq.mapper.insert", vo);
	}

	@Override
	public FaqVO faq_detail(int id) {
		return sql.selectOne("faq.mapper.detail", id);
	}

	@Override
	public void faq_update(FaqVO vo) {
		sql.update("faq.mapper.update", vo);
	}

	@Override
	public void faq_delete(int id) {
		sql.delete("faq.mapper.delete", id);
	}

	@Override
	public void faq_read(int id) {
		sql.update("faq.mapper.read", id);
	}

	@Override
	public FaqPage faq_list(FaqPage page) {
		// 총 글의 개수를 조회 (totlList)
		int pagecnt = sql.selectOne("faq.mapper.totalList", page);
		page.setTotallist(pagecnt); // 총 글의 수

		// 전체 글을 조회하여 List
		List<FaqVO> list = sql.selectList("faq.mapper.list", page);
		page.setList(list);
		return page;
	}

}
