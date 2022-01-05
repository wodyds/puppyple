package com.example.DTO;

import java.io.Serializable;

public class BoardDTO implements Serializable {

    private int id,board_readcnt;
    private String flag,member_id,board_title,board_content,
            board_writedate,board_filename,board_filepath,trade_category,trade_price,member_nickname;

    //게시판 추가할 때 모든 정보를 저장
    public BoardDTO(int id, int board_readcnt, String flag, String member_id,
                    String board_title, String board_content, String board_writedate,
                    String board_filename, String board_filepath, String trade_category,
                    String trade_price, String member_nickname) {
        this.id = id;
        this.board_readcnt = board_readcnt;
        this.flag = flag;
        this.member_id = member_id;
        this.board_title = board_title;
        this.board_content = board_content;
        this.board_writedate = board_writedate;
        this.board_filename = board_filename;
        this.board_filepath = board_filepath;
        this.trade_category = trade_category;
        this.trade_price = trade_price;
        this.member_nickname = member_nickname;
    }

    public String getMember_nickname() {
        return member_nickname;
    }

    public void setMember_nickname(String member_nickname) {
        this.member_nickname = member_nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoard_readcnt() {
        return board_readcnt;
    }

    public void setBoard_readcnt(int board_readcnt) {
        this.board_readcnt = board_readcnt;
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

    public String getBoard_writedate() {
        return board_writedate;
    }

    public void setBoard_writedate(String board_writedate) {
        this.board_writedate = board_writedate;
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

    public String getTrade_category() {
        return trade_category;
    }

    public void setTrade_category(String trade_category) {
        this.trade_category = trade_category;
    }

    public String getTrade_price() {
        return trade_price;
    }

    public void setTrade_price(String trade_price) {
        this.trade_price = trade_price;
    }
}
