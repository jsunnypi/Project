package com.carrot.vo;

import java.util.Date;

/*
-- 카테고리 정보를 저장하는 테이블
CREATE TABLE Categories (
    id NUMBER PRIMARY KEY,           -- 카테고리 고유 번호
    name VARCHAR2(50) NOT NULL,      -- 카테고리 이름
    parent_id NUMBER DEFAULT 0,      -- 상위 카테고리 번호
    description VARCHAR2(200),       -- 카테고리 설명
    created_date DATE DEFAULT SYSDATE, -- 생성 날짜
    CONSTRAINT fk_parent_category FOREIGN KEY (parent_id) REFERENCES Categories(id)
);
*/

public class CategoryVO {
	private int id; // 카테고리 고유 번호
	private String name; // 카테고리 이름
	private int parentId; // 상위 카테고리 번호
	private String description; // 카테고리 설명
	private Date createdAt; // 생성 날짜

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "CategoriesVO [id=" + id + ", name=" + name + ", parentId=" + parentId + ", description=" + description
				+ ", createdAt=" + createdAt + "]";
	}
}
