package com.carrot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.carrot.util.DBManager;
import com.carrot.vo.FavoriteVO;
import com.carrot.vo.ProductVO;

public class FavoritesDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private FavoritesDAO() {
		conn = null;
		pstmt = null;
		rs = null;
	}

	private static FavoritesDAO instance = new FavoritesDAO();

	public static FavoritesDAO getInstance() {
		return instance;
	}

	// 관심 상품 추가: 회원이 관심 있는 상품을 관심 목록에 추가
	public int insertFavorite(FavoriteVO favoriteVO) {
		String sql = "INSERT INTO Favorites (id, product_id, member_id) "
				+ "VALUES (seq_favorites_id.NEXTVAL, ?, ?)";
		conn = null;
		pstmt = null;
		int result = 0;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, favoriteVO.getProductId());
			pstmt.setInt(2, favoriteVO.getMemberId());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return result;
	}

	// 관심 상품 목록 조회: 특정 회원의 관심 상품 목록 조회
	public List<ProductVO> getFavoritesByMemberId(int memberId) {
		String sql = "SELECT p.* FROM Products p " + "JOIN Favorites f ON p.id = f.product_id "
				+ "WHERE f.member_id=? ORDER BY f.created_date DESC";
		conn = null;
		pstmt = null;
		rs = null;
		List<ProductVO> favoriteProductList = new ArrayList<ProductVO>();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductVO productVO = new ProductVO();
				productVO.setId(rs.getInt("id"));
				productVO.setAuthorId(rs.getInt("author_id"));
				productVO.setTitle(rs.getString("title"));
				productVO.setDescription(rs.getString("description"));
				productVO.setPhotoUrl(rs.getString("photo_url"));
				productVO.setPrice(rs.getInt("price"));
				productVO.setCategory(rs.getInt("category"));
				productVO.setViews(rs.getInt("views"));
				productVO.setSold(rs.getBoolean("is_sold"));
				productVO.setHidden(rs.getBoolean("is_hidden"));
				productVO.setCreatedAt(rs.getDate("created_at"));
				productVO.setLastBump(rs.getDate("last_bump"));
				productVO.setBumpCount(rs.getInt("bump_count"));

				favoriteProductList.add(productVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return favoriteProductList;
	}

	// 관심 상품 체크: 특정 상품이 관심 목록에 있는지 확인
	public boolean checkFavorite(int memberId, int productId) {
		String sql = "SELECT COUNT(*) FROM Favorites WHERE member_id=? AND product_id=?";
		conn = null;
		pstmt = null;
		rs = null;
		boolean result = false;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberId);
			pstmt.setInt(2, productId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getBoolean("COUNT(*)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return result;
	}

	// 상품별 관심 수 조회: 특정 상품의 관심 등록 수 조회
	public int getFavoriteCount(int productId) {
		String sql = "SELECT COUNT(*) FROM Favorites WHERE product_id=?";
		conn = null;
		pstmt = null;
		rs = null;
		int result = 0;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("COUNT(*)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return result;
	}

	// 특정 회원의 모든 관심상품 삭제
	public int deleteAllFavorite(int memberId) {
		String sql = "DELETE FROM Favorites WHERE id=?";
		conn = null;
		pstmt = null;
		int result = 0;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberId);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return result;
	}

	// 관심 상품 삭제: 관심 목록에서 특정 상품 제거
	public int deleteFavorite(int memberId, int productId) {
		String sql = "DELETE FROM Favorites WHERE member_id=? AND product_id=?";
		conn = null;
		pstmt = null;
		int result = 0;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberId);
			pstmt.setInt(2, productId);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return result;
	}
}
