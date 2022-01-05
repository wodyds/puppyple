package community;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityServiceImpl implements CommunityService {

	@Autowired private CommunityDAO dao;
	
	@Override
	public void community_insert(CommunityVO vo) {
		dao.community_insert(vo);
	}

	@Override
	public CommunityVO community_detail(HashMap<String, String> map) {
		return dao.community_detail(map);
	}

	@Override
	public void community_update(CommunityVO vo) {
		dao.community_update(vo);
	}

	@Override
	public void community_delete(int id) {
		dao.community_delete(id);

	}

	@Override
	public void community_read(int id) {
		dao.community_read(id);
	}

	@Override
	public CommunityPage community_list(CommunityPage page) {
		return dao.community_list(page);
	}

	@Override
	public int community_comment_insert(CommunityCommentVO vo) {
		return dao.community_comment_insert(vo);
	}

	@Override
	public int community_comment_update(CommunityCommentVO vo) {
		return dao.community_comment_update(vo);
	}

	@Override
	public void community_comment_delete(int id) {
		dao.community_comment_delete(id);
	}

	@Override
	public List<CommunityCommentVO> community_comment_list(int pid) {
		return dao.community_comment_list(pid);
	}

	@Override
	public CommunityPage community_list(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.community_list(map);
	}

}
