package inquiry;

import java.util.List;

import org.springframework.stereotype.Component;

import common.PageVO;

@Component
public class InquiryPage extends PageVO {

	private List<InquiryVO> list;

	public List<InquiryVO> getList() {
		return list;
	}

	public void setList(List<InquiryVO> list) {
		this.list = list;
	}	
}
