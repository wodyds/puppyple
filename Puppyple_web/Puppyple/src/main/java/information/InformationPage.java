package information;

import java.util.List;

import org.springframework.stereotype.Component;

import common.PageVO;

@Component
public class InformationPage extends PageVO {

	private List<InformationVO> list;

	public List<InformationVO> getList() {
		return list;
	}

	public void setList(List<InformationVO> list) {
		this.list = list;
	}	
}
