package board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO dao;

	@Override
	public int board_add(BoardVO vo) {
		return dao.board_add(vo);
	}

	@Override
	public int board_Modify(BoardVO vo) {
		return dao.board_Modify(vo);
	}

	@Override
	public List<BoardVO> board_list() {
		return dao.board_list();
	}

	@Override
	public List<BoardVO> board_detail(int id) {
		return dao.board_detail(id);
	}

	public int board_add_Nofile(BoardVO vo) {
		return dao.board_add_Nofile(vo);
	}

	public int and_board_Delete(int id) {
		return dao.board_delete(id);
	}

	public int board_Modify_Nofile(BoardVO vo) {
		return dao.board_Modify_Nofile(vo);
	}

	public int board_CommentAdd(BoardCommentVO vo) {
		return dao.board_CommentAdd(vo);
	}

	public List<BoardCommentVO> BoardComment_List(int pid) {
		return dao.board_CommentList(pid);
	}

	public List<BoardVO> board_list_free() {
		return dao.board_list_free();
	}

	public List<BoardVO> board_list_Info() {
		return dao.board_list_Info();
	}

	public List<BoardVO> board_list_Gallery() {
		return dao.board_list_Gallery();
	}

	public List<BoardVO> board_list_Trade() {
		return dao.board_list_Trade();
	}

	public List<BoardVO> board_list_Notice() {
		return dao.board_list_Notice();
	}

	public List<BoardVO> board_list_FAQ() {
		return dao.board_list_FAQ();
	}

	public int and_board_comment_Delete(int id) {
		return dao.board_comment_Delete(id);
	}

	public List<BoardVO> board_list_My(String member_id) {
		return dao.board_list_My(member_id);
	}

	public List<BoardVO> board_list_Event() {
		return dao.board_list_Event();
	}

	public int board_Modify_Deletefile(int id) {
		return dao.board_Modify_Deletefile(id);
	}

	public List<BoardVO> Main_board_list() {
		return dao.Main_board_list();
	}

	public List<BoardVO> Main_Notice_list() {
		return dao.Main_Notice_list();
	}

	public List<BoardVO> and_BoardList_Inquiry(String member_id) {
		return dao.and_BoardList_Inquiry(member_id);
	}

	public List<BoardVO> and_BoardList_Inquiry_Admin() {
		return dao.and_BoardList_Inquiry_admin();
	}

	public int and_update_boardCount(int id) {
		return dao.and_update_boardCount(id);
	}

}
