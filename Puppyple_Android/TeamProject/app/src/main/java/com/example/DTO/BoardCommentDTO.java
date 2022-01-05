package com.example.DTO;

import java.io.Serializable;

public class BoardCommentDTO implements Serializable {
    int id;
    String pid,flag,member_id,content,writedate,member_nickname;

    public BoardCommentDTO(int id, String pid, String member_id, String content, String writedate, String member_nickname){
        this.id = id;
        this.pid = pid;
        this.member_id = member_id;
        this.content = content;
        this.writedate = writedate;
        this.member_nickname = member_nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getMember_nickname() {
        return member_nickname;
    }

    public void setMember_nickname(String member_nickname) {
        this.member_nickname = member_nickname;
    }
}
