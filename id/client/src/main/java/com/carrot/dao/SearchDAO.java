package com.carrot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.carrot.util.DBManager;
import com.carrot.vo.ProductVO;

public class SearchDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private SearchDAO() {
		conn = null;
		pstmt = null;
		rs = null;
	}

	private static SearchDAO instance = new SearchDAO();

	public static SearchDAO getInstance() {
		return instance;
	}

	// 기본 최신순 전체 상품 검색
	public List<ProductVO> getAllProductsByLatest() {
		String sql = "SELECT * FROM Products WHERE is_hidden=0 ORDER BY created_at DESC";
	    conn = null;
	    pstmt = null;
	    rs = null;
	    List<ProductVO> productList = new ArrayList<ProductVO>();

	    try {
	        conn = DBManager.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        
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

				productList.add(productVO);
			}
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBManager.close(conn, pstmt);
	    }
	    
		return productList;
	}
	
	// 기본 인기순 전체 상품 검색
	public List<ProductVO> getProductsByPopularity() {
		String sql = "SELECT * FROM Products WHERE is_hidden=0 ORDER BY views DESC";
	    conn = null;
	    pstmt = null;
	    rs = null;
	    List<ProductVO> productList = new ArrayList<ProductVO>();

	    try {
	        conn = DBManager.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        
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

				productList.add(productVO);
			}
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBManager.close(conn, pstmt);
	    }
	    
		return productList;
	}

	// 거래가능한 상품만 최신순 검색 (판매완료 제외)
	public List<ProductVO> getAvailableProductsByLatest() {
		String sql = "SELECT * FROM Products WHERE is_hidden=0 AND is_sold=0 ORDER BY created_at DESC";
	    conn = null;
	    pstmt = null;
	    rs = null;
	    List<ProductVO> productList = new ArrayList<ProductVO>();

	    try {
	        conn = DBManager.getConnection();
	        pstmt = conn.prepareStatement(sql);


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

				productList.add(productVO);
			}
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBManager.close(conn, pstmt);
	    }
	    
		return productList;
	}

	// 카테고리별 상품 검색
	public List<ProductVO> searchProductsByCategory(int categoryId) {
		String sql = "SELECT p.* FROM Products p "
					+ "JOIN Categories c ON p.category = c.id "
					+ "WHERE (c.id = ? OR c.parent_id = ?) "
					+ "AND p.is_hidden=0 ORDER BY p.created_at DESC";
			
	    conn = null;
	    pstmt = null;
	    rs = null;
	    List<ProductVO> productList = new ArrayList<ProductVO>();

	    try {
	        conn = DBManager.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        
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

				productList.add(productVO);
			}
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBManager.close(conn, pstmt);
	    }
	    
		return productList;
	}

	// 가격대별 상품 검색
	public List<ProductVO> searchProductsByPriceRange(String priceRange) {
		// priceRange: "free"(무료나눔), "under5000", "under10000", "under20000"
		String sql = "SELECT * FROM Products "
				+ "WHERE is_hidden = 0 "
				+ "AND (? = 'free' AND price = 0 "
				+ "    OR ? = 'under5000' AND price BETWEEN 1 AND 5000 "
		        + "    OR ? = 'under10000' AND price BETWEEN 1 AND 10000 "
		        + "    OR ? = 'under20000' AND price BETWEEN 1 AND 20000 "
		        + "    OR ? = 'all') "
		        + "ORDER BY created_at DESC";
	    conn = null;
	    pstmt = null;
	    rs = null;
	    List<ProductVO> productList = new ArrayList<ProductVO>();

	    try {
	        conn = DBManager.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, priceRange);
	        pstmt.setString(2, priceRange);
	        pstmt.setString(3, priceRange);
	        pstmt.setString(4, priceRange);
	        pstmt.setString(5, priceRange);
	        
	        
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

				productList.add(productVO);
			}
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBManager.close(conn, pstmt);
	    }
	    
		return productList;
	}

	// 특정 가격 범위 검색
	public List<ProductVO> searchProductsByCustomPriceRange(int minPrice, int maxPrice) {
		if(minPrice > maxPrice) {
			int temp = minPrice;
			minPrice = maxPrice;
			maxPrice = temp;
		}
		String sql = "SELECT * FROM Products WHERE is_hidden=0 "
				+ "AND price BETWEEN ? AND ? ORDER BY created_at DESC";
	    conn = null;
	    pstmt = null;
	    rs = null;
	    List<ProductVO> productList = new ArrayList<ProductVO>();

	    try {
	        conn = DBManager.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        
	        
	        
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

				productList.add(productVO);
			}
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBManager.close(conn, pstmt);
	    }
	    
		return productList;
	}

	// 복합 검색 (카테고리 + 가격 + 정렬기준)
	public List<ProductVO> searchProductsByMultipleConditions(
			int categoryId,
			String priceRange,
			boolean availableOnly,
			boolean isPopular) {
	    String sql = "SELECT DISTINCT p.id, p.author_id, p.title, "
	    		+ "                p.photo_url, p.price, p.category, "
	    		+ "                p.views, p.is_sold, p.is_hidden, "
	    		+ "                p.created_at, p.last_bump, p.bump_count "
	    		+ "FROM Products p "
	    		+ "JOIN Categories c ON p.category = c.id "
	    		+ "WHERE p.is_hidden = 0 "
	    		+ "AND (c.id = ? OR c.parent_id = ?) "
	    		+ "AND (? = 'free' AND price = 0 OR "
	    		+ "     ? = 'under5000' AND price BETWEEN 1 AND 5000 OR "
	    		+ "     ? = 'under10000' AND price BETWEEN 1 AND 10000 OR "
	    		+ "     ? = 'under20000' AND price BETWEEN 1 AND 20000 OR "
	    		+ "     ? = 'all') "
	    		+ "AND (? = 1 AND p.is_sold = 0) "
	    		+ "ORDER BY "
	    		+ "    CASE WHEN ? = 1 THEN p.views ELSE NULL END DESC, "
	    		+ "    CASE WHEN ? = 0 THEN p.created_at ELSE NULL END DESC";
	    conn = null;
	    pstmt = null;
	    rs = null;
	    List<ProductVO> productList = new ArrayList<ProductVO>();

	    try {
	        conn = DBManager.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, categoryId);
	        pstmt.setInt(2, categoryId);
	        pstmt.setString(3, priceRange);
	        pstmt.setString(4, priceRange);
	        pstmt.setString(5, priceRange);
	        pstmt.setString(6, priceRange);
	        pstmt.setString(7, priceRange);
	        pstmt.setBoolean(8, availableOnly);
	        pstmt.setBoolean(9, isPopular);
	        pstmt.setBoolean(10, isPopular);
	        
	        
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
				ProductVO productVO = new ProductVO();
				productVO.setId(rs.getInt("id"));
				productVO.setAuthorId(rs.getInt("author_id"));
				productVO.setTitle(rs.getString("title"));
				productVO.setPhotoUrl(rs.getString("photo_url"));
				productVO.setPrice(rs.getInt("price"));
				productVO.setCategory(rs.getInt("category"));
				productVO.setViews(rs.getInt("views"));
				productVO.setSold(rs.getBoolean("is_sold"));
				productVO.setHidden(rs.getBoolean("is_hidden"));
				productVO.setCreatedAt(rs.getDate("created_at"));
				productVO.setLastBump(rs.getDate("last_bump"));
				productVO.setBumpCount(rs.getInt("bump_count"));

				productList.add(productVO);
			}
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBManager.close(conn, pstmt);
	    }
	    
		return productList;
	}

	// 키워드로 검색 (제목, 내용 포함)
	public List<ProductVO> searchProductsByKeyword(String keyword, boolean isPopular, boolean availableOnly) {
	    String sql = "";
	    conn = null;
	    pstmt = null;
	    rs = null;
	    List<ProductVO> productList = new ArrayList<ProductVO>();

	    try {
	        conn = DBManager.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        
	        
	        
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

				productList.add(productVO);
			}
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBManager.close(conn, pstmt);
	    }
	    
		return productList;
	}
	
	// 통합 검색 메서드
	public List<ProductVO> searchProducts(
	        String keyword,          // 검색 키워드
	        int categoryId,      // 카테고리
	        String priceRange,       // 가격 범위
	        int minPrice,
	        int maxPrice,
	        boolean availableOnly,    // 거래가능만 보기
	        boolean isPopular         // 정렬 기준
	) {
		String sql = "SELECT DISTINCT p.id, p.author_id, p.title, "
				+ "                p.photo_url, p.price, p.category, "
				+ "                p.views, p.is_sold, p.is_hidden, "
				+ "                p.created_at, p.last_bump, p.bump_count "
				+ "FROM Products p "
				+ "JOIN Categories c ON p.category = c.id "
				+ "WHERE p.is_hidden = 0 "
				+ "AND (? = '' "
				+ "    OR LOWER(p.title) LIKE LOWER('%' || ? || '%') "
				+ "    OR LOWER(p.description) LIKE LOWER('%' || ? || '%')) "
				+ "AND (? = 0 OR c.id = ? OR c.parent_id = ?) "
				+ "AND (? = 'custom' AND price BETWEEN ? AND ? OR "
				+ "     ? = 'free' AND price = 0 OR "
				+ "     ? = 'under5000' AND price BETWEEN 1 AND 5000 OR "
				+ "     ? = 'under10000' AND price BETWEEN 1 AND 10000 OR "
				+ "     ? = 'under20000' AND price BETWEEN 1 AND 20000 OR "
				+ "     ? = 'all') "
				+ "AND (? = 1 AND p.is_sold = 0) "
				+ "ORDER BY "
				+ "    CASE WHEN ? = 1 THEN p.views ELSE NULL END DESC, "
				+ "    CASE WHEN ? = 0 THEN p.created_at ELSE NULL END DESC";
	    conn = null;
	    pstmt = null;
	    rs = null;
	    List<ProductVO> productList = new ArrayList<ProductVO>();
	    
	    try {
	        conn = DBManager.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, keyword);
	        pstmt.setString(2, keyword);
	        pstmt.setString(3, keyword);
	        pstmt.setInt(4, categoryId);
	        pstmt.setInt(5, categoryId);
	        pstmt.setInt(6, categoryId);
	        pstmt.setString(7, priceRange);
	        pstmt.setInt(8, minPrice);
	        pstmt.setInt(9, maxPrice);
	        pstmt.setString(10, priceRange);
	        pstmt.setString(11, priceRange);
	        pstmt.setString(12, priceRange);
	        pstmt.setString(13, priceRange);
	        pstmt.setString(14, priceRange);
	        pstmt.setBoolean(15, availableOnly);
	        pstmt.setBoolean(16, isPopular);
	        pstmt.setBoolean(17, isPopular);
	        
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
				ProductVO productVO = new ProductVO();
				productVO.setId(rs.getInt("id"));
				productVO.setAuthorId(rs.getInt("author_id"));
				productVO.setTitle(rs.getString("title"));
				productVO.setPhotoUrl(rs.getString("photo_url"));
				productVO.setPrice(rs.getInt("price"));
				productVO.setCategory(rs.getInt("category"));
				productVO.setViews(rs.getInt("views"));
				productVO.setSold(rs.getBoolean("is_sold"));
				productVO.setHidden(rs.getBoolean("is_hidden"));
				productVO.setCreatedAt(rs.getDate("created_at"));
				productVO.setLastBump(rs.getDate("last_bump"));
				productVO.setBumpCount(rs.getInt("bump_count"));

				productList.add(productVO);
			}
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBManager.close(conn, pstmt);
	    }
	    
	    return productList;
	}
}
