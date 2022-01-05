package event;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class EventDAO implements EventService {

	@Autowired
	@Qualifier("bteam")
	private SqlSession sql;

	@Override
	public EventPage event_list(EventPage page) {
		// 총 글의 개수를 조회 (totlList)
		int pagecnt = sql.selectOne("event.mapper.totalList", page);
		page.setTotallist(pagecnt); // 총 글의 수

		// 전체 글을 조회하여 List
		List<EventVO> list = sql.selectList("event.mapper.list", page);
		page.setList(list);
		return page;
	}

}
