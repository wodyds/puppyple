package trade;

import java.util.List;

import org.springframework.stereotype.Component;

import common.PageVO;

@Component
public class TradePage extends PageVO {

	private List<TradeVO> list;

	public List<TradeVO> getList() {
		return list;
	}

	public void setList(List<TradeVO> list) {
		this.list = list;
	}	
}
