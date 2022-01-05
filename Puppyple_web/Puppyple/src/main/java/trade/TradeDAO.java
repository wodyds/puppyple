package trade;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class TradeDAO implements TradeService {

	@Autowired
	@Qualifier("bteam")
	private SqlSession sql;

	@Override
	public void trade_insert(TradeVO vo) {
		sql.insert("trade.mapper.insert", vo);
	}

	@Override
	public TradeVO trade_detail(HashMap<String, String> map) {
		return sql.selectOne("trade.mapper.detail", map);
	}

	@Override
	public void trade_update(TradeVO vo) {
		sql.update("trade.mapper.update", vo);
	}

	@Override
	public void trade_delete(int id) {
		sql.delete("trade.mapper.delete", id);
	}

	@Override
	public void trade_read(int id) {
		sql.update("trade.mapper.read", id);
	}

	@Override
	public TradePage trade_list(TradePage page) {
		// 총 글의 개수를 조회 (totlList)
		int pagecnt = sql.selectOne("trade.mapper.totalList", page);
		page.setTotallist(pagecnt); // 총 글의 수

		// 전체 글을 조회하여 List
		List<TradeVO> list = sql.selectList("trade.mapper.list", page);
		page.setList(list);
		return page;
	}

	@Override
	public int trade_comment_insert(TradeCommentVO vo) {
		return sql.insert("trade.mapper.comment_insert", vo);
	}

	@Override
	public int trade_comment_update(TradeCommentVO vo) {
		return sql.update("trade.mapper.comment_update", vo);
	}

	@Override
	public void trade_comment_delete(int id) {
		sql.delete("trade.mapper.comment_delete", id);
	}

	@Override
	public List<TradeCommentVO> trade_comment_list(int pid) {
		return sql.selectList("trade.mapper.comment_list", pid);
	}

}
