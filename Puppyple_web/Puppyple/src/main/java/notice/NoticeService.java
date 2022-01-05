package notice;

import java.util.List;

public interface NoticeService {
	// CRUD (Create, Read, Update, Delete)

	void notice_insert(NoticeVO vo); // 공지글 신규 저장 (C)

	NoticePage notice_list(NoticePage page); // 공지글 목록조회 - 페이지 처리된 목록 (R)

	NoticeVO notice_detail(int id); // 공지글 상세 조회 (R)

	void notice_update(NoticeVO vo); // 공지글 변경 저장 (U)

	void notice_delete(int id); // 공지글 삭제 (D)

	void notice_read(int id); // 공지글 조회시 조회수 증가 처리 (U)

}
