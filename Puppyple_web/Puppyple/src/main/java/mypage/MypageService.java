package mypage;

import java.util.List;

import board.BoardVO;
import community.CommunityCommentVO;
import gallery.GalleryPage;
import pet.PetVO;

public interface MypageService {

	// 펫 리스트 불러오기
	List<MypageVO> admin_pet_petList(String member_id);

	// 펫 삭제
	void admin_pet_web_petDelete(int pet_id);

	void member_info_delete(String member_id); // 내정보 삭제

	void member_info_update(MypageVO vo); // 내정보 변경 저장

	MypageVO member_info(String member_id); // 내정보 조회 (R)

	MypagePage mypage_admin_list(MypagePage page); // 회원 목록조회 - 페이지 처리된 목록 (R)

	void admin_delete(String member_id); // 회원 삭제 (D)

	MypageVO admin_detail(String member_id);// 회원정보 상세 조회 (R)

	// 내가쓴글목록조회 
	MypagePostPage mypage_post(MypagePostPage page); // 내가쓴글 목록조회 - 페이지 처리된 목록 (R)

	MypagePostPage mypage_favorites(MypagePostPage page); // 내가쓴글 목록조회 - 페이지 처리된 목록 (R)

}
