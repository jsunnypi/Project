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
	<div align="center">
		<%@ include file="/views/common/header.jsp"%>
		userLogin.userid : ${userLogin.userid}
		product.id : ${product.id}
		<h1>상품 상세 페이지</h1>
		<form method="post">
			<table>
				<tr>
					<th>카테고리</th>
					<td colspan="5">${product.category}</td>
					<td>
						
									
								<%-- 	로그인했을때  --%>
						<c:choose>
							<c:when test="${userLogin.id == product.id}">
								<input type="button" value="글 수정하기"
									onclick="location.href='writeModify.do'">
							</c:when>

							<c:otherwise>
							<input type="button" value="글 수정하기"
									onclick="location.href='writeModify.do'">
							</c:otherwise>
						</c:choose> 


					</td>
				</tr>



				<tr>
					<td colspan="5"><c:choose>
							<c:when test="${empty product.photoUrl}">
								<img src="photo/product/noimg.gif">
							</c:when>

							<c:otherwise>
								<img
									src="${pageContext.request.contextPath}/photos/product/apple.jpg">
							</c:otherwise>
						</c:choose></td>

					<td>

						<table>
							<tr>
								<td>${product.title}</td>
							</tr>
							<tr>
								<td>${product.price}원</td>
							</tr>
							<tr>
								<td>${product.description}</td>
							</tr>
							<tr>
								<td><input type="button" value="메시지 보내기"></td>
								<td><input type="button" value="구매하기"
									onclick="location.href='purchase.do?id=${product.id}'"></td>
							</tr>
						</table>

					</td>

				</tr>

				<tr>
					<td colspan="2">판매자: ${product.authorId}님</td> &nbsp;&nbsp;
					<td>수집한 당근 : 개</td>
				</tr>

			</table>

			<br> 판매자의 다른 판매 물품 목록&nbsp;&nbsp;&nbsp;&nbsp; 
			<input
				type="button" value="더 구경하기" onclick="location.href='salesList.do'">
			<!--  <input type="button" value="더 구경하기" onclick="location.href='salesList?authorId=${product.authorId}'">-->


			<article>
				<c:forEach var="product" items="${salesList}">
					<div>
						<a
							href="<c:url value='/detailedPage.do?id=${product.id}'/>">
							<div>${product.title}</div>
							<div>${product.price}원</div>
							<div>
								<img src="${product.photoUrl}">
							</div>
						</a>
					</div>
				</c:forEach>
			</article>




		</form>
	</div>

</body>
</html>