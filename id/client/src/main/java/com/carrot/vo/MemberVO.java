package com.carrot.vo;

import java.util.Date;

/*
-- 회원 정보를 저장하는 테이블
CREATE TABLE Members (
    id NUMBER PRIMARY KEY,  -- 고유번호
    userid VARCHAR2(50),           -- 아이디
    nickname VARCHAR2(50),           -- 닉네임
    name VARCHAR2(50),               -- 이름
    password VARCHAR2(100),          -- 비밀번호
    phone VARCHAR2(20),              -- 전화번호
    email VARCHAR2(100),             -- 이메일
    address VARCHAR2(200),           -- 주소
    registration_date DATE DEFAULT SYSDATE,  -- 회원가입 날짜
    grade NUMBER(2) DEFAULT 0        -- 등급
);
*/

public class MemberVO {
	private int id; // 고유번호
	private String userid; // 아이디
	private String nickname; // 닉네임
	private String name; // 이름
	private String password; // 비밀번호
	private String phone; // 전화번호
	private String email; // 이메일
	private String address; // 주소
	private Date registrationDate; // 회원가입 날짜
	private int grade; // 등급

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getRegistration_date() {
		return registrationDate;
	}

	public void setRegistration_date(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "MembersVO [id=" + id + ", userid=" + userid + ", nickname=" + nickname + ", name=" + name
				+ ", password=" + password + ", phone=" + phone + ", email=" + email + ", address=" + address
				+ ", registrationDate=" + registrationDate + ", grade=" + grade + "]";
	}
}
