package event;

import java.util.List;

import org.springframework.stereotype.Component;

import common.PageVO;

@Component
public class EventPage extends PageVO {

	private List<EventVO> list;

	public List<EventVO> getList() {
		return list;
	}

	public void setList(List<EventVO> list) {
		this.list = list;
	}
}
