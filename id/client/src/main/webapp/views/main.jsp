<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.container {
	display: flex;
}

.content {
	flex: 1;
	margin-left: 220px; /* sidebar 너비 + 여백 */
	padding: 20px;
	display: grid;
	grid-template-columns: repeat(3, 1fr);
	gap: 20px;
}

.product-card {
	border: 1px solid #ddd;
	padding: 10px;
	text-align: center;
}

.product-image {
	width: 100%;
	height: 200px;
	background-color: #f0f0f0;
	margin-bottom: 10px;
}
</style>
</head>
<body>
	<%@ include file="/views/common/header.jsp"%>
	<div class="container">
		<%@ include file="/views/common/sidebar.jsp"%>
		<div class="content">
			<h1>메인입니다</h1>
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
		</div>
	</div>
</body>
</html>