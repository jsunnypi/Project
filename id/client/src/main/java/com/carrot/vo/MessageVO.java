package com.carrot.vo;

import java.util.Date;

/*
-- 메시지 정보를 저장하는 테이블
CREATE TABLE Messages (
    id NUMBER PRIMARY KEY,  -- 메세지 고유 번호
    sender_id NUMBER,                -- 보낸 회원 번호
    receiver_id NUMBER,              -- 받는 회원 번호
    date DATE DEFAULT SYSDATE,       -- 날짜
    content CLOB,                    -- 메세지 내용
    is_read NUMBER(1) DEFAULT 0,     -- 읽음 여부
    CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES Members(id),
    CONSTRAINT fk_receiver FOREIGN KEY (receiver_id) REFERENCES Members(id)
);
*/

public class MessageVO {
	private int id; // 메세지 고유 번호
	private int senderId; // 보낸 회원 번호
	private int receiverId; // 받는 회원 번호
	private Date date; // 날짜
	private String content; // 메세지 내용
	private boolean isRead; // 읽음 여부

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean getIsRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	@Override
	public String toString() {
		return "MessagesVO [id=" + id + ", senderId=" + senderId + ", receiverId=" + receiverId + ", date=" + date
				+ ", content=" + content + ", isRead=" + isRead + "]";
	}
}
