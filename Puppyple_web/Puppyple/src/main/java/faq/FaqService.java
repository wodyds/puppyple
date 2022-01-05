package faq;

import java.util.List;

public interface FaqService {
	// CRUD (Create, Read, Update, Delete)
	
	void faq_insert(FaqVO vo);	// FAQ글 신규 저장 (C)
	FaqPage faq_list(FaqPage page); // FAQ글 목록조회 - 페이지 처리된 목록 (R)
	FaqVO faq_detail(int id);			// FAQ글 상세 조회 (R)	
	void faq_update(FaqVO vo);	// FAQ글 변경 저장 (U)
	void faq_delete(int id);			// FAQ글 삭제 (D)
	void faq_read(int id);			// FAQ글 조회시 조회수 증가 처리 (U)
	
}
