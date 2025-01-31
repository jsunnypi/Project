package com.carrot.vo;

import java.util.Date;

/*
-- 구매 정보를 저장하는 테이블
CREATE TABLE Purchases (
    id NUMBER PRIMARY KEY,  -- 구매 고유 번호
    product_id NUMBER,               -- 판매글 번호
    member_id NUMBER,                -- 회원 번호
    purchase_date DATE DEFAULT SYSDATE,  -- 구매 날짜
    CONSTRAINT fk_product_pur FOREIGN KEY (product_id) REFERENCES Products(id),
    CONSTRAINT fk_member_pur FOREIGN KEY (member_id) REFERENCES Members(id)
);
*/

public class PurchaseVO {
	private int id; // 구매 고유 번호
	private int productId; // 판매글 번호
	private int memberId; // 회원 번호
	private Date purchaseDate; // 구매 날짜

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

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	@Override
	public String toString() {
		return "PurchasesVO [id=" + id + ", productId=" + productId + ", memberId=" + memberId + ", purchaseDate="
				+ purchaseDate + "]";
	}

}
