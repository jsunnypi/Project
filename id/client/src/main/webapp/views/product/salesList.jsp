<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/views/common/header.jsp"%>
	<%@ include file="/views/common/sidebar.jsp"%>

	<article>
		<c:forEach var="product" items="${salesList}">
			<div>
				<a
					href="<c:url value='/detailedPage.do?authorId=${product.authorId}'/>">
					<div>${product.title}</div>
					<div>${product.price}원</div>
					<div>
						<img src="${product.photoUrl}">
					</div>
				</a>
			</div>
		</c:forEach>
	</article>


	<%-- <div align="center">
<h1> 판매 물품 목록 </h1>
	<input type="button" value="돌아가기" onclick="location.href='main.do'"> 
<form>
	<c:forEach var="product" items="${salesList}">
		<div>${product.title}</div>
		<div>${product.price}원</div>
		<div>${product.photoUrl}</div>
	</c:forEach>
</form>

</div> --%>
</body>
</html>