package com.carrot.vo;

import java.util.Date;

/*
-- 판매 상품 정보를 저장하는 테이블
CREATE TABLE Products (
    id NUMBER PRIMARY KEY,  -- 고유번호
    author_id NUMBER,                -- 작성자 고유번호
    title VARCHAR2(100),             -- 제목
    description CLOB,                -- 설명
    photo_url VARCHAR2(200),         -- 사진 URL
    price NUMBER(10,2),              -- 가격
    category NUMBER,                 -- 카테고리
    views NUMBER DEFAULT 0,          -- 조회수
    is_sold NUMBER(1) DEFAULT 0,     -- 판매여부
    is_hidden NUMBER(1) DEFAULT 0,   -- 숨김
    created_at DATE DEFAULT SYSDATE, -- 작성날짜
    last_bump DATE,                  -- 마지막 끌올 시간
    bump_count NUMBER DEFAULT 0,     -- 끌올 횟수
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES Members(id),
    CONSTRAINT fk_product_category FOREIGN KEY (category) REFERENCES Categories(id)
);
*/

public class ProductVO {
	private int id; // 고유번호
	private int authorId; // 작성자 고유번호
	private String title; // 제목
	private String description; // 설명
	private String photoUrl; // 사진 URL
	private double price; // 가격
	private int category; // 카테고리 (Categories 테이블의 id)
	private int views; // 조회수
	private boolean isSold; // 판매여부
	private boolean isHidden; // 숨김
	private Date createdAt; // 작성날짜
	private Date lastBump; // 마지막 끌올 시간
	private int bumpCount; // 끌올 횟수

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public boolean getIsSold() {
		return isSold;
	}

	public void setSold(boolean isSold) {
		this.isSold = isSold;
	}

	public boolean getIsHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLastBump() {
		return lastBump;
	}

	public void setLastBump(Date lastBump) {
		this.lastBump = lastBump;
	}

	public int getBumpCount() {
		return bumpCount;
	}

	public void setBumpCount(int bumpCount) {
		this.bumpCount = bumpCount;
	}

	@Override
	public String toString() {
		return "ProductsVO [id=" + id + ", authorId=" + authorId + ", title=" + title + ", description=" + description
				+ ", photoUrl=" + photoUrl + ", price=" + price + ", category=" + category + ", views=" + views
				+ ", isSold=" + isSold + ", isHidden=" + isHidden + ", createdAt=" + createdAt + ", lastBump="
				+ lastBump + ", bumpCount=" + bumpCount + "]";
	}
}
