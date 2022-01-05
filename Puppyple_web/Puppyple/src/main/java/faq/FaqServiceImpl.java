package faq;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaqServiceImpl implements FaqService {

	@Autowired private FaqDAO dao;
	
	@Override
	public void faq_insert(FaqVO vo) {
		dao.faq_insert(vo);
	}

	@Override
	public FaqVO faq_detail(int id) {
		return dao.faq_detail(id);
	}

	@Override
	public void faq_update(FaqVO vo) {
		dao.faq_update(vo);
	}

	@Override
	public void faq_delete(int id) {
		dao.faq_delete(id);

	}

	@Override
	public void faq_read(int id) {
		dao.faq_read(id);
	}

	@Override
	public FaqPage faq_list(FaqPage page) {
		return dao.faq_list(page);
	}


}
