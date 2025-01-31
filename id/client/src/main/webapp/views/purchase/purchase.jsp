<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<%@ include file="/views/common/header.jsp"%>
		<h1>구매하기</h1>
		<form method="post">
			<table>
				<tr>
					<th>카테고리</th>
					<td colspan="5">${product.category}</td>
					
				</tr>



				<tr>
					<td colspan="5"><c:choose>
							<c:when test="${empty product.photoUrl}">
								<img src="upload/noimg.gif">
							</c:when>

							<c:otherwise>
								<img src="upload/${product.photoUrl}">
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
							
						</table>

					</td>

				</tr>

				<tr>
					<td colspan="2">판매자: ${product.authorId}님</td> &nbsp;&nbsp;
					<td>수집한 당근 : 개</td>
				</tr>

			</table>
			
			<h3>위의 상품을 구매하셨습니다.</h3>
			<h3>판매자에게 어떤 당근을 드릴까요?</h3>
			
			
</body>
</html>