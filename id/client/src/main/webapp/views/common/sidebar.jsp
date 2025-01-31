<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.sidebar {
    width: 200px;
    height: 100vh;  /* 전체 화면 높이만큼 설정 */
    padding: 20px;
    border-right: 1px solid #ddd;
    position: fixed;  /* 고정 위치 */
    left: 0;         /* 왼쪽 끝에 위치 */
    top: 1px;          /* 최상단에 위치 */
    background-color: white;
    margin-top: 60px;  /* 헤더 높이만큼 여백 추가 */
    overflow-y: auto;  /* 내용이 많을 경우 스크롤 */
}

.category-title {
    font-weight: bold;
    margin: 20px 0 10px 0;
}

.filter-item {
    margin: 8px 0;
}

.price-range {
    margin-top: 10px;
}

.price-range input {
    width: 80px;
    margin: 5px 0;
}

.apply-btn {
    width: 100%;
    padding: 5px;
    margin-top: 5px;
    background-color: #f0f0f0;
    border: 1px solid #ddd;
    border-radius: 4px;
    cursor: pointer;
}
</style>
</head>
<body>
<div class="sidebar">
    <div class="trading-status">
        <input type="checkbox" id="trading"> 
        <label for="trading">거래 가능</label>
    </div>

    <div class="category-section">
        <div class="category-title">카테고리</div>
        <div class="filter-item">
            <input type="radio" name="category" id="all" checked> 
            <label for="all">전체</label>
        </div>
        <div class="filter-item">
            <input type="radio" name="category" id="book"> 
            <label for="book">도서</label>
        </div>
        <div class="filter-item">
            <input type="radio" name="category" id="stationery"> 
            <label for="stationery">문구</label>
        </div>
        <div class="filter-item">
            <input type="radio" name="category" id="electronics"> 
            <label for="electronics">전자기기</label>
        </div>
        <div class="filter-item">
            <input type="radio" name="category" id="clothing"> 
            <label for="clothing">의류</label>
        </div>
    </div>

    <div class="sort-section">
        <div class="category-title">정렬</div>
        <div class="filter-item">
            <input type="radio" name="sort" id="latest" checked> 
            <label for="latest">최신순</label>
        </div>
        <div class="filter-item">
            <input type="radio" name="sort" id="popular"> 
            <label for="popular">인기순</label>
        </div>
    </div>

    <div class="price-section">
        <div class="category-title">가격</div>
        <div class="filter-item">
            <input type="radio" name="price" id="free"> 
            <label for="free">나눔</label>
        </div>
        <div class="filter-item">
            <input type="radio" name="price" id="under5000"> 
            <label for="under5000">5,000원 이하</label>
        </div>
        <div class="filter-item">
            <input type="radio" name="price" id="under10000"> 
            <label for="under10000">10,000원 이하</label>
        </div>
        <div class="filter-item">
            <input type="radio" name="price" id="under20000"> 
            <label for="under20000">20,000원 이하</label>
        </div>
        <div class="price-range">
            <input type="text" placeholder="최소가격"> ~
            <input type="text" placeholder="최대가격">
            <button type="button" class="apply-btn">적용하기</button>
        </div>
    </div>
</div>
</body>
</html>