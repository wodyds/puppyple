package event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

	@Autowired private EventDAO dao;

	@Override
	public EventPage event_list(EventPage page) {
		return dao.event_list(page);
	}


}
