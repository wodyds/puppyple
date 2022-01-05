package community;

import java.util.HashMap;
import java.util.List;

public interface CommunityService {
	// CRUD (Create, Read, Update, Delete)

	void community_insert(CommunityVO vo); // 자유게시판글 신규 저장 (C)

	CommunityPage community_list(CommunityPage page); // 자유게시판글 목록조회 - 페이지 처리된 목록 (R)

	CommunityPage community_list(HashMap<String, Object> map); // 자유게시판글 목록조회 - 페이지 처리된 목록 (R)

	CommunityVO community_detail(HashMap<String, String> map); // 자유게시판글 상세 조회 (R)

	void community_update(CommunityVO vo); // 자유게시판글 변경 저장 (U)

	void community_delete(int id); // 자유게시판글 삭제 (D)

	void community_read(int id); // 자유게시판글 조회시 조회수 증가 처리 (U)

	int community_comment_insert(CommunityCommentVO vo); // 자유게시판 댓글 신규 저장 처리

	int community_comment_update(CommunityCommentVO vo); // 자유게시판 댓글 변경 저장 처리

	void community_comment_delete(int id); // 자유게시판 댓글 삭제 처리

	List<CommunityCommentVO> community_comment_list(int pid); // 자유게시판 댓글 목록 조회
																																  // pid : 원글의 id
}
