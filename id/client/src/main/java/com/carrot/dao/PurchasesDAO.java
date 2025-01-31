package com.carrot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.carrot.util.DBManager;
import com.carrot.vo.PurchaseVO;
import com.carrot.vo.PurchaseHistoryVO;

public class PurchasesDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private PurchasesDAO() {
		conn = null;
		pstmt = null;
		rs = null;
	}

	private static PurchasesDAO instance = new PurchasesDAO();

	public static PurchasesDAO getInstance() {
		return instance;
	}

	// 구매 정보 추가: 새로운 구매 내역을 데이터베이스에 저장
	public int insertPurchase(int product_id, int member_id) {
		String sql = "INSERT INTO Purchases (id, product_id, member_id) "
				+ "VALUES (seq_purchases_id.NEXTVAL, ?, ?)";
		conn = null;
		pstmt = null;
		int result = 0;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product_id);
			pstmt.setInt(2, member_id);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return result;
	}

	// 구매 내역 조회: 특정 회원의 구매 내역 목록 조회
	public List<PurchaseHistoryVO> getPurchasesByMemberId(int memberId) {
		String sql = "SELECT p.*, "
				+ "pur.product_id, "
				+ "pur.id AS purchase_id, "
				+ "pur.member_id, "
				+ "pur.purchase_date "
				+ "FROM Products p "
				+ "JOIN Purchases pur ON p.id = pur.product_id "
				+ "WHERE pur.member_id=? ORDER BY pur.purchase_date DESC";
		conn = null;
		pstmt = null;
		rs = null;
		List<PurchaseHistoryVO> purchaseHistoryList = new ArrayList<PurchaseHistoryVO>();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PurchaseHistoryVO purchaseHistoryVO = new PurchaseHistoryVO();

				purchaseHistoryVO.setAuthorId(rs.getInt("author_id"));
				purchaseHistoryVO.setTitle(rs.getString("title"));
				purchaseHistoryVO.setDescription(rs.getString("description"));
				purchaseHistoryVO.setPhotoUrl(rs.getString("photo_url"));
				purchaseHistoryVO.setPrice(rs.getInt("price"));
				purchaseHistoryVO.setCategory(rs.getInt("category"));
				purchaseHistoryVO.setViews(rs.getInt("views"));
				purchaseHistoryVO.setSold(rs.getBoolean("is_sold"));
				purchaseHistoryVO.setHidden(rs.getBoolean("is_hidden"));
				purchaseHistoryVO.setCreatedAt(rs.getDate("created_at"));
				purchaseHistoryVO.setProductId(rs.getInt("product_id"));
				purchaseHistoryVO.setPurchaseId(rs.getInt("purchase_id"));
				purchaseHistoryVO.setMemberId(rs.getInt("member_id"));
				purchaseHistoryVO.setPurchaseDate(rs.getDate("purchase_date"));

				purchaseHistoryList.add(purchaseHistoryVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return purchaseHistoryList;
	}

	// 판매 내역 조회: 특정 회원의 판매 내역 목록 조회
	public List<PurchaseHistoryVO> getSalesByMemberId(int memberId) {
		String sql = "SELECT p.*, "
				+ "pur.product_id, "
				+ "pur.id AS purchase_id, "
				+ "pur.member_id, "
				+ "pur.purchase_date "
				+ "FROM Products p "
				+ "JOIN Purchases pur ON p.id = pur.product_id "
				+ "WHERE p.author_id=? ORDER BY pur.purchase_date DESC";
		conn = null;
		pstmt = null;
		rs = null;
		List<PurchaseHistoryVO> purchaseHistoryList = new ArrayList<PurchaseHistoryVO>();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PurchaseHistoryVO purchaseHistoryVO = new PurchaseHistoryVO();

				purchaseHistoryVO.setAuthorId(rs.getInt("author_id"));
				purchaseHistoryVO.setTitle(rs.getString("title"));
				purchaseHistoryVO.setDescription(rs.getString("description"));
				purchaseHistoryVO.setPhotoUrl(rs.getString("photo_url"));
				purchaseHistoryVO.setPrice(rs.getInt("price"));
				purchaseHistoryVO.setCategory(rs.getInt("category"));
				purchaseHistoryVO.setViews(rs.getInt("views"));
				purchaseHistoryVO.setSold(rs.getBoolean("is_sold"));
				purchaseHistoryVO.setHidden(rs.getBoolean("is_hidden"));
				purchaseHistoryVO.setCreatedAt(rs.getDate("created_at"));
				purchaseHistoryVO.setProductId(rs.getInt("product_id"));
				purchaseHistoryVO.setPurchaseId(rs.getInt("purchase_id"));
				purchaseHistoryVO.setMemberId(rs.getInt("member_id"));
				purchaseHistoryVO.setPurchaseDate(rs.getDate("purchase_date"));

				purchaseHistoryList.add(purchaseHistoryVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return purchaseHistoryList;
	}

	// 구매 상세 조회: 특정 구매 건의 상세 정보 조회
	public PurchaseVO getPurchaseById(int purchaseId) {
		String sql = "SELECT * FROM Purchases WHERE id=?";
		conn = null;
		pstmt = null;
		rs = null;
		PurchaseVO purchaseVO = new PurchaseVO();
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, purchaseId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				purchaseVO.setId(rs.getInt("id"));
				purchaseVO.setProductId(rs.getInt("product_id"));
				purchaseVO.setMemberId(rs.getInt("member_id"));
				purchaseVO.setPurchaseDate(rs.getDate("purchase_date"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return purchaseVO;
	}

	// 상품별 구매 여부 확인: 특정 상품의 구매 완료 여부 확인
	public boolean checkProductPurchased(int productId) {
		String sql = "SELECT COUNT(*) FROM Purchases WHERE product_id=?";
		conn = null;
		pstmt = null;
		rs = null;
		boolean result = false;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getBoolean("COUNT(*)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return result;
	}

	// 구매 취소: 특정 구매 건 취소 처리
	public int cancelPurchase(int purchaseId) {
		String sql = "DELETE FROM Purchases WHERE id=?";
		conn = null;
		pstmt = null;
		int result = 0;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, purchaseId);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return result;
	}
}
