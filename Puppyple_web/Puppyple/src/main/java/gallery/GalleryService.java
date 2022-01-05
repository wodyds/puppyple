package gallery;

import java.util.HashMap;
import java.util.List;

public interface GalleryService {
	// CRUD (Create, Read, Update, Delete)

	void gallery_insert(GalleryVO vo); // 갤러리글 신규 저장 (C)

	GalleryPage gallery_list(GalleryPage page); // 갤러리글 목록조회 - 페이지 처리된 목록 (R)

	GalleryVO gallery_detail(HashMap<String, String> map); // 갤러리글 상세 조회 (R)

	void gallery_update(GalleryVO vo); // 갤러리글 변경 저장 (U)

	void gallery_delete(int id); // 갤러리글 삭제 (D)

	void gallery_read(int id); // 갤러리글 조회시 조회수 증가 처리 (U)

	int gallery_comment_insert(GalleryCommentVO vo); // 갤러리 댓글 신규 저장 처리

	int gallery_comment_update(GalleryCommentVO vo); // 갤러리 댓글 변경 저장 처리

	void gallery_comment_delete(int id); // 갤러리 댓글 삭제 처리

	List<GalleryCommentVO> gallery_comment_list(int pid); // 갤러리 댓글 목록 조회
																												// pid : 원글의 id
}
