package mypage;

import java.util.List;

import org.springframework.stereotype.Component;

import common.PageVO;

@Component
public class MypagePage extends PageVO {

	private List<MypageVO> list;

	public List<MypageVO> getList() {
		return list;
	}

	public void setList(List<MypageVO> list) {
		this.list = list;
	}	
}
