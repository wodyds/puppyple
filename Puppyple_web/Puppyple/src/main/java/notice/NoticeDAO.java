package notice;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeDAO implements NoticeService {

	@Autowired
	@Qualifier("bteam")
	private SqlSession sql;

	@Override
	public void notice_insert(NoticeVO vo) {
		sql.insert("notice.mapper.insert", vo);
	}

	@Override
	public NoticeVO notice_detail(int id) {
		return sql.selectOne("notice.mapper.detail", id);
	}

	@Override
	public void notice_update(NoticeVO vo) {
		sql.update("notice.mapper.update", vo);
	}

	@Override
	public void notice_delete(int id) {
		sql.delete("notice.mapper.delete", id);
	}

	@Override
	public void notice_read(int id) {
		sql.update("notice.mapper.read", id);
	}

	@Override
	public NoticePage notice_list(NoticePage page) {
		// 총 글의 개수를 조회 (totlList)
		int pagecnt = sql.selectOne("notice.mapper.totalList", page);
		page.setTotallist(pagecnt); // 총 글의 수

		// 전체 글을 조회하여 List
		List<NoticeVO> list = sql.selectList("notice.mapper.list", page);
		page.setList(list);
		return page;
	}

}
