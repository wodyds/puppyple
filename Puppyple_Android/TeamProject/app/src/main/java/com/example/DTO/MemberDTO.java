package com.example.DTO;

import java.io.Serializable;

public class MemberDTO implements Serializable {

    private String member_id, member_pw, member_nickname,
                    member_phone, member_email, member_kakao, member_naver,
                    member_joindate, member_admin;

    //로그인시 암호없이 멤버정보를 가져올떄
    public MemberDTO(String member_id, String member_nickname, String member_phone, String member_email, String member_kakao, String member_naver, String member_joindate, String member_admin) {
        this.member_id = member_id;
        this.member_nickname = member_nickname;
        this.member_phone = member_phone;
        this.member_email = member_email;
        this.member_kakao = member_kakao;
        this.member_naver = member_naver;
        this.member_joindate = member_joindate;
        this.member_admin = member_admin;
    }

    //회원가입할때 모든 정보를 저장하기 위해
    public MemberDTO(String member_id, String member_pw, String member_nickname, String member_phone, String member_email, String member_kakao, String member_naver, String member_joindate, String member_admin) {
        this.member_id = member_id;
        this.member_pw = member_pw;
        this.member_nickname = member_nickname;
        this.member_phone = member_phone;
        this.member_email = member_email;
        this.member_kakao = member_kakao;
        this.member_naver = member_naver;
        this.member_joindate = member_joindate;
        this.member_admin = member_admin;
    }

    //카카오 회원가입 정보 저장
    public MemberDTO(String member_id,String member_pw, String member_email, String member_nickname, String member_kakao, String member_admin){
        this.member_id = member_id;
        this.member_pw = member_pw;
        this.member_email = member_email;
        this.member_nickname = member_nickname;
        this.member_kakao = member_kakao;
        this.member_admin = member_admin;
    }

    //네이버 회원가입 정보 저장
    public MemberDTO(String member_id,String member_pw, String member_email, String member_nickname, String member_naver){
        this.member_id = member_id;
        this.member_pw = member_pw;
        this.member_email = member_email;
        this.member_nickname = member_nickname;
        this.member_naver = member_naver;
    }


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

    public String getMember_joindate() {
        return member_joindate;
    }

    public void setMember_joindate(String member_joindate) {
        this.member_joindate = member_joindate;
    }

    public String getMember_admin() {
        return member_admin;
    }

    public void setMember_admin(String member_admin) {
        this.member_admin = member_admin;
    }
}
