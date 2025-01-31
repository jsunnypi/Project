package com.carrot.vo;

import java.util.Date;

/*
-- 관심 상품 정보를 저장하는 테이블
CREATE TABLE Favorites (
    id NUMBER PRIMARY KEY,  -- 관심 고유 번호
    product_id NUMBER,               -- 판매글 번호
    member_id NUMBER,                -- 회원 번호
    date DATE DEFAULT SYSDATE,       -- 날짜
    CONSTRAINT fk_product_fav FOREIGN KEY (product_id) REFERENCES Products(id),
    CONSTRAINT fk_member_fav FOREIGN KEY (member_id) REFERENCES Members(id)
);
*/

public class FavoriteVO {
	private int id; // 관심 고유 번호
	private int productId; // 판매글 번호
	private int memberId; // 회원 번호
	private Date date; // 날짜

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "FavoritesVO [id=" + id + ", productId=" + productId + ", memberId=" + memberId + ", date=" + date + "]";
	}

}
