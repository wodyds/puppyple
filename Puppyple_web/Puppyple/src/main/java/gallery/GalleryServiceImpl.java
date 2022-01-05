package gallery;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import community.CommunityVO;

@Service
public class GalleryServiceImpl implements GalleryService {

	@Autowired
	private GalleryDAO dao;

	@Override
	public void gallery_insert(GalleryVO vo) {
		dao.gallery_insert(vo);
	}

	@Override
	public GalleryVO gallery_detail(HashMap<String, String> map) {
		return dao.gallery_detail(map);
	}

	@Override
	public void gallery_update(GalleryVO vo) {
		dao.gallery_update(vo);
	}

	@Override
	public void gallery_delete(int id) {
		dao.gallery_delete(id);

	}

	@Override
	public void gallery_read(int id) {
		dao.gallery_read(id);
	}

	@Override
	public GalleryPage gallery_list(GalleryPage page) {
		return dao.gallery_list(page);
	}

	@Override
	public int gallery_comment_insert(GalleryCommentVO vo) {
		return dao.gallery_comment_insert(vo);
	}

	@Override
	public int gallery_comment_update(GalleryCommentVO vo) {
		return dao.gallery_comment_update(vo);
	}

	@Override
	public void gallery_comment_delete(int id) {
		dao.gallery_comment_delete(id);
	}

	@Override
	public List<GalleryCommentVO> gallery_comment_list(int pid) {
		return dao.gallery_comment_list(pid);
	}

}
