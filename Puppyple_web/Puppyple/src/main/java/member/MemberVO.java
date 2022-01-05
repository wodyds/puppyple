package member;

import java.sql.Date;

public class MemberVO {

	private String member_id;
	private String member_pw;
	private String member_nickname;
	private String member_phone;
	private String member_email;
	private String member_kakao;
	private String member_naver;
	private Date member_joindate;
	private String member_admin;
	private String social_type, social_email;

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

	public Date getMember_joindate() {
		return member_joindate;
	}

	public void setMember_joindate(Date member_joindate) {
		this.member_joindate = member_joindate;
	}

	public String getMember_admin() {
		return member_admin;
	}

	public void setMember_admin(String member_admin) {
		this.member_admin = member_admin;
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

}
