package trade;

import java.util.HashMap;
import java.util.List;

public interface TradeService {
	// CRUD (Create, Read, Update, Delete)

	void trade_insert(TradeVO vo); // 중고거래글 신규 저장 (C)

	TradePage trade_list(TradePage page); // 중고거래글 목록조회 - 페이지 처리된 목록 (R)

	TradeVO trade_detail(HashMap<String, String> map); // 중고거래글 상세 조회 (R)

	void trade_update(TradeVO vo); // 중고거래글 변경 저장 (U)

	void trade_delete(int id); // 중고거래글 삭제 (D)

	void trade_read(int id); // 중고거래글 조회시 조회수 증가 처리 (U)

	int trade_comment_insert(TradeCommentVO vo); // 중고거래 댓글 신규 저장 처리

	int trade_comment_update(TradeCommentVO vo); // 중고거래 댓글 변경 저장 처리

	void trade_comment_delete(int id); // 중고거래 댓글 삭제 처리

	List<TradeCommentVO> trade_comment_list(int pid); // 중고거래 댓글 목록 조회
																											// pid : 원글의 id
}
