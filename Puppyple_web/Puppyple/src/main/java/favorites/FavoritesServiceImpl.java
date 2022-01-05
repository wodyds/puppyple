package favorites;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoritesServiceImpl implements FavoritesService {

	@Autowired
	private FavoritesDAO dao;

	@Override
	public int favorites_insert(FavoritesVO vo) {
		return dao.favorites_insert(vo);

	}

	@Override
	public int favorites_delete(FavoritesVO vo) {
		return dao.favorites_delete(vo);
	}

	// favorites_count
	@Override
	public int favorites_count(int pid) {
		return dao.favorites_count(pid);
	}

}
