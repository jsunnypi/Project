<%@page import="com.carrot.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.header {
	border-bottom: 1px solid #000;
	padding: 10px 20px;
}

.logo-section {
	display: flex;
	align-items: center;
	gap: 20px;
}

.logo {
	font-size: 24px;
	font-weight: bold;
	margin: 0;
}

.nav-menu {
	display: flex;
	gap: 10px;
	margin-left: auto;
}

.menu-item {
	text-decoration: none;
	color: #333;
	padding: 5px 10px;
	background-color: #f0f0f0;
	border-radius: 4px;
}
</style>
</head>
<body>

	<!-- header.jsp -->

	<%
	// 세션에서 로그인 정보 가져오기
	MemberVO userLogin = (MemberVO) session.getAttribute("userLogin");
	boolean isLoggedIn = (userLogin != null); // userLogin이 null이 아니면 로그인된 상태
	%>

	<div class="header">
		<div class="logo-section">
			<h1 class="logo">
				<a href="main.do">토끼마켓</a>
			</h1>
			<div class="nav-menu">
				<%
				if (isLoggedIn) {
				%>
				<a href="${pageContext.request.contextPath}/write.do" class="menu-item">글쓰기</a>
				<a href="${pageContext.request.contextPath}/myPage.do" class="menu-item">마이페이지</a> 
				<a href="${pageContext.request.contextPath}/logout.do" class="menu-item">로그아웃</a>
				<%
				} else {
				%>
				<a href="${pageContext.request.contextPath}/login.do" class="menu-item">로그인</a>
				<a href="${pageContext.request.contextPath}/join.do" class="menu-item">회원가입</a>
				<%
				}
				%>
			</div>
		</div>
	</div>

</body>
</html>
