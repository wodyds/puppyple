package favorites;

public interface FavoritesService {
	// CRUD (Create, Read, Update, Delete)

	//좋아요 카운트
	int favorites_count (int pid);
	
	//좋아요 저장
	int favorites_insert (FavoritesVO vo);
	//좋아요 삭제
	int favorites_delete (FavoritesVO vo);
	
}
