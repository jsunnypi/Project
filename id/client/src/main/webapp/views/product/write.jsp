<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매글 작성</title>

</head>
<body>
	<%@ include file="/views/common/header.jsp"%>
	<div align="center">
		<h1>판매글 작성 페이지</h1>
		<form method="post" enctype="multipart/form-data" name="frm" action="write.do">
			<table>
				<tr>
					<td><input type="hidden" name="id"></td>
					<td><input type="hidden" name="authorId"></td>
				</tr>
				<tr>
					<td>카테고리</td>
					<td><select name="category">
							<option selected>선택</option>
							<optgroup label="전자제품">
								<option value="6">노트북</option>
								<option value="7">태블릿</option>
								<option value="8">스마트폰</option>
								<option value="9">카메라</option>
								<option value="10">게임기</option>
								<option value="11">TV</option>
							</optgroup>
							<optgroup label="가전제품">
								<option value="12">세탁기</option>
								<option value="13">냉장고</option>
								<option value="14">청소기</option>
								<option value="15">에어컨</option>
								<option value="16">히터</option>
								<option value="17">믹서기</option>
							</optgroup>
							<optgroup label="의류">
								<option value="18">셔츠</option>
								<option value="19">바지</option>
								<option value="20">코트</option>
								<option value="21">드레스</option>
								<option value="22">운동복</option>
								<option value="23">신발</option>
							</optgroup>

							<optgroup label="도서">
								<option value="24">소설</option>
								<option value="25">역사</option>
								<option value="26">과학</option>
								<option value="27">요리</option>
								<option value="28">여행</option>
								<option value="29">예술</option>
							</optgroup>
							<optgroup label="문구류">
								<option value="30">노트</option>
								<option value="31">펜</option>
								<option value="32">파일</option>
								<option value="33">스티커</option>
								<option value="34">테이프</option>
								<option value="35">가위</option>
							</optgroup>
					</select></td>
				</tr>


				<tr>
					<td>제 목</td>
					<td><input type="text" name="title"></td>
				</tr>


				<tr>
					<td>가격</td>
					<td><input type="text" name="price">원</td>
				</tr>
				<tr>
					<td>무료 나눔</td>
					<td><input type="checkbox" name="price" value="1"></td>
				</tr>
				<tr>
					<td>사진 첨부</td>
					<td><input type="file" name="photoUrl"></td>

				</tr>
				<tr>
					<td>설 명</td>
					<td><textarea name="description" rows="10" cols="80"></textarea>
					</td>
				</tr>
			</table>
			<br> <input type="submit" value="등록"
				onclick="return productCheck()"> <input type="reset"
				value="돌아가기" onclick="location.href='main.do'">




		</form>

	</div>
</body>
</html>