package mypage;

import java.sql.Date;

public class MypageVO {

	private String member_id;
	private String member_pw;
	private String member_nickname;
	private String member_phone;
	private String member_email;
	private String social_type, social_email;
	private String member_kakao;
	private String member_naver;
	private int id, no;
	private String flag;
	private String board_title;
	private String board_content;
	private Date board_writedate;
	private int board_readcnt;
	private String board_filename;
	private String board_filepath;
	private String like_member_id;
	private int like_member_id_cnt;
	private int pet_id;
	private String pet_name;
	private String pet_breed;
	private String pet_age;
	private String pet_gender;
	private String pet_nuetering;
	private String pet_weight;
	private String pet_filename;
	private String pet_filepath;
	private Date member_joindate;
	private String content, writedate;
	private int member_cnt; // 회원 수
	private int pet_cnt;// 펫 수
	private int board_cnt;// 게시판 글 수
	private int boardcomment_cnt;// 댓글 수
	private int board_M_cnt;// 고객문의 수
	private int board_B_cnt;// 자유게시판 글 수
	private int board_I_cnt;// 정보공유 글 수
	private int board_G_cnt;// 갤러리 글 수
	private int board_T_cnt;// 중고거래 글 수
	private int board_N_cnt;// 공지사항 글 수
	private int board_F_cnt;// FAQ 글 수
	private int board_E_cnt;// 이벤트 글 수

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMember_pw() {
		return member_pw;
	}

	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}

	public String getMember_nickname() {
		return member_nickname;
	}

	public void setMember_nickname(String member_nickname) {
		this.member_nickname = member_nickname;
	}

	public String getMember_phone() {
		return member_phone;
	}

	public void setMember_phone(String member_phone) {
		this.member_phone = member_phone;
	}

	public String getMember_email() {
		return member_email;
	}

	public void setMember_email(String member_email) {
		this.member_email = member_email;
	}

	public String getSocial_type() {
		return social_type;
	}

	public void setSocial_type(String social_type) {
		this.social_type = social_type;
	}

	public String getSocial_email() {
		return social_email;
	}

	public void setSocial_email(String social_email) {
		this.social_email = social_email;
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

	public int getPet_id() {
		return pet_id;
	}

	public void setPet_id(int pet_id) {
		this.pet_id = pet_id;
	}

	public String getPet_name() {
		return pet_name;
	}

	public void setPet_name(String pet_name) {
		this.pet_name = pet_name;
	}

	public String getPet_breed() {
		return pet_breed;
	}

	public void setPet_breed(String pet_breed) {
		this.pet_breed = pet_breed;
	}

	public String getPet_age() {
		return pet_age;
	}

	public void setPet_age(String pet_age) {
		this.pet_age = pet_age;
	}

	public String getPet_gender() {
		return pet_gender;
	}

	public void setPet_gender(String pet_gender) {
		this.pet_gender = pet_gender;
	}

	public String getPet_nuetering() {
		return pet_nuetering;
	}

	public void setPet_nuetering(String pet_nuetering) {
		this.pet_nuetering = pet_nuetering;
	}

	public String getPet_weight() {
		return pet_weight;
	}

	public void setPet_weight(String pet_weight) {
		this.pet_weight = pet_weight;
	}

	public String getPet_filename() {
		return pet_filename;
	}

	public void setPet_filename(String pet_filename) {
		this.pet_filename = pet_filename;
	}

	public String getPet_filepath() {
		return pet_filepath;
	}

	public void setPet_filepath(String pet_filepath) {
		this.pet_filepath = pet_filepath;
	}

	public Date getMember_joindate() {
		return member_joindate;
	}

	public void setMember_joindate(Date member_joindate) {
		this.member_joindate = member_joindate;
	}

	public String getMember_kakao() {
		return member_kakao;
	}

	public void setMember_kakao(String member_kakao) {
		this.member_kakao = member_kakao;
	}

	public String getMember_naver() {
		return member_naver;
	}

	public void setMember_naver(String member_naver) {
		this.member_naver = member_naver;
	}

	public int getMember_cnt() {
		return member_cnt;
	}

	public void setMember_cnt(int member_cnt) {
		this.member_cnt = member_cnt;
	}

	public int getPet_cnt() {
		return pet_cnt;
	}

	public void setPet_cnt(int pet_cnt) {
		this.pet_cnt = pet_cnt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWritedate() {
		return writedate;
	}

	public void setWritedate(String writedate) {
		this.writedate = writedate;
	}

	public int getBoard_cnt() {
		return board_cnt;
	}

	public void setBoard_cnt(int board_cnt) {
		this.board_cnt = board_cnt;
	}

	public int getBoardcomment_cnt() {
		return boardcomment_cnt;
	}

	public void setBoardcomment_cnt(int boardcomment_cnt) {
		this.boardcomment_cnt = boardcomment_cnt;
	}

	public int getBoard_M_cnt() {
		return board_M_cnt;
	}

	public void setBoard_M_cnt(int board_M_cnt) {
		this.board_M_cnt = board_M_cnt;
	}

	public int getBoard_B_cnt() {
		return board_B_cnt;
	}

	public void setBoard_B_cnt(int board_B_cnt) {
		this.board_B_cnt = board_B_cnt;
	}

	public int getBoard_I_cnt() {
		return board_I_cnt;
	}

	public void setBoard_I_cnt(int board_I_cnt) {
		this.board_I_cnt = board_I_cnt;
	}

	public int getBoard_G_cnt() {
		return board_G_cnt;
	}

	public void setBoard_G_cnt(int board_G_cnt) {
		this.board_G_cnt = board_G_cnt;
	}

	public int getBoard_T_cnt() {
		return board_T_cnt;
	}

	public void setBoard_T_cnt(int board_T_cnt) {
		this.board_T_cnt = board_T_cnt;
	}

	public int getBoard_N_cnt() {
		return board_N_cnt;
	}

	public void setBoard_N_cnt(int board_N_cnt) {
		this.board_N_cnt = board_N_cnt;
	}

	public int getBoard_F_cnt() {
		return board_F_cnt;
	}

	public void setBoard_F_cnt(int board_F_cnt) {
		this.board_F_cnt = board_F_cnt;
	}

	public int getBoard_E_cnt() {
		return board_E_cnt;
	}

	public void setBoard_E_cnt(int board_E_cnt) {
		this.board_E_cnt = board_E_cnt;
	}

}
