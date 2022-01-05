package information;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InformationServiceImpl implements InformationService {

	@Autowired
	private InformationDAO dao;

	@Override
	public void information_insert(InformationVO vo) {
		dao.information_insert(vo);
	}

	@Override
	public InformationVO information_detail(HashMap<String, String> map) {
		return dao.information_detail(map);
	}

	@Override
	public void information_update(InformationVO vo) {
		dao.information_update(vo);
	}

	@Override
	public void information_delete(int id) {
		dao.information_delete(id);

	}

	@Override
	public void information_read(int id) {
		dao.information_read(id);
	}

	@Override
	public InformationPage information_list(InformationPage page) {
		return dao.information_list(page);
	}

	@Override
	public int information_comment_insert(InformationCommentVO vo) {
		return dao.information_comment_insert(vo);
	}

	@Override
	public int information_comment_update(InformationCommentVO vo) {
		return dao.information_comment_update(vo);
	}

	@Override
	public void information_comment_delete(int id) {
		dao.information_comment_delete(id);
	}

	@Override
	public List<InformationCommentVO> information_comment_list(int pid) {
		return dao.information_comment_list(pid);
	}

}
