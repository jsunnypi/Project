package com.carrot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.carrot.util.DBManager;
import com.carrot.vo.MemberVO;
import com.carrot.vo.ProductVO;

public class MembersDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private MembersDAO() {
		conn = null;
		pstmt = null;
		rs = null;
	}

	private static MembersDAO instance = new MembersDAO();

	public static MembersDAO getInstance() {
		return instance;
	}

	// 회원가입: 회원 정보를 받아 데이터베이스에 저장
	public int insertMember(MemberVO memberVO) {
		String sql = "insert into members (id, userid, nickname, name, password, phone, email, address) "
				+ "values (seq_members_id.nextval, ?, ?, ?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = -1;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberVO.getUserid());
			pstmt.setString(2, memberVO.getNickname());
			pstmt.setString(3, memberVO.getName());
			pstmt.setString(4, memberVO.getPassword());
			pstmt.setString(5, memberVO.getPhone());
			pstmt.setString(6, memberVO.getEmail());
			pstmt.setString(7, memberVO.getAddress());

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBManager.close(conn, pstmt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 로그인: 아이디와 비밀번호를 받아 일치하는 회원 정보 반환
	public MemberVO loginMember(String userid, String password) {
		String sql = "select * from members where userid = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO memberVO = null;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				memberVO = new MemberVO();
				
				memberVO.setId(rs.getInt("id"));
				memberVO.setUserid(rs.getString("userid"));
				memberVO.setNickname(rs.getString("nickname"));
				memberVO.setName(rs.getString("name"));
				memberVO.setPassword(rs.getString("password"));
				memberVO.setPhone(rs.getString("phone"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setAddress(rs.getString("address"));
				memberVO.setRegistration_date(rs.getDate("registration_date"));
				memberVO.setGrade(rs.getInt("grade"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return memberVO;
	}

	// 회원정보 수정: 변경된 회원 정보를 받아 데이터베이스 업데이트
	public int updateMember(MemberVO memberVO) {
		String sql = "update members set userid = ?, password = ?, name = ?,"
				+ "nickname = ?, phone = ?, email = ?, address = ? where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, memberVO.getUserid());
			pstmt.setString(2, memberVO.getPassword());
			pstmt.setString(3, memberVO.getName());
			pstmt.setString(4, memberVO.getNickname());
			pstmt.setString(5, memberVO.getPhone());
			pstmt.setString(6, memberVO.getEmail());
			pstmt.setString(7, memberVO.getAddress());
			pstmt.setInt(8, memberVO.getId());
			memberVO.setRegistration_date(rs.getDate("registration_date"));
			memberVO.setGrade(rs.getInt("grade"));

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return result;
	}

	// 회원 조회: 회원 ID로 회원 정보 조회
	public MemberVO getMemberById(int id) {
		String sql = "select * from members where id = ?";
		Connection conn = null;
		PreparedStatement pstmtPreparedStatement = null;
		ResultSet rs = null;
		MemberVO memberVO = new MemberVO();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				memberVO.setId(rs.getInt("id"));
				memberVO.setUserid(rs.getString("userid"));
				memberVO.setNickname(rs.getString("nickname"));
				memberVO.setName(rs.getString("name"));
				memberVO.setPassword(rs.getString("password"));
				memberVO.setPhone(rs.getString("phone"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setAddress(rs.getString("address"));
				memberVO.setRegistration_date(rs.getDate("registration_date"));
				memberVO.setGrade(rs.getInt("grade"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmtPreparedStatement, rs);
		}
		return memberVO;
	}

	// 회원 조회: 회원 ID로 회원 정보 조회
	public MemberVO getMemberByUserId(String userid) {
		String sql = "select * from members where userid = ?";
		Connection conn = null;
		PreparedStatement pstmtPreparedStatement = null;
		ResultSet rs = null;
		MemberVO memberVO = new MemberVO();
		try {

			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				memberVO.setId(rs.getInt("id"));
				memberVO.setUserid(rs.getString("userid"));
				memberVO.setNickname(rs.getString("nickname"));
				memberVO.setName(rs.getString("name"));
				memberVO.setPassword(rs.getString("password"));
				memberVO.setPhone(rs.getString("phone"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setAddress(rs.getString("address"));
				memberVO.setRegistration_date(rs.getDate("registration_date"));
				memberVO.setGrade(rs.getInt("grade"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmtPreparedStatement, rs);
		}
		return memberVO;
	}

	// 회원 삭제: 회원 ID를 받아 해당 회원 정보 삭제
	public int deleteMember(int id) {
		String sql = "DELETE FROM Members WHERE id = ?";
		conn = null;
		pstmt = null;
		int result = 0;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return result;
	}

	// 아이디 중복 체크: 회원가입 시 아이디 중복 확인
	public boolean checkDuplicateId(String userid) {
		String sql = "SELECT COUNT(*) FROM Members WHERE userid = ?";
		conn = null;
		pstmt = null;
		rs = null;
		boolean result = false;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);

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

	// 닉네임 중복 체크
	public boolean checkDuplicateNickname(String nickname) {
		String sql = "SELECT COUNT(*) FROM Members WHERE nickname = ?";
		conn = null;
		pstmt = null;
		rs = null;
		boolean result = false;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nickname);

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

	public List<MemberVO> getAllMembers() {
		String sql = "SELECT * FROM Members ORDER BY registration_date DESC";
		conn = null;
		pstmt = null;
		rs = null;
		List<MemberVO> memberList = new ArrayList<MemberVO>();

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberVO memberVO = new MemberVO();

				memberVO.setId(rs.getInt("id"));
				memberVO.setUserid(rs.getString("userid"));
				memberVO.setNickname(rs.getString("nickname"));
				memberVO.setName(rs.getString("name"));
				memberVO.setPassword(rs.getString("password"));
				memberVO.setPhone(rs.getString("phone"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setAddress(rs.getString("address"));

				memberList.add(memberVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}

		return memberList;
	}
}
