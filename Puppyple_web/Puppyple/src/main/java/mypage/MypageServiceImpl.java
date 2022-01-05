package mypage;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import community.CommunityCommentVO;
import gallery.GalleryPage;
import member.MemberVO;
import pet.PetVO;

@Service
public class MypageServiceImpl implements MypageService{

	@Autowired private MypageDAO dao;

	@Override
	public void member_info_delete(String member_id) {
		dao.member_info_delete(member_id);
	}

	@Override
	public void member_info_update(MypageVO vo) {
		dao.member_info_update(vo);
	}

	@Override
	public MypageVO member_info(String member_id) {
		return dao.member_info(member_id);
	}

	public void web_member_modify(MemberVO vo) {
		dao.web_member_modify(vo);
	}
	
	@Override
	public MypagePostPage mypage_post(MypagePostPage page) {
		return dao.mypage_post(page);
	}

	@Override
	public MypagePostPage mypage_favorites(MypagePostPage page) {
		return dao.mypage_favorites(page);
	}

	@Override
	public MypagePage mypage_admin_list(MypagePage page) {
		return dao.mypage_admin_list(page);
	}

	@Override
	public void admin_delete(String member_id) {
		dao.admin_delete(member_id);
	}

	@Override
	public MypageVO admin_detail(String member_id) {
		return dao.admin_detail(member_id);
	}

	@Override
	public List<MypageVO> admin_pet_petList(String member_id) {
		return dao.admin_pet_petList(member_id);
	}
	@Override
	public void admin_pet_web_petDelete(int pet_id) {
		dao.admin_pet_web_petDelete(pet_id);
		
	}


}
