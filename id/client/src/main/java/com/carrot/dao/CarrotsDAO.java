package com.carrot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.carrot.util.DBManager;
import com.carrot.vo.CarrotVO;

public class CarrotsDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private CarrotsDAO() {
		conn = null;
		pstmt = null;
		rs = null;
	}

	private static CarrotsDAO instance = new CarrotsDAO();

	public static CarrotsDAO getInstance() {
		return instance;
	}

	// 당근 정보 추가: 회원의 당근 정보를 새로 생성하거나 업데이트
	public int insertCarrots(int memberId) {
		String sql = "INSERT INTO Carrots (id, member_id) VALUES (seq_carrots_id.NEXTVAL, ?)";
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

	// 당근 정보 조회: 특정 회원의 당근 정보 조회
	public CarrotVO getCarrotsByMemberId(int memberId) {
		String sql = "SELECT * FROM Carrots WHERE member_id=?";
		conn = null;
		pstmt = null;
		rs = null;
		CarrotVO carrotVO = new CarrotVO();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				carrotVO.setId(rs.getInt("id"));
				carrotVO.setMemberId(rs.getInt("member_id"));
				carrotVO.setGoldenCarrots(rs.getInt("golden_carrots"));
				carrotVO.setRottenCarrots(rs.getInt("rotten_carrots"));
				carrotVO.setCreatedDate(rs.getDate("created_date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return carrotVO;
	}

	// 당근 정보 수정
	public int updateCarrotsByMemberId(CarrotVO carrotVO) {
		String sql = "UPDATE Carrots SET golden_carrots=?, rotten_carrots=? WHERE member_id=?";
		conn = null;
		pstmt = null;
		int result = 0;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, carrotVO.getGoldenCarrots());
			pstmt.setInt(2, carrotVO.getRottenCarrots());
			pstmt.setInt(3, carrotVO.getMemberId());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return result;
	}

	// 황금 당근 추가: 회원의 황금 당근 수량 증가
	public int addGoldenCarrots(int memberId) {
		String sql = "UPDATE Carrots SET golden_carrots=golden_carrots+1 WHERE member_id=?";
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

	// 썩은 당근 추가: 회원의 썩은 당근 수량 증가
	public int addRottenCarrots(int memberId) {
		String sql = "UPDATE Carrots SET rotten_carrots=rotten_carrots+1 WHERE member_id=?";
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

	// 황금 당근 사용: 회원의 황금 당근 수량 감소
	public int useGoldenCarrots(int memberId, int amount) {
		String sql = "UPDATE Carrots SET golden_carrots=golden_carrots-? WHERE member_id=?";
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

	// 썩은 당근 사용: 회원의 썩은 당근 수량 감소
	public int useRottenCarrots(int memberId, int amount) {
		String sql = "UPDATE Carrots SET rotten_carrots=rotten_carrots-? WHERE member_id=?";
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

	// 전체 당근 순위 조회: 황금 당근 기준으로 회원들의 순위 조회
	public List<CarrotVO> getCarrotsRanking() {
		String sql = "SELECT * FROM Carrots ORDER BY golden_carrots DESC";
		conn = null;
		pstmt = null;
		rs = null;
		List<CarrotVO> carrotList = new ArrayList<CarrotVO>();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				CarrotVO carrotVO = new CarrotVO();
				carrotVO.setId(rs.getInt("id"));
				carrotVO.setMemberId(rs.getInt("member_id"));
				carrotVO.setGoldenCarrots(rs.getInt("golden_carrots"));
				carrotVO.setRottenCarrots(rs.getInt("rotten_carrots"));
				carrotVO.setCreatedDate(rs.getDate("created_date"));

				carrotList.add(carrotVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return carrotList;
	}

	public int deleteCarrotsByMemberId(int memberId) {
		String sql = "DELETE FROM Carrots WHERE id=?";
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
}
