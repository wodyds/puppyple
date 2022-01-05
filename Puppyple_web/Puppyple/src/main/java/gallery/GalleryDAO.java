package gallery;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import community.CommunityVO;

@Repository
public class GalleryDAO implements GalleryService {

	@Autowired
	@Qualifier("bteam")
	private SqlSession sql;

	@Override
	public void gallery_insert(GalleryVO vo) {
		sql.insert("gallery.mapper.insert", vo);
	}

	@Override
	public GalleryVO gallery_detail(HashMap<String, String> map) {
		return sql.selectOne("gallery.mapper.detail", map);
	}

	@Override
	public void gallery_update(GalleryVO vo) {
		sql.update("gallery.mapper.update", vo);
	}

	@Override
	public void gallery_delete(int id) {
		sql.delete("gallery.mapper.delete", id);
	}

	@Override
	public void gallery_read(int id) {
		sql.update("gallery.mapper.read", id);
	}

	@Override
	public GalleryPage gallery_list(GalleryPage page) {
		// 총 글의 개수를 조회 (totlList)
		int pagecnt = sql.selectOne("gallery.mapper.totalList", page);
		page.setTotallist(pagecnt); // 총 글의 수

		// 전체 글을 조회하여 List
		List<GalleryVO> list = sql.selectList("gallery.mapper.list", page);
		page.setList(list);
		return page;
	}

	@Override
	public int gallery_comment_insert(GalleryCommentVO vo) {
		return sql.insert("gallery.mapper.comment_insert", vo);
	}

	@Override
	public int gallery_comment_update(GalleryCommentVO vo) {
		return sql.update("gallery.mapper.comment_update", vo);
	}

	@Override
	public void gallery_comment_delete(int id) {
		sql.delete("gallery.mapper.comment_delete", id);
	}

	@Override
	public List<GalleryCommentVO> gallery_comment_list(int pid) {
		return sql.selectList("gallery.mapper.comment_list", pid);
	}

}
