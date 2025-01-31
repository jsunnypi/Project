package com.carrot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.carrot.util.DBManager;
import com.carrot.vo.CategoryVO;

public class CategoriesDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private CategoriesDAO() {
		conn = null;
		pstmt = null;
		rs = null;
	}

	private static CategoriesDAO instance = new CategoriesDAO();

	public static CategoriesDAO getInstance() {
		return instance;
	}

	// 카테고리 추가: 새로운 카테고리를 데이터베이스에 저장
	public int insertCategory(CategoryVO categoryVO) {
		String sql = "INSERT INTO Categories (id, name, parent_id, description) "
				+ "VALUES (seq_categories_id.NEXTVAL, ?, ?, ?)";
		conn = null;
		pstmt = null;
		int result = 0;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, categoryVO.getName());
			pstmt.setInt(2, categoryVO.getParentId());
			pstmt.setString(3, categoryVO.getDescription());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return result;
	}

	// 카테고리 조회: 특정 카테고리 정보 조회
	public CategoryVO getCategoryById(int categoryId) {
		String sql = "SELECT * FROM Categories WHERE id=?";
		conn = null;
		pstmt = null;
		rs = null;
		CategoryVO categoryVO = new CategoryVO();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, categoryId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				categoryVO.setId(rs.getInt("id"));
				categoryVO.setName(rs.getString("name"));
				categoryVO.setParentId(rs.getInt("parent_id"));
				categoryVO.setDescription(rs.getString("description"));
				categoryVO.setCreatedAt(rs.getDate("created_date"));

				categoryVO.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}

		return categoryVO;
	}

	// 전체 카테고리 목록 조회
	public List<CategoryVO> getAllCategories() {
		String sql = "SELECT * FROM Categories ORDER BY parent_id, id";
		conn = null;
		pstmt = null;
		rs = null;
		List<CategoryVO> categoryList = new ArrayList<CategoryVO>();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CategoryVO categoryVO = new CategoryVO();
				categoryVO.setId(rs.getInt("id"));
				categoryVO.setName(rs.getString("name"));
				categoryVO.setParentId(rs.getInt("parent_id"));
				categoryVO.setDescription(rs.getString("description"));
				categoryVO.setCreatedAt(rs.getDate("created_date"));

				categoryList.add(categoryVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}

		return categoryList;
	}

	// 상위 카테고리 목록 조회: 최상위 카테고리만 조회
	public List<CategoryVO> getParentCategories() {
		String sql = "SELECT * FROM Categories WHERE parent_id=0";
		conn = null;
		pstmt = null;
		rs = null;
		List<CategoryVO> categoryList = new ArrayList<CategoryVO>();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CategoryVO categoryVO = new CategoryVO();
				categoryVO.setId(rs.getInt("id"));
				categoryVO.setName(rs.getString("name"));
				categoryVO.setParentId(rs.getInt("parent_id"));
				categoryVO.setDescription(rs.getString("description"));
				categoryVO.setCreatedAt(rs.getDate("created_date"));

				categoryList.add(categoryVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}

		return categoryList;
	}

	// 하위 카테고리 목록 조회: 특정 카테고리의 하위 카테고리 조회
	public List<CategoryVO> getSubCategories(int parentId) {
		String sql = "SELECT * FROM Categories WHERE parent_id=?";
		conn = null;
		pstmt = null;
		rs = null;
		List<CategoryVO> categoryList = new ArrayList<CategoryVO>();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parentId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CategoryVO categoryVO = new CategoryVO();
				categoryVO.setId(rs.getInt("id"));
				categoryVO.setName(rs.getString("name"));
				categoryVO.setParentId(rs.getInt("parent_id"));
				categoryVO.setDescription(rs.getString("description"));
				categoryVO.setCreatedAt(rs.getDate("created_date"));

				categoryList.add(categoryVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}

		return categoryList;
	}

	// 카테고리 수정: 카테고리 정보 업데이트
	public int updateCategory(CategoryVO categoryVO) {
		String sql = "UPDATE Categories SET name=?, description=? WHERE id=?";
		conn = null;
		pstmt = null;
		int result = 0;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, categoryVO.getName());
			pstmt.setString(2, categoryVO.getDescription());
			pstmt.setInt(3, categoryVO.getParentId());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return result;
	}

	// 카테고리 삭제: 특정 카테고리 삭제 (하위 카테고리가 없는 경우에만 가능)
	public int deleteCategory(int categoryId) {
		String sql = "DELETE FROM Categories WHERE id=?";
		conn = null;
		pstmt = null;
		int result = 0;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, categoryId);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return result;
	}

	// 카테고리 삭제: 특정 카테고리와 모든 하위 카테고리를 삭제
	public int deleteCategoryWithSubCategories(int categoryId) {
		String sql = "DELETE FROM Categories WHERE parent_id=?";
		conn = null;
		pstmt = null;
		int result = 0;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, categoryId);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return result;
	}
}
