package trade;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gallery.GalleryVO;

@Service
public class TradeServiceImpl implements TradeService {

	@Autowired
	private TradeDAO dao;

	@Override
	public void trade_insert(TradeVO vo) {
		dao.trade_insert(vo);
	}

	@Override
	public TradeVO trade_detail(HashMap<String, String> map) {
		return dao.trade_detail(map);
	}

	@Override
	public void trade_update(TradeVO vo) {
		dao.trade_update(vo);
	}

	@Override
	public void trade_delete(int id) {
		dao.trade_delete(id);

	}

	@Override
	public void trade_read(int id) {
		dao.trade_read(id);
	}

	@Override
	public TradePage trade_list(TradePage page) {
		return dao.trade_list(page);
	}

	@Override
	public int trade_comment_insert(TradeCommentVO vo) {
		return dao.trade_comment_insert(vo);
	}

	@Override
	public int trade_comment_update(TradeCommentVO vo) {
		return dao.trade_comment_update(vo);
	}

	@Override
	public void trade_comment_delete(int id) {
		dao.trade_comment_delete(id);
	}

	@Override
	public List<TradeCommentVO> trade_comment_list(int pid) {
		return dao.trade_comment_list(pid);
	}

}
