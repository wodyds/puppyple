package board;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO implements BoardService{

	@Autowired @Qualifier("bteam") private SqlSession sql;
	
	@Override
	public int board_add(BoardVO vo) {
		return sql.insert("board.mapper.and_board_Add", vo);
	}

	@Override
	public List<BoardVO> board_list() {
		return sql.selectList("board.mapper.and_BoardList_All");
	}

	@Override
	public List<BoardVO> board_detail(int id) {
		return sql.selectList("board.mapper.and_Board_detail",id);
	}

	public int board_add_Nofile(BoardVO vo) {
		return sql.insert("board.mapper.and_board_Add_Nofile", vo);
	}

	public int board_delete(int id) {
		return sql.delete("board.mapper.and_board_Delete",id);
	}

	@Override
	public int board_Modify(BoardVO vo) {
		return sql.update("board.mapper.and_board_Modify",vo);
	}

	public int board_Modify_Nofile(BoardVO vo) {
		return sql.update("board.mapper.and_board_Modify_Nofile",vo);
	}

	public int board_CommentAdd(BoardCommentVO vo) {
		return sql.insert("board.mapper.and_board_CommentAdd",vo);
	}

	public List<BoardCommentVO> board_CommentList(int pid) {
		return sql.selectList("board.mapper.and_BoardComment_List",pid);
	}

	public List<BoardVO> board_list_free() {
		return sql.selectList("board.mapper.and_BoardList_Free");
	}
	
	public List<BoardVO> board_list_Info() {
		return sql.selectList("board.mapper.and_BoardList_Info");
	}
	
	public List<BoardVO> board_list_Gallery() {
		return sql.selectList("board.mapper.and_BoardList_Gallery");
	}
	
	public List<BoardVO> board_list_Trade() {
		return sql.selectList("board.mapper.and_BoardList_Trade");
	}

	public List<BoardVO> board_list_Notice() {
		return sql.selectList("board.mapper.and_BoardList_Notice");
	}

	public List<BoardVO> board_list_FAQ() {
		return sql.selectList("board.mapper.and_BoardList_FAQ");
	}

	public int board_comment_Delete(int id) {
		return sql.delete("board.mapper.and_board_comment_Delete",id);
	}

	public List<BoardVO> board_list_My(String member_id) {
		return sql.selectList("board.mapper.and_BoardList_My", member_id);
	}

	public List<BoardVO> board_list_Event() {
		return sql.selectList("board.mapper.and_BoardList_Event");
	}

	public int board_Modify_Deletefile(int id) {
		return sql.update("board.mapper.and_board_Modify_Deletefile",id);
	}

	public List<BoardVO> Main_board_list() {
		return sql.selectList("board.mapper.and_MainBoardList_All");
	}

	public List<BoardVO> Main_Notice_list() {
		return sql.selectList("board.mapper.and_MainBoardList_Notice");
	}

	public List<BoardVO> and_BoardList_Inquiry(String member_id) {
		return sql.selectList("board.mapper.and_BoardList_Inquiry",member_id);
	}

	public List<BoardVO> and_BoardList_Inquiry_admin() {
		return sql.selectList("board.mapper.and_BoardList_Inquiry_admin");
	}

	public int and_update_boardCount(int id) {
		return sql.update("board.mapper.and_update_boardCount",id);
	}

}
