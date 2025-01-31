package com.carrot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.carrot.vo.MemberVO;
import com.carrot.vo.MessageVO;

public class MessagesDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private MessagesDAO() {
		conn = null;
		pstmt = null;
		rs = null;
	}

	private static MessagesDAO instance = new MessagesDAO();

	public static MessagesDAO getInstance() {
		return instance;
	}

	// 메시지 전송: 새로운 메시지를 데이터베이스에 저장
	public int insertMessage(MessageVO messageVO) {
		return 0;
	}

	// 특정 회원의 받은 메시지 목록 조회
	public List<MessageVO> getReceivedMessages(int receiverId) {
		return new ArrayList<>();
	}

	// 특정 회원의 보낸 메시지 목록 조회
	public List<MessageVO> getSentMessages(int senderId) {
		return new ArrayList<>();
	}

	// 특정 메시지 상세 내용 조회
	public MessageVO getMessageById(int messageId) {
		return new MessageVO();
	}

	// 메시지 읽음 상태 업데이트
	public int updateMessageReadStatus(int messageId) {
		return 0;
	}

	// 특정 회원의 읽지 않은 메시지 수 조회
	public int getUnreadMessageCount(int receiverId) {
		return 0;
	}

	// 두 회원 간의 대화 내용 조회
	public List<MessageVO> getConversation(int senderId, int receiverId) {
		return new ArrayList<>();
	}

	// 특정 사용자의 전체 대화 목록 조회 (가장 최근 메시지 기준으로 정렬)
	public List<MessageVO> getMessageList(int memberId) {
		return new ArrayList<>();
	}

	// 특정 사용자의 대화 상대 목록 조회 (최근 대화 상대 순으로 정렬)
	public List<MemberVO> getConversationPartners(int memberId) {
		return new ArrayList<>();
	}

	// 보낸 사람만 메시지 내용 수정 가능
	public int updateMessage(int messageId, String content, int senderId) {
		return 0;
	}

	// 보낸 사람이 메시지 삭제 가능
	public int deleteMessage(int messageId, int userId) {
		return 0;
	}
}
