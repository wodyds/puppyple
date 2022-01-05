package event;

import java.util.List;

public interface EventService {
	// CRUD (Create, Read, Update, Delete)

	EventPage event_list(EventPage page); // 이벤트글 목록조회 - 페이지 처리된 목록 (R)

}
