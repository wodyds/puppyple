package inquiry;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InquiryServiceImpl implements InquiryService {

	@Autowired
	private InquiryDAO dao;

	@Override
	public void inquiry_insert(InquiryVO vo) {
		dao.inquiry_insert(vo);
	}

	@Override
	public InquiryVO inquiry_detail(HashMap<String, String> map) {
		return dao.inquiry_detail(map);
	}

	@Override
	public void inquiry_update(InquiryVO vo) {
		dao.inquiry_update(vo);
	}

	@Override
	public void inquiry_delete(int id) {
		dao.inquiry_delete(id);

	}

	@Override
	public InquiryPage inquiry_list(InquiryPage page) {
		return dao.inquiry_list(page);
	}

	@Override
	public int inquiry_comment_insert(InquiryCommentVO vo) {
		return dao.inquiry_comment_insert(vo);
	}

	@Override
	public int inquiry_comment_update(InquiryCommentVO vo) {
		return dao.inquiry_comment_update(vo);
	}

	@Override
	public void inquiry_comment_delete(int id) {
		dao.inquiry_comment_delete(id);
	}

	@Override
	public List<InquiryCommentVO> inquiry_comment_list(int pid) {
		return dao.inquiry_comment_list(pid);
	}

}
