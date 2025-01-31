package com.carrot.vo;

import java.util.Date;

/*
-- 당근(포인트) 정보를 저장하는 테이블
CREATE TABLE Carrots (
    id NUMBER PRIMARY KEY,  -- 당근 고유 번호
    member_id NUMBER,                -- 회원 번호
    golden_carrots NUMBER,           -- 황금 당근
    rotten_carrots NUMBER,           -- 썩은 당근
    date DATE DEFAULT SYSDATE,       -- 날짜
    CONSTRAINT fk_member_car FOREIGN KEY (member_id) REFERENCES Members(id)
);
*/
public class CarrotVO {
	private int id; // 당근 고유 번호
	private int memberId; // 회원 번호
	private int goldenCarrots; // 황금 당근 수량
	private int rottenCarrots; // 썩은 당근 수량
	private Date createdDate; // 날짜

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getGoldenCarrots() {
		return goldenCarrots;
	}

	public void setGoldenCarrots(int goldenCarrots) {
		this.goldenCarrots = goldenCarrots;
	}

	public int getRottenCarrots() {
		return rottenCarrots;
	}

	public void setRottenCarrots(int rottenCarrots) {
		this.rottenCarrots = rottenCarrots;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "CarrotsVO [id=" + id + ", memberId=" + memberId + ", goldenCarrots=" + goldenCarrots
				+ ", rottenCarrots=" + rottenCarrots + ", date=" + createdDate + "]";
	}

}
