package gallery;

import java.sql.Date;

public class GalleryVO {

	private int id, no;
	private String flag;
	private String member_id;
	private String board_title;
	private String board_content;
	private Date board_writedate;
	private int board_readcnt;
	private String board_filename;
	private String board_filepath;
	private String member_nickname;
	private String like_member_id;
	private int like_member_id_cnt;

	public String getLike_member_id() {
		return like_member_id;
	}

	public void setLike_member_id(String like_member_id) {
		this.like_member_id = like_member_id;
	}

	public int getLike_member_id_cnt() {
		return like_member_id_cnt;
	}

	public void setLike_member_id_cnt(int like_member_id_cnt) {
		this.like_member_id_cnt = like_member_id_cnt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getBoard_title() {
		return board_title;
	}

	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}

	public String getBoard_content() {
		return board_content;
	}

	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}

	public Date getBoard_writedate() {
		return board_writedate;
	}

	public void setBoard_writedate(Date board_writedate) {
		this.board_writedate = board_writedate;
	}

	public int getBoard_readcnt() {
		return board_readcnt;
	}

	public void setBoard_readcnt(int board_readcnt) {
		this.board_readcnt = board_readcnt;
	}

	public String getBoard_filename() {
		return board_filename;
	}

	public void setBoard_filename(String board_filename) {
		this.board_filename = board_filename;
	}

	public String getBoard_filepath() {
		return board_filepath;
	}

	public void setBoard_filepath(String board_filepath) {
		this.board_filepath = board_filepath;
	}

	public String getMember_nickname() {
		return member_nickname;
	}

	public void setMember_nickname(String member_nickname) {
		this.member_nickname = member_nickname;
	}

}
