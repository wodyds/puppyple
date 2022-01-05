package favorites;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class FavoritesDAO implements FavoritesService{
	
	@Autowired @Qualifier("bteam") private SqlSession sql;

	@Override
	public int favorites_insert(FavoritesVO vo) {
		return sql.insert("favorites.mapper.favorites_insert", vo);
	}

	@Override
	public int favorites_delete(FavoritesVO vo) {
		return sql.delete("favorites.mapper.favorites_delete", vo);
	}
	
	//favorites_count
	@Override
	public int favorites_count(int pid) {
		return sql.selectOne("favorites.mapper.favorites_count", pid);
	}
}
