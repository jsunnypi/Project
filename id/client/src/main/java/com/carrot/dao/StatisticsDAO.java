package com.carrot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class StatisticsDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private StatisticsDAO() {
		conn = null;
		pstmt = null;
		rs = null;
	}

	private static StatisticsDAO instance = new StatisticsDAO();

	public static StatisticsDAO getInstance() {
		return instance;
	}

	// 특정 상품의 조회수 증가
	public void incrementProductViews(int productId) {
	}
}
