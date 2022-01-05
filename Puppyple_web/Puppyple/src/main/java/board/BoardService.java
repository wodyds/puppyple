package board;

import java.util.List;

public interface BoardService {

	int board_add(BoardVO vo);

	List<BoardVO> board_list();

	List<BoardVO> board_detail(int id);

	int board_Modify(BoardVO vo);
}
