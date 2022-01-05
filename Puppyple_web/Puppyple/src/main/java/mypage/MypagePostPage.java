package mypage;

import java.util.List;

import org.springframework.stereotype.Component;

import common.PageVO;
import community.CommunityVO;

@Component
public class MypagePostPage extends PageVO {

	private List<CommunityVO> list;

	private String member_id;

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public List<CommunityVO> getList() {
		return list;
	}

	public void setList(List<CommunityVO> list) {
		this.list = list;
	}
}
